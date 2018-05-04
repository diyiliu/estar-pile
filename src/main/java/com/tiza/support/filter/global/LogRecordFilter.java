package com.tiza.support.filter.global;

import com.tiza.support.model.ReqBody;
import com.tiza.support.model.RespResult;
import com.tiza.support.util.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Description: LogRecordFilter
 * Author: DIYILIU
 * Update: 2018-05-04 11:03
 */

@Slf4j
@Aspect
@Component
public class LogRecordFilter {

    @Resource
    private JacksonUtil jacksonUtil;

    @AfterReturning(pointcut = "execution(* com.tiza.api..*Controller.*(..))", returning = "respResult")
    public void doAfter(RespResult respResult) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String ip = request.getRemoteAddr();
            ReqBody reqBody = (ReqBody) request.getAttribute("body");

            // 记录请求内容
            log.info("IP:[{}], 请求:[{}], 响应:[{}]",
                    ip, jacksonUtil.toJson(reqBody), jacksonUtil.toJson(respResult));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
