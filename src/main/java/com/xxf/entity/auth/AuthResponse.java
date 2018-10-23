package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthResponse {

    /**
     * 用户唯一标识
     */
    @JsonProperty("openid")
    private String openId;

    /**
     * 会话密钥
     */
    @JsonProperty("session_key")
    private String session_key;

    /**
     * 用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回
     */
    @JsonProperty("unionid")
    private String unionId;

    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private int errCode;

    /**
     * 错误信息
     */
    @JsonProperty("errMsg")
    private String errMsg;

}
