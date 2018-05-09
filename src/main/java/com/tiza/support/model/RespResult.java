package com.tiza.support.model;

import com.tiza.support.util.HMacMD5;
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
    private String data;
    private String sig;

    public void buildSig(String sigSecret){
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(ret)
                .append(msg)
                .append(data);

        sig = HMacMD5.getHmacMd5Str(sigSecret, strBuf.toString());
    }
}
