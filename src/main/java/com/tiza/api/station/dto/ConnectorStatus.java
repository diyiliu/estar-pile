package com.tiza.api.station.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * Description: ConnectorStatus
 * Author: DIYILIU
 * Update: 2018-02-28 16:00
 */

@Data
public class ConnectorStatus {

    @JsonIgnore
    private Long id;

    private String connectorID;

    private Integer status;

    private Integer parkStatus;

    private Integer lockStatus;
}
