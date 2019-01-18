package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WeAppTemplateMsg {

    private static final String TEMP_ID = "tudVOL-g2H8Hp49p-ruw24SkQbiEHmlqJjBkCZAXvqU";
    private static final String PAGE = "detail/detail";
    private static final String KEYWORD = "keyword1.DATA";

    @JsonProperty("template_id")
    private String tempId = TEMP_ID;

    @JsonProperty("page")
    private String page = PAGE;

    @JsonProperty("form_id")
    private String formId;

    @JsonProperty("data")
    private String data;

    @JsonProperty("emphasis_keyword")
    private String keyword = KEYWORD;

}
