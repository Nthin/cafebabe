package com.xxf.service;

import com.xxf.entity.CafeException;
import com.xxf.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {

    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({CafeException.class})
    public Result cafeExceptionHandler(CafeException ce) {
        log.error(ce.getMessage(), ce);
        return new Result(ce.getCode(), ce.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler({Exception.class})
    public Result otherExceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        return new Result(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
