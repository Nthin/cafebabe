package com.xxf.service.impl;

import com.xxf.entity.CafeException;
import com.xxf.entity.User;
import com.xxf.entity.WantedVO;
import com.xxf.mapper.UserMapper;
import com.xxf.mapper.WantedVOMapper;
import com.xxf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    private WantedVOMapper wantedVOMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WantedVOMapper wantedVOMapper) {
        this.userMapper = userMapper;
        this.wantedVOMapper = wantedVOMapper;
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
        if (result == 1) {
            log.info("new user = {}", user);
            return;
        }
        if (result == 2) {
            log.info("update user = {}", user);
            return;
        }
        throw new CafeException("Failed to create new user : " + user);
    }

    @Override
    public List<WantedVO> getAllWantedByUserId(int id) {
        return wantedVOMapper.selectWantedVOByUserId(id, null);
    }

    @Override
    public List<WantedVO> getAllUntakedByUserId(int id) {
        return wantedVOMapper.selectWantedVOByUserId(id, 0);
    }

    @Override
    public List<WantedVO> getAllTakedByUserId(int id) {
        return wantedVOMapper.selectWantedVOByUserId(id, 1);
    }

    @Override
    public void updateUser(int id, String position, String phone) {
        int result = userMapper.update(id, position, phone);
        if (result != 1) {
            throw new CafeException("Failed to update user : " + id);
        }
        log.info("updateUser(), userId = {}, phone = {}", id, phone);
    }
}
