package com.tiza.support.model;

import com.tiza.api.operator.dto.Operator;
import com.tiza.support.util.AESUtil;
import com.tiza.support.util.JacksonUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Description: BaseController
 * Author: DIYILIU
 * Update: 2018-12-03 10:49
 */

@Component
public class BaseController {

    @Resource
    protected JacksonUtil jacksonUtil;

    protected RespResult buildResult(Map respMap, String msg, Operator operator) throws Exception {
        RespResult respResult = new RespResult();
        String respData = AESUtil.Encrypt(jacksonUtil.toJson(respMap), operator.getDataSecret(), operator.getDataSecretIv());
        respResult.setData(respData);
        respResult.setMsg(msg);
        respResult.buildSig(operator.getSigSecret());

        return respResult;
    }
}
