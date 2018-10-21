package com.xxf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Wanted {

    private int id;

    private int size;

    private String taste;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;

    private int priceLow;

    private int priceHigh;

    private int taked;

}
