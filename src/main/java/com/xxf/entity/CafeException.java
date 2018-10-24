package com.xxf.entity;

public class CafeException extends RuntimeException {

    private int code;

    private String errMsg;

    public CafeException(String errMsg) {
        super(errMsg);
    }

    public CafeException(int code, String errMsg) {
        super(errMsg);
        this.code = code;
    }

    public CafeException(Throwable e) {
        super(e);
    }
}
