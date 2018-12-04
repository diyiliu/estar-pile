package com.tiza.api.charge.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Description: ChargeOrder
 * Author: DIYILIU
 * Update: 2018-03-12 10:02
 */

@Data
@Entity
@Table(name = "bs_chargeorder")
public class ChargeOrder {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "co_seq")
    @SequenceGenerator(name="co_seq", sequenceName = "SQ_BS_CHARGEORDER", allocationSize = 1)
    private Long id;

    @Column(name = "orderSeq")
    private String startChargeSeq;

    @Column(name = "orderStatus")
    private Integer startChargeSeqStat;

    @Column(name = "stopCode")
    private String identCode;

    @Transient
    private String connectorID;

    @JsonIgnore
    @Column(name = "connectorID")
    private Long connector;

    private Integer connectorStatus;

    private Double currentA;

    private Double currentB;

    private Double currentC;

    private Double voltageA;

    private Double voltageB;

    private Double voltageC;

    private Double soc;

    private Date startTime;

    private Date endTime;

    private Double totalPower;

    @JsonProperty("totalElecMoney")
    private Double elecMoney;

    @JsonProperty("totalSeviceMoney")
    private Double serviceMoney;

    private Double totalMoney;

    private Integer stopReason;

    @Column(name = "checkTotalPower")
    private Double totalOrderPower;

    @Column(name = "checkTotalMoney")
    private Double totalOrderMoney;

    private String chargeDetail;

    @Transient
    private Double sumPeriod;

    @Transient
    private List<ChargeDetail> chargeDetails;
}
