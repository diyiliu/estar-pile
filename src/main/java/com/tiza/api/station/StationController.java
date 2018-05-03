package com.tiza.api.station;

import com.tiza.api.operator.dto.Operator;
import com.tiza.support.model.RespResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: StationController
 * Author: DIYILIU
 * Update: 2018-05-03 15:02
 */

@RestController
public class StationController {


    @PostMapping("/notification_stationStatus")
    public RespResult notificationStationStatus(HttpServletRequest request){
        Operator operator = (Operator) request.getAttribute("operator");
        String json = (String) request.getAttribute("data");

        System.out.println(json);

        return null;
    }
}
