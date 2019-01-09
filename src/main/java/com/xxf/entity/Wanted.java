package com.xxf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Wanted {

    private int id;

    private int brand;

    private int size;

    private String taste;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    private int priceLow;

    private int priceHigh;

    private String address;

    private String addressDetail;

    private double latitude;

    private double longitude;

    private int taked;

}
