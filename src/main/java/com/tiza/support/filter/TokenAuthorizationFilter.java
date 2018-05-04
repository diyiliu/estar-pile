package com.tiza.support.filter;

import com.tiza.api.operator.dto.Operator;
import com.tiza.api.operator.facade.OperatorJpa;
import com.tiza.support.model.ReqBody;
import com.tiza.support.model.RespResult;
import com.tiza.support.model.Token;
import com.tiza.support.util.AESUtil;
import com.tiza.support.util.HMacMD5;
import com.tiza.support.util.JacksonUtil;
import com.tiza.support.util.RedisUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

/**
 * Description: TokenAuthorizationFilter
 * Author: DIYILIU
 * Update: 2018-05-03 09:38
 */

@Component
public class TokenAuthorizationFilter implements Filter {

    @Resource
    private JacksonUtil jacksonUtil;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private OperatorJpa operatorJpa;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();

        // 区分 请求token
        if (!uri.endsWith("query_token")) {
            String auth = req.getHeader("Authorization");
            String[] authArr = auth.split(" ");
            if (authArr.length == 2 && authArr[0].equals("Bearer")) {
                RespResult result = new RespResult();

                String token = authArr[1];
                Token t = (Token) redisUtil.get(token);
                // token 无效
                if (t == null) {
                    result.setRet(4002);
                    result.setMsg("Token 无效");

                    interrupt(response, result);
                    return;
                } else {
                    // token 过期
                    long datetime = t.getDatetime();
                    if (System.currentTimeMillis() - datetime > t.getTokenAvailableTime() * 1000) {
                        result.setRet(4002);
                        result.setMsg("Token 过期");

                        interrupt(response, result);
                        return;
                    }
                }
            }
        }

        byte[] bytes = FileCopyUtils.copyToByteArray(req.getInputStream());
        String body = new String(bytes, Charset.forName("UTF-8"));
        // 消息体
        ReqBody reqBody = jacksonUtil.toObject(body, ReqBody.class);

        // 运营商信息
        String operatorId = reqBody.getOperatorID();
        Operator operator = operatorJpa.findByOperatorId(operatorId);
        if (operator == null) {
            RespResult result = new RespResult();
            result.setRet(500);
            result.setMsg("OperatorId不存在");

            interrupt(response, result);
            return;
        }

        // 校验签名
        if (!checkSig(reqBody, operator.getSigSecret())) {
            RespResult result = new RespResult();
            result.setRet(4001);
            result.setMsg("Sig错误");

            interrupt(response, result);
            return;
        }

        // 返回数据
        try {
            String json = AESUtil.Decrypt(reqBody.getData(), operator.getDataSecret(), operator.getDataSecretIv());
            req.setAttribute("operator", operator);
            req.setAttribute("data", json);

            chain.doFilter(request, response);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();

            RespResult result = new RespResult();
            result.setRet(500);
            result.setMsg("数据解密异常!");
            interrupt(response, result);
        }
    }

    /**
     * 返回错误
     *
     * @param response
     */
    private void interrupt(ServletResponse response, RespResult result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String error = jacksonUtil.toJson(result);
        response.getWriter().write(error);
    }

    /**
     * 校验签名
     *
     * @param reqBody
     * @param sigSecret
     * @return
     */
    private boolean checkSig(ReqBody reqBody, String sigSecret) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(reqBody.getOperatorID())
                .append(reqBody.getData())
                .append(reqBody.getTimeStamp())
                .append(reqBody.getSeq());

        String str = HMacMD5.getHmacMd5Str(sigSecret, strBuf.toString());
        return str.equals(reqBody.getSig());
    }


    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
