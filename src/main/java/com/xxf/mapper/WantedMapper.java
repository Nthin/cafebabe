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

    @Insert({"insert into wanted(size, taste, start_time, end_time, price_low, price_high) values(#{size}, #{taste}, #{startTime}, #{endTime}, #{priceLow}, #{priceHigh})"})
    int insert(Wanted wanted);

    @Update({"update wanted set taked = #{taked} where id = #{id}"})
    int update(@Param("id") int id, @Param("taked") int taked);

    @Select({"select r.userId from record r where wantedId = #{id}"})
    int selectFromRecord(@Param("id") int id);
}
