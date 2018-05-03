package com.tiza.support.model;

import lombok.Data;

/**
 * Description: ReqBody
 * Author: DIYILIU
 * Update: 2018-05-03 10:07
 */

@Data
public class ReqBody {

    private String operatorID;
    private String data;
    private String timeStamp;
    private String seq;
    private String sig;
}
