package com.tiza.support.model;

import lombok.Data;

/**
 * Description: Token
 * Author: DIYILIU
 * Update: 2017-12-27 11:18
 */

@Data
public class Token {

    private String token;
    private String operatorId;
    private Long datetime;
    private Long tokenAvailableTime;
}

