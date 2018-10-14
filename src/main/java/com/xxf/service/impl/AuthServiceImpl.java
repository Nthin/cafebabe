package com.xxf.service.impl;

import com.xxf.entity.auth.AuthCode;
import com.xxf.mapper.AuthMapper;
import com.xxf.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public AuthCode getAuthValue() {
        return authMapper.select();
    }
}
