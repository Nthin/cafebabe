package com.xxf.mapper;

import com.xxf.common.SimpleSelectInExtendedLanguageDriver;
import com.xxf.entity.WantedVO;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WantedVOMapper {

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select({"<script> SELECT w.id, w.brand, w.address, u.nickname, w.price_high, w.end_time, w.latitude, w.longitude, r.add_time " +
            "FROM wanted w LEFT JOIN record r ON w.id = r.wanted_id LEFT JOIN user u ON r.add_user_id = u.id " +
            "WHERE w.taked = 0 " +
            "AND w.end_time > CURRENT_TIME() " +
            "<if test='price != null'>AND w.price_high &lt;= #{price} </if>" +
            "<if test='brands != null'>AND w.brand IN (#{brands}) </if></script>"})
    List<WantedVO> selectUntakedByFilter(@Param("brands") List<Integer> brands, @Param("price") Integer price);

}
