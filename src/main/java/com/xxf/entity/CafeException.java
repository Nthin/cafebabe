package com.xxf.entity;

import org.springframework.http.HttpStatus;

public class CafeException extends RuntimeException {

    private int code = HttpStatus.BAD_REQUEST.value();

    public CafeException(String errMsg) {
        super(errMsg);
    }

    public CafeException(Throwable e) {
        super(e);
    }

    public CafeException(int code, String errMsg) {
        super(errMsg);
        this.code = code;
    }

    public CafeException(int code, Throwable e) {
        super(e);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
