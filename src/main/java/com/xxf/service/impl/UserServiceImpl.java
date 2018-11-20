package com.xxf.service.impl;

import com.xxf.entity.CafeException;
import com.xxf.entity.User;
import com.xxf.entity.Wanted;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedMapper;
import com.xxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private WantedMapper wantedMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WantedMapper wantedMapper) {
        this.userMapper = userMapper;
        this.wantedMapper = wantedMapper;
    }

    @Override
    public List<User> listAllUsers() {
        return userMapper.selectAll();
    }

    @Override
    public User getUserDetails(String openId) {
        User user = userMapper.selectOne(openId);
        if (user == null) {
            throw new CafeException(HttpStatus.NOT_FOUND.value(), "User not found by openId : " + openId);
        }
        return user;
    }

    @Override
    public void newUser(User user) {
        int result = userMapper.insert(user);
        if (result != 1) {
            throw new CafeException("Failed to create new user : " + user);
        }
    }

    @Override
    public List<Wanted> getAllWantedByUserId(int id) {
        List<Integer> records = userMapper.selectFromRecord(id);
        return records.stream().map(wantedId -> wantedMapper.selectOne(wantedId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Wanted> getAllUntakedByUserId(int id) {
        List<Integer> records = userMapper.selectFromRecord(id);
        return records.stream().map(wantedId -> wantedMapper.selectOne(wantedId))
                .filter(wanted -> wanted.getTaked() == 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<Wanted> getAllTakedByUserId(int id) {
        List<Integer> records = userMapper.selectFromRecord(id);
        return records.stream().map(wantedId -> wantedMapper.selectOne(wantedId))
                .filter(wanted -> wanted.getTaked() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUser(int id, String position, String phone) {
        int result = userMapper.update(id, position, phone);
        if (result != 1) {
            throw new CafeException("Failed to update user : " + id);
        }
    }
}
