package com.xxf.service.impl;

import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WantedMapper wantedMapper;

    @Override
    public List<User> listAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public List<Wanted> getAllWantedByUserId(int id) {


        return null;
    }
}
