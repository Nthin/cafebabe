package com.xxf.entity;

public class CafeException extends RuntimeException {

    private int code;

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
