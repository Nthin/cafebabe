package com.xxf.mapper;

import com.xxf.entity.WantedVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WantedVOMapper {

    @Select({"SELECT w.id, w.brand, w.address, u.nickname, u.avatarUrl FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id where w.taked = 0 and w.end_time > CURRENT_TIME()"})
    List<WantedVO> selectUntaked();

    @Select({"SELECT w.id, w.brand, w.address, u.nickname, u.avatarUrl FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id where w.taked = 0 and w.brand = #{brand} and w.end_time > CURRENT_TIME()"})
    List<WantedVO> selectUntakedByBrand(int brand);

}
