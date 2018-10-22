package com.xxf.mapper;

import com.xxf.entity.Wanted;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WantedMapper {

    @Select({"select * from wanted"})
    @Results({
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high")
    })
    List<Wanted> selectAll();

    @Select({"select * from wanted where id = #{id}"})
    @Results({
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high")
    })
    Wanted selectOne(@Param("id") int id);

    @Insert({"insert into wanted(brand, size, taste, start_time, end_time, price_low, price_high) values(#{brand}, #{size}, #{taste}, #{startTime}, #{endTime}, #{priceLow}, #{priceHigh})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Wanted wanted);

    @Insert({"insert into record(wantedId, userId) values(#{wantedId}, #{userId})"})
    int insertRecord(@Param("wantedId") int wantedId, @Param("userId") int userId);

    @Update({"updateUser wanted set taked = #{taked} where id = #{id}"})
    int update(@Param("id") int id, @Param("taked") int taked);

    @Select({"select r.userId from record r where wantedId = #{id}"})
    int selectFromRecord(@Param("id") int id);
}
