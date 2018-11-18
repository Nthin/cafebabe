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

    @Select({"select * from user where openId = #{openId}"})
    @Results({
            @Result(property = "registerTime", column = "register_time")
    })
    User selectOne(@Param("openId") String openId);

    @Select({"select * from user where id = #{id}"})
    @Results({
            @Result(property = "registerTime", column = "register_time")
    })
    User selectOneById(@Param("id") int id);

    @Insert({"insert into user(openId, register_time, nickname, avatarUrl) values(#{openId}, #{registerTime}, #{nickname}, #{avatarUrl})"})
    int insert(User user);

    @Select({"select r.wantedId from record r where userId = #{id}"})
    List<Integer> selectFromRecord(@Param("id") int id);

    @Update({"update user u set u.position = #{position}, u.phone = #{phone} where u.id = #{id}"})
    int update(@Param("id") int id, @Param("position") String position, @Param("phone") String phone);
}
