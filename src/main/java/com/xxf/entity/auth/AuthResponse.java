package com.xxf.entity.auth;

import lombok.Data;

@Data
public class AuthResponse {

    /**
     * 用户唯一标识
     */
    private String openId;

    /**
     * 会话密钥
     */
    private String sessionKey;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    private String unionId;

    /**
     * 错误码
     */
    private int errCode;

    /**
     * 错误信息
     */
    private String errMsg;

}
