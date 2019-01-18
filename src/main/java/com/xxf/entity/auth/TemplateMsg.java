package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TemplateMsg {

    @NonNull
    @JsonProperty("touser")
    private String toUser;

    @NonNull
    @JsonProperty("weapp_template_msg")
    private WeAppTemplateMsg msg;

    @JsonProperty("mp_template_msg")
    private String mpMsg = "";

}
