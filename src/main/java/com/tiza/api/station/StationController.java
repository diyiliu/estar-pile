package com.tiza.api.station;

import com.tiza.api.operator.dto.Operator;
import com.tiza.api.station.dto.Connector;
import com.tiza.api.station.dto.ConnectorStatus;
import com.tiza.api.station.facade.ConnectorJpa;
import com.tiza.api.station.facade.ConnectorStatusJpa;
import com.tiza.support.model.RespResult;
import com.tiza.support.util.AESUtil;
import com.tiza.support.util.JacksonUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: StationController
 * Author: DIYILIU
 * Update: 2018-05-03 15:02
 */

@RestController
public class StationController {

    @Resource
    private JacksonUtil jacksonUtil;

    @Resource
    private ConnectorJpa connectorJpa;

    @Resource
    private ConnectorStatusJpa connectorStatusJpa;

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
            respResult.setMsg(connectorStatus.getConnectorID() +  "不存在。");

            return respResult;
        }

        connectorStatus.setId(connector.getId());
        connectorStatus =  connectorStatusJpa.save(connectorStatus);
        if (connectorStatus == null){
            respMap.put("Status", 1);

            String respData = AESUtil.Encrypt(jacksonUtil.toJson(respMap), operator.getDataSecret(), operator.getDataSecretIv());
            respResult.setData(respData);
            respResult.setMsg(connectorStatus.getConnectorID() +  "FAIL");

            return respResult;
        }

        respMap.put("Status", 0);
        String respData = AESUtil.Encrypt(jacksonUtil.toJson(respMap), operator.getDataSecret(), operator.getDataSecretIv());
        respResult.setData(respData);
        respResult.setMsg(connectorStatus.getConnectorID() +  "OK");

        return respResult;
    }
}
