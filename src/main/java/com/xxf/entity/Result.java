package com.xxf.entity;

import lombok.Data;

@Data
public class Result<T> {

    private int code = 200;

    private T data;

    private String errMsg;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }
}
