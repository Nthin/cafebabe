package com.xxf.service.impl;

import com.xxf.client.AuthClient;
import com.xxf.entity.auth.AuthCode;
import com.xxf.entity.auth.AuthResponse;
import com.xxf.mapper.AuthMapper;
import com.xxf.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String GRANT_TYPE = "authorization_code";

    @Autowired
    private AuthMapper authMapper;

    @Override
    public AuthResponse getAuthValue(String code) {
        AuthCode authCode = authMapper.select();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weixin.qq.com/sns/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        AuthClient authClient = retrofit.create(AuthClient.class);
        try {
            Response<AuthResponse> response = authClient.getResponse(authCode.getAppId(), authCode.getAppSecret(), code, GRANT_TYPE).execute();
            if (response.code() >= 400) {
                log.error(response.errorBody().toString());
                return null;
            }
            return response.body();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
