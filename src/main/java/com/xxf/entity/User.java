package com.xxf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;

    private String openId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;

    private String nickname;

    private String position;

    private String phone;

    private String wechat;

    private String avatarUrl;

}
