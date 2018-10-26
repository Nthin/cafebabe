package com.xxf.service.impl;

import com.xxf.entity.CafeException;
import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public User getUserDetails(String openId) {
        return userMapper.selectOne(openId);
    }

    @Override
    public void newUser(User user) {
        int result = userMapper.insert(user);
        if (result != 1) {
            throw new CafeException(400, "Failed to create new user : " + user);
        }
    }

    @Override
    public List<Wanted> getAllWantedByUserId(int id) {
        List<Integer> records = userMapper.selectFromRecord(id);
        return records.stream().map(wantedId -> wantedMapper.selectOne(wantedId))
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(int id, String position, String phone, String wechat) {
        int result = userMapper.update(id, position, phone, wechat);
        if (result != 1) {
            throw new CafeException(400, "Failed to update user : " + id);
        }
    }
}
