package com.tiza.api.account;

import com.tiza.api.operator.dto.Operator;
import com.tiza.support.anno.SystemLog;
import com.tiza.support.model.RespResult;
import com.tiza.support.model.Token;
import com.tiza.support.util.AESUtil;
import com.tiza.support.util.JacksonUtil;
import com.tiza.support.util.RedisUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description: AccountController
 * Author: DIYILIU
 * Update: 2018-05-03 10:04
 */

@RestController
public class AccountController {
    private final static long TOKEN_AVAILABLE_TIME = 30 * 60;

    @Resource
    private JacksonUtil jacksonUtil;

    @Resource
    private RedisUtil redisUtil;

    /**
     *  获取TOKEN
     *
     * @param request
     * @return
     * @throws Exception
     */
    @SystemLog
    @PostMapping("/query_token")
    public RespResult queryToken(HttpServletRequest request) throws Exception{
        Operator operator = (Operator) request.getAttribute("operator");
        String json = (String) request.getAttribute("data");
        Map tokenMap = jacksonUtil.toObject(json, HashMap.class);

        RespResult respResult = new RespResult();
        if (!operator.getOperatorSecret().equals(tokenMap.get("OperatorSecret"))){
            respResult.setRet(4002);
            respResult.setMsg("OperatorSecret错误");

            return respResult;
        }

        // 生产token
        String token = generateToken();

        Token t = new Token();
        t.setToken(token);
        t.setOperatorId(operator.getOperatorId());
        t.setDatetime(System.currentTimeMillis());
        t.setTokenAvailableTime(TOKEN_AVAILABLE_TIME);
        redisUtil.set(token, t);

        Map respMap = new HashMap();
        respMap.put("SuccStat", 0);
        respMap.put("FailReason", 0);
        respMap.put("AccessToken", token);
        respMap.put("TokenAvailableTime", TOKEN_AVAILABLE_TIME);

        String respData = AESUtil.Encrypt(jacksonUtil.toJson(respMap), operator.getDataSecret(), operator.getDataSecretIv());
        respResult.setData(respData);
        respResult.setRet(0);
        respResult.setMsg("请求成功");
        respResult.buildSig(operator.getSigSecret());

        return respResult;
    }

    /**
     *  生成Token
     *
     * @return
     */
    private String generateToken() throws Exception {
        String uuid = UUID.randomUUID().toString();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(uuid.getBytes("UTF-8"));

        StringBuffer strBuf = new StringBuffer();
        for (byte b : bytes) {
            strBuf.append(String.format("%02X", b < 0 ? b + 256 : b));
        }

        return strBuf.toString();
    }
}
