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
            @Result(property = "priceHigh", column = "price_high"),
            @Result(property = "addressDetail", column = "address_detail")
    })
    List<Wanted> selectAll();

    @Select({"select * from wanted where id = #{id}"})
    @Results({
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high"),
            @Result(property = "addressDetail", column = "address_detail")
    })
    Wanted selectOne(@Param("id") int id);

    @Insert({"insert into wanted(brand, size, taste, start_time, end_time, price_low, price_high, address, address_detail, latitude, longitude) values(#{brand}, #{size}, #{taste}, #{startTime}, #{endTime}, #{priceLow}, #{priceHigh}, #{address}, #{addressDetail}, #{latitude}, #{longitude})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Wanted wanted);

    @Insert({"insert into record(wantedId, userId, add_time) values(#{wantedId}, #{userId}, #{addTime})"})
    int insertRecord(@Param("wantedId") int wantedId, @Param("userId") int userId, @Param("addTime") String addTime);

    @Update({"update wanted set taked = #{taked} where id = #{id}"})
    int update(@Param("id") int id, @Param("taked") int taked);

    @Select({"select r.userId from record r where wantedId = #{id}"})
    int selectFromRecord(@Param("id") int id);

    @Update({"update record r set r.taked_userId = #{takedUserId}, r.taked_time = #{takedTime} where r.wantedId = #{wantedId}"})
    int updateRecord(@Param("wantedId") int wantedId, @Param("takedUserId") int takedUserId, @Param("takedTime") String takedTime);
}
