package com.tiza.api.station.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Description: ConnectorStatus
 * Author: DIYILIU
 * Update: 2018-02-28 16:00
 */

@Data
@Entity
@Table(name = "BS_CHARGECONNECTORSTATUS")
public class ConnectorStatus {

    @Id
    @JsonIgnore
    private Long id;

    private String connectorID;

    private Integer status;

    private Integer parkStatus;

    private Integer lockStatus;
}
