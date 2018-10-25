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
    List<WantedVO> listAllUntaked();

    /**
     * 根据品牌筛选可用记录
     * @param brand
     * @return
     */
    List<WantedVO> listUntakedByBrand(int brand);

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
     * @param wanted
     * @return
     */
    boolean addNewWanted(Wanted wanted, int userId);

    /**
     * 下订单
     *
     * @param id
     * @param taked
     * @return
     */
    boolean changeWantedStatus(int id, int taked);
}
