package com.tiza.api.station.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/** 充电设备接口信息
 * Description: Connector
 * Author: DIYILIU
 * Update: 2017-12-07 14:12
 */

@Data
@Entity
@Table(name = "BS_CHARGECONNECTOR")
public class Connector {

    @Id
    @JsonIgnore
    private Long id;

    private String connectorID;

    private String connectorName;

    private Integer connectorType;

    private Integer voltageUpperLimits;

    private Integer voltageLowerLimits;

    private Integer nationalStandard;

    @Column(name = "ELECTRICITYCURRENT")
    private Double current;

    private Double power;

    private String parkNo;
}
