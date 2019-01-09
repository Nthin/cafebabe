package com.xxf.mapper;

import com.xxf.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select({"select * from user"})
    List<User> selectAll();

    @Select({"select * from user u where u.open_id = #{openId}"})
    User selectOne(@Param("openId") String openId);

    @Select({"select * from user u where u.id = #{id}"})
    User selectOneById(@Param("id") int id);

    @Insert({"insert into user(open_id, register_time, nickname, avatar_url) values(#{openId}, #{registerTime}, #{nickname}, #{avatarUrl})"})
    int insert(User user);

    @Select({"select r.wanted_id from record r where r.add_user_id = #{id}"})
    List<Integer> selectFromRecord(@Param("id") int id);

    @Update({"update user u set u.position = #{position}, u.phone = #{phone} where u.id = #{id}"})
    int update(@Param("id") int id, @Param("position") String position, @Param("phone") String phone);
}
