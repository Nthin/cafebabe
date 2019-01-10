package com.xxf.mapper;

import com.xxf.entity.Record;
import com.xxf.entity.Wanted;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WantedMapper {

    @Select({"select * from wanted"})
    List<Wanted> selectAll();

    @Select({"select * from wanted w where w.id = #{id}"})
    Wanted selectOne(@Param("id") int id);

    @Insert({"insert into wanted(brand, size, taste, end_time, price_low, price_high, address, address_detail, latitude, longitude) values(#{brand}, #{size}, #{taste}, #{endTime}, #{priceLow}, #{priceHigh}, #{address}, #{addressDetail}, #{latitude}, #{longitude})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(Wanted wanted);

    @Insert({"insert into record(wanted_id, add_user_id, add_time) values(#{wantedId}, #{userId}, #{addTime})"})
    int insertRecord(@Param("wantedId") int wantedId, @Param("userId") int userId, @Param("addTime") String addTime);

    @Update({"update wanted set taked = #{taked} where id = #{id}"})
    int update(@Param("id") int id, @Param("taked") int taked);

    @Select({"select * from record r where r.wanted_id = #{id}"})
    Record selectFromRecord(@Param("id") int id);

    @Update({"update record r set r.taked_user_id = #{takedUserId}, r.taked_time = #{takedTime} where r.wanted_id = #{wantedId}"})
    int updateRecord(@Param("wantedId") int wantedId, @Param("takedUserId") int takedUserId, @Param("takedTime") String takedTime);
}
