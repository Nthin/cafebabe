package com.xxf.mapper;

import com.xxf.entity.WantedVO;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WantedVOMapper {

    @Select({"SELECT w.id, w.brand, w.address, u.nickname, w.price_high, w.end_time, w.latitude, w.longitude FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id where w.taked = 0 and w.end_time > CURRENT_TIME()"})
    @Results({
            @Result(property = "priceLow", column = "price_low"),
            @Result(property = "priceHigh", column = "price_high"),
            @Result(property = "endTime", column = "end_time")
    })
    List<WantedVO> selectUntaked();

}
