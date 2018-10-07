package com.xxf.mapper;

import com.xxf.entity.WantedVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WantedVOMapper {

    @Select({"SELECT w.id, u.nickname, u.position FROM wanted w LEFT JOIN record r ON w.id = r.wantedId LEFT JOIN user u ON r.userId = u.id where w.taked = 0 and w.end_time > NOW()"})
    List<WantedVO> selectUntaked();

}
