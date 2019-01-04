package com.xxf.service;

import com.xxf.entity.DetailVO;
import com.xxf.entity.Wanted;
import com.xxf.entity.WantedVO;

import java.util.List;

public interface WantedService {

    /**
     * 查询所有发布记录
     *
     * @return
     */
    List<Wanted> listAllWanted();

    /**
     * 查询所有可用记录
     *
     * @return
     */
    List<WantedVO> listAllUntaked(List<Integer> brands, Integer priceHigh);

    /**
     * 查询某一条记录的所有信息
     *
     * @param id
     * @return
     */
    DetailVO getDetail(int id);

    /**
     * 新增记录
     *
     * @param userId
     * @param wanted
     * @return
     */
    void addNewWanted(int userId, Wanted wanted);

    /**
     * 下订单
     * @param wantedId
     * @param taked
     * @param takedUserId
     */
    void changeWantedStatus(int wantedId, int taked, int takedUserId);
}
