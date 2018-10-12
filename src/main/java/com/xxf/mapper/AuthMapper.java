package com.xxf.mapper;

import com.xxf.entity.auth.AuthCode;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {

    @Select({"select * from auth"})
    @Results({
            @Result(property = "appId", column = "app_id"),
            @Result(property = "appSecret", column = "app_secret")
    })
    AuthCode select();

}
