package com.xxf.entity.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class WeAppTemplateMsg {

    private static final String TEMP_ID = "tudVOL-g2H8Hp49p-ruw2-CB_XDbHARGw9-H2X8NH-8";
    private static final String PAGE_PREFIX = "pages/detail/detail?from=my&id=";
    private static final String KEYWORD = "keyword1.DATA";

    @JsonProperty("template_id")
    private String tempId = TEMP_ID;

    @JsonProperty("page")
    private String page;

    @NonNull
    @JsonProperty("form_id")
    private String formId;

    @NonNull
    @JsonRawValue
    @JsonProperty("data")
    private String data;

    @JsonProperty("emphasis_keyword")
    private String keyword = KEYWORD;

    public void setPage(int wantedId) {
        this.page = PAGE_PREFIX + wantedId;
    }

}
