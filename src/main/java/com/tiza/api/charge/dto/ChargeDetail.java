package com.tiza.api.charge.dto;

import lombok.Data;

/**
 * Description: ChargeDetail
 * Author: DIYILIU
 * Update: 2018-03-12 14:45
 */

@Data
public class ChargeDetail {

    private String detailStartTime;

    private String detailEndTime;

    private Double elecPrice;

    private Double sevicePrice;

    private Double detailPower;

    private Double detailElecMoney;

    private Double detailSeviceMoney;
}
