package com.xxf.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int id;

    private String openId;

    private Date registerTime;

    private String nickname;

    private String position;

    private String phone;

    private String wechat;

    private String avatarUrl;

}
