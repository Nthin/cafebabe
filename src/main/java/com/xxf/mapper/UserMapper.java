package com.xxf.mapper;

import com.xxf.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select({"select * from user"})
    @Results({
            @Result(property = "registerTime", column = "register_time")
    })
    List<User> selectAll();

    @Select({"select * from user where id = #{id}"})
    @Results({
            @Result(property = "registerTime", column = "register_time")
    })
    User selectOne(@Param("id") int id);

    @Insert({"insert into user(openId, register_time, nickname, position, phone, wechat) values(#{openId}, #{registerTime}, #{nickname}, #{position}, #{phone}, #{wechat})"})
    int insert(User user);

    @Select({"select r.wantedId from record r where userId = #{id}"})
    List<Integer> selectFromRecord(@Param("id") int id);
}
