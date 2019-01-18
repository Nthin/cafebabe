package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AccessResponse {

    /**
     * 获取到的凭证
     */
    @JsonProperty("access_token")
    private String accessToken;

    /**
     * 凭证有效时间，单位：秒。目前是 7200 秒之内的值。
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;

    /**
     * 错误码
     */
    @JsonProperty("errcode")
    private int errCode;

    /**
     * 错误信息
     */
    @JsonProperty("errmsg")
    private String errMsg;

}
