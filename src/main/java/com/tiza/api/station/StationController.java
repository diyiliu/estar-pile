package com.tiza.api.station;

import com.tiza.api.operator.dto.Operator;
import com.tiza.api.station.dto.Connector;
import com.tiza.api.station.dto.ConnectorStatus;
import com.tiza.api.station.facade.ConnectorJpa;
import com.tiza.api.station.facade.ConnectorStatusJpa;
import com.tiza.support.model.BaseController;
import com.tiza.support.model.RespResult;
import com.tiza.support.util.AESUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: StationController
 * Author: DIYILIU
 * Update: 2018-05-03 15:02
 */

@RestController
public class StationController extends BaseController {

    @Resource
    private ConnectorJpa connectorJpa;

    @Resource
    private ConnectorStatusJpa connectorStatusJpa;

    /**
     *  6.3 推送设备状态
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/notification_stationStatus")
    public RespResult notificationStationStatus(HttpServletRequest request) throws Exception{
        Operator operator = (Operator) request.getAttribute("operator");
        String json = (String) request.getAttribute("data");

        Map statusMap = jacksonUtil.toObject(json, HashMap.class);
        String statusJson = jacksonUtil.toJson(statusMap.get("ConnectorStatusInfo"));
        ConnectorStatus connectorStatus = jacksonUtil.toObject(statusJson, ConnectorStatus.class);

        // 查询设备接口
        Connector connector = connectorJpa.findByConnectorIDAndOperatorID(connectorStatus.getConnectorID(), operator.getOperatorId());

        RespResult respResult = new RespResult();
        respResult.setRet(0);
        Map respMap = new HashMap();
        if (connector == null){
            respMap.put("Status", 1);

            String respData = AESUtil.Encrypt(jacksonUtil.toJson(respMap), operator.getDataSecret(), operator.getDataSecretIv());
            respResult.setData(respData);
            respResult.setMsg("设备接口[" + connectorStatus.getConnectorID() + "]不存在。");

            return respResult;
        }

        // 设备接口编码
        String connectorID = connectorStatus.getConnectorID();
        connectorStatus.setId(connector.getId());
        connectorStatus.setSystemTime(new Date());
        connectorStatus =  connectorStatusJpa.save(connectorStatus);
        if (connectorStatus == null){
            respMap.put("Status", 1);
            respResult.setMsg("设备[" + connectorID  +  "]状态更新失败!");

            return buildResult(respMap, "设备[" + connectorID  +  "]状态更新失败!", operator);
        }

        respMap.put("Status", 0);

        return buildResult(respMap, "设备[" + connectorID  +  "]状态更新成功!", operator);
    }
}
