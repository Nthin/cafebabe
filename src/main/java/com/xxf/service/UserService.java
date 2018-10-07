package com.xxf.service;

import com.xxf.entity.User;
import com.xxf.entity.Wanted;

import java.util.List;

public interface UserService {

    /**
     * 所有用户列表
     * @return
     */
    List<User> listAllUsers();

    /**
     * 根据用户id查询该用户的所有订单
     * @param id
     * @return
     */
    List<Wanted> getAllWantedByUserId(int id);

}
