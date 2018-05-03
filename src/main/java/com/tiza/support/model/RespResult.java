package com.tiza.support.model;

import lombok.Data;

/**
 * Description: RespResult
 * Author: DIYILIU
 * Update: 2017-12-08 10:50
 */

@Data
public class RespResult {

    private Integer ret = 0;
    private String msg = "";
    private Object Data;
}
