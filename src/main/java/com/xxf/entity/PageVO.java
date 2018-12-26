package com.xxf.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {

    @JsonProperty("list")
    private List<WantedVO> wantedVOList;

    @JsonProperty("next")
    private Integer nextPage;

}
