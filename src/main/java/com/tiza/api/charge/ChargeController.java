package com.tiza.api.charge;

import com.tiza.api.charge.dto.ChargeOrder;
import com.tiza.api.charge.facade.ChargeOrderJpa;
import com.tiza.api.operator.dto.Operator;
import com.tiza.api.station.dto.Connector;
import com.tiza.api.station.facade.ConnectorJpa;
import com.tiza.support.model.BaseController;
import com.tiza.support.model.RespResult;
import com.tiza.support.util.JacksonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: ChargeController
 * Author: DIYILIU
 * Update: 2018-12-03 10:06
 */

@RestController
public class ChargeController extends BaseController {

    @Resource
    private JacksonUtil jacksonUtil;

    @Resource
    private ConnectorJpa connectorJpa;

    @Resource
    private ChargeOrderJpa chargeOrderJpa;

    /**
     * 6.10 推送充电订单信息
     *
     * @return
     */
    @PostMapping("/notification_charge_order_info")
    public RespResult notificationChargeOrderInfo(HttpServletRequest request) throws Exception {
        Operator operator = (Operator) request.getAttribute("operator");
        String json = (String) request.getAttribute("data");
        ChargeOrder chargeOrder = jacksonUtil.toObject(json, ChargeOrder.class);
        String orderSeq = chargeOrder.getStartChargeSeq();

        Map respMap = new HashMap();
        respMap.put("StartChargeSeq", chargeOrder.getStartChargeSeq());
        respMap.put("ConnectorID", chargeOrder.getConnectorID());

        // 查询设备接口
        Connector connector = connectorJpa.findByConnectorIDAndOperatorID(chargeOrder.getConnectorID(), operator.getOperatorId());
        if (connector == null){
            respMap.put("ConfirmResult", 2);

            return buildResult(respMap, "设备[" + chargeOrder.getConnectorID() + "]不存在, 充电订单信息[" + orderSeq + "更新失败!", operator);
        }

        ChargeOrder oldOrder = chargeOrderJpa.findByStartChargeSeqAndConnector(
                chargeOrder.getStartChargeSeq(), connector.getId());
        if (oldOrder != null) {
            oldOrder.setStartTime(chargeOrder.getStartTime());
            oldOrder.setEndTime(chargeOrder.getEndTime());
            oldOrder.setTotalPower(chargeOrder.getTotalPower());
            oldOrder.setElecMoney(chargeOrder.getElecMoney());
            oldOrder.setServiceMoney(chargeOrder.getServiceMoney());
            oldOrder.setTotalMoney(chargeOrder.getTotalMoney());
            oldOrder.setStopReason(chargeOrder.getStopReason());
            oldOrder.setChargeDetail(jacksonUtil.toJson(chargeOrder.getChargeDetails()));

            chargeOrder = chargeOrderJpa.save(oldOrder);
        } else {

            chargeOrder.setConnector(connector.getId());
            chargeOrder.setChargeDetail(jacksonUtil.toJson(chargeOrder.getChargeDetails()));
            chargeOrder = chargeOrderJpa.save(chargeOrder);
        }

        if (chargeOrder == null) {
            respMap.put("ConfirmResult", 2);

            return buildResult(respMap, "充电订单信息[" + orderSeq + "更新失败!", operator);
        }

        respMap.put("ConfirmResult", 0);
        return buildResult(respMap, "充电订单信息[" + orderSeq + "更新成功!", operator);
    }
}
