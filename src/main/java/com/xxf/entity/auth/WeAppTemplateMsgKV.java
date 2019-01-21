package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
public class WeAppTemplateMsgKV {

    @JsonProperty("keyword1")
    private Map<String, String> k1;

    @JsonProperty("keyword2")
    private Map<String, String> k2;

    @JsonProperty("keyword3")
    private Map<String, String> k3;

    @JsonProperty("keyword4")
    private Map<String, String> k4;

    @JsonProperty("keyword5")
    private Map<String, String> k5;

}
