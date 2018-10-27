package com.xxf.service.impl;

import com.xxf.client.AuthClient;
import com.xxf.entity.CafeException;
import com.xxf.entity.auth.AuthCode;
import com.xxf.entity.auth.AuthResponse;
import com.xxf.mapper.AuthMapper;
import com.xxf.mapper.UserMapper;
import com.xxf.service.AuthService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String GRANT_TYPE = "authorization_code";
    private static final String AUTH_URL = "https://api.weixin.qq.com/sns/";

    private AuthMapper authMapper;

    private UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(AuthMapper authMapper, UserMapper userMapper) {
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    @Override
    public AuthResponse login(String code) {
        AuthResponse resp = getAuthResp(code);
        if (resp.getOpenId() == null) {
            throw new CafeException(400, "getAuthResp fail, openId is null");
        }
//        newUserIfNotExist(resp.getOpenId());
        return resp;
    }

    private AuthResponse getAuthResp(String code) {
        AuthCode authCode = authMapper.select();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        AuthClient authClient = retrofit.create(AuthClient.class);
        AuthResponse resp;
        try {
            resp = authClient.getResponse(authCode.getAppId(), authCode.getAppSecret(), code, GRANT_TYPE).execute().body();
            if (resp == null) {
                throw new CafeException(400, "getAuthResp fail, resp is null");
            }
            if (resp.getErrCode() != 0) {
                throw new CafeException(resp.getErrCode(), resp.getErrMsg());
            }
        } catch (IOException e) {
            throw new CafeException(400, e);
        }
        return resp;
    }

    private void newUserIfNotExist(@NonNull String openId) {
        if (userMapper.selectOne(openId) == null) {
//            userMapper.insert(new User(openId));
        }
    }

    private String generate3rdSession(String openId, String sessionKey) {
        String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(sessionKey).append("#").append(openId);
        } finally {
            sb.delete(0, sb.length());
        }
        return thirdSessionKey;
    }
}
