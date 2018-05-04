package com.tiza.support.filter.global;

import com.tiza.support.model.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.*;

/**
 * Description: ExceptionHandlerAdvice
 * Author: DIYILIU
 * Update: 2018-05-04 10:28
 */

@Slf4j
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
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(out);
        e.printStackTrace(stream);
        String error = new String(out.toByteArray());
        // 记录错误信息
        log.error(error);

        RespResult result = new RespResult();
        result.setRet(500);
        result.setMsg(e.getMessage());

        return result;
    }
}
