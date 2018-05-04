package com.tiza.api.station.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    @Transient
    private String connectorID;

    @Column(name = "SERVICESTATUS")
    private Integer status;

    private Integer parkStatus;

    private Integer lockStatus;

    private Date systemTime;
}
