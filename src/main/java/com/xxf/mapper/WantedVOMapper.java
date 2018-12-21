package com.xxf.mapper;

import com.xxf.common.SimpleSelectInExtendedLanguageDriver;
import com.xxf.entity.WantedVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WantedVOMapper {

    @Select({"SELECT w.id, w.brand, w.address, u.nickname, w.price_high, w.end_time, w.latitude, w.longitude " +
            "FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id " +
            "WHERE w.taked = 0 " +
            "AND w.end_time > CURRENT_TIME()"})
    @Results({
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high"),
            @Result(property = "endTime", column = "end_time")
    })
    List<WantedVO> selectUntaked();

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select({"<script> SELECT w.id, w.brand, w.address, u.nickname, w.price_high, w.end_time, w.latitude, w.longitude " +
            "FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id " +
            "WHERE w.taked = 0 " +
            "AND w.end_time > CURRENT_TIME() " +
            "<if test='price != null'>AND w.price_high &lt;= #{price} </if>" +
            "<if test='brands != null'>AND w.brand IN (#{brands}) </if></script>"})
    @Results({
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high"),
            @Result(property = "endTime", column = "end_time")
    })
    List<WantedVO> selectUntakedByFilter(@Param("brands") List<Integer> brands, @Param("price") Integer price);

}
