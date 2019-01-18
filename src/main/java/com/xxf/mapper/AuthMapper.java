package com.xxf.mapper;

import com.xxf.entity.auth.AuthCode;
import org.apache.ibatis.annotations.Select;

public interface AuthMapper {

    @Select({"select a.app_id, a.app_secret from auth a"})
    AuthCode select();

}
