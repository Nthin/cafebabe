package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UniformMsgResponse {

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
