package com.xxf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class WantedVO {

    private Integer id;

    private Integer brand;

    private String address;

    private String nickname;

    private int priceHigh;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private double latitude;

    private double longitude;

}
