package com.xxf.entity;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Result<T> {

    private int code = HttpStatus.OK.value();

    private T data;

    private String errMsg;

    public Result() {
    }

    public Result(int code) {
        this.code = code;
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public Result(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
