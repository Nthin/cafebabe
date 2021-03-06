package com.xxf.service;

import com.xxf.entity.User;
import com.xxf.entity.WantedVO;

import java.util.List;

public interface UserService {

    /**
     * 所有用户列表
     *
     * @return
     */
    List<User> listAllUsers();

    /**
     * 用户详情
     *
     * @param openId
     * @return
     */
    User getUserDetails(String openId);

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    void newUser(User user);

    /**
     * 根据用户id查询该用户的所有订单
     *
     * @param id
     * @return
     */
    List<WantedVO> getAllWantedByUserId(int id);

    /**
     * 根据用户id查询该用户的所有可用订单
     *
     * @param id
     * @return
     */
    List<WantedVO> getAllUntakedByUserId(int id);

    /**
     * 根据用户id查询该用户的所有历史订单
     *
     * @param id
     * @return
     */
    List<WantedVO> getAllTakedByUserId(int id);

    /**
     * 补全用户资料
     *
     * @param id
     * @param position
     * @param phone
     */
    void updateUser(int id, String position, String phone);
}
