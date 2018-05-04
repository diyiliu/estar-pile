package com.tiza.support.filter.global;

import com.tiza.support.model.RespResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Description: ExceptionHandlerAdvice
 * Author: DIYILIU
 * Update: 2018-05-04 10:28
 */

@RestController
@ControllerAdvice
public class ExceptionHandlerFilter {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespResult noHandlerFound() {
        RespResult result = new RespResult();
        result.setRet(404);
        result.setMsg("请求路径不存在!");

        return result;
    }

    @ExceptionHandler(Exception.class)
    public RespResult defaultErrorHandler(Exception e) {
        e.printStackTrace();

        RespResult result = new RespResult();
        result.setRet(500);
        result.setMsg(e.getMessage());

        return result;
    }
}
