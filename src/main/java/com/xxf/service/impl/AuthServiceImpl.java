package com.xxf.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xxf.client.AuthClient;
import com.xxf.entity.CafeException;
import com.xxf.entity.auth.*;
import com.xxf.mapper.AuthMapper;
import com.xxf.mapper.UserMapper;
import com.xxf.service.AuthService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private static final String GRANT_TYPE_AUTH = "authorization_code";
    private static final String GRANT_TYPE_ACCESS_TOKEN = "client_credential";
    private static final String AUTH_URL = "https://api.weixin.qq.com/sns/";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/";
    private static final int DEFAULT_EXPIRE_TIME = 7000;

    private static String appId;
    private static String secret;

    private static Cache<String, String> tokenCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS)
            .maximumSize(1)
            .build();

    private AuthMapper authMapper;
    private UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(AuthMapper authMapper, UserMapper userMapper) {
        this.authMapper = authMapper;
        this.userMapper = userMapper;
    }

    @PostConstruct
    private void getAuthCode() {
        AuthCode authCode = authMapper.select();
        appId = authCode.getAppId();
        secret = authCode.getAppSecret();
    }

    @Override
    public AuthResponse login(String code) {
        AuthResponse resp = getAuthResp(code);
        if (StringUtils.isBlank(resp.getOpenId())) {
            throw new CafeException("login fail, openId is empty");
        }
//        newUserIfNotExist(resp.getOpenId());
        return resp;
    }

    @Override
    public void sendUniformMsg(String openId, String formId, Map<String, String> params) {
        try {
            String token = tokenCache.get("token", AuthServiceImpl::getAccessToken);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ACCESS_TOKEN_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
            AuthClient authClient = retrofit.create(AuthClient.class);
            WeAppTemplateMsg weAppTemplateMsg = new WeAppTemplateMsg(formId, formatData(params));
            TemplateMsg templateMsg = new TemplateMsg(openId, weAppTemplateMsg);
            ObjectMapper mapper = new ObjectMapper();
            log.info("templateMsg : {}", mapper.writeValueAsString(templateMsg));
            UniformMsgResponse resp = authClient.sendUniformMsg(token, templateMsg).execute().body();
            log.info("resp : {}", resp);
            if (resp == null) {
                throw new CafeException("sendUniformMsg fail, resp is null");
            }
            if (resp.getErrCode() != 0) {
                throw new CafeException(resp.getErrCode(), resp.getErrMsg());
            }
        } catch (Exception e) {
            throw new CafeException(e);
        }
    }

    private AuthResponse getAuthResp(String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AUTH_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        AuthClient authClient = retrofit.create(AuthClient.class);
        AuthResponse resp;
        try {
            resp = authClient.getAuthResponse(appId, secret, code, GRANT_TYPE_AUTH).execute().body();
            if (resp == null) {
                throw new CafeException("getAuthResp fail, resp is null");
            }
            if (resp.getErrCode() != 0) {
                throw new CafeException(resp.getErrCode(), resp.getErrMsg());
            }
        } catch (IOException e) {
            throw new CafeException(e);
        }
        return resp;
    }

    private static String getAccessToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ACCESS_TOKEN_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        AuthClient authClient = retrofit.create(AuthClient.class);
        AccessResponse resp;
        try {
            resp = authClient.getAccessResponse(appId, secret, GRANT_TYPE_ACCESS_TOKEN).execute().body();
            if (resp == null) {
                throw new CafeException("AccessResponse fail, resp is null");
            }
            if (resp.getErrCode() != 0) {
                throw new CafeException(resp.getErrCode(), resp.getErrMsg());
            }
        } catch (IOException e) {
            throw new CafeException(e);
        }
        String token = resp.getAccessToken();
        if (StringUtils.isBlank(token)) {
            throw new CafeException("access fail, token is empty");
        }
        return token;
    }

    private Map<String, Map<String, String>> formatData(Map<String, String> params) throws JsonProcessingException {
        Map<String, Map<String, String>> dataMap = new LinkedHashMap<>();
        String brand = params.get("brand");
        String size = params.get("size");
        String price = params.get("price");
        String nickname = params.get("nickname");
        DateFormat df = new SimpleDateFormat("MM-dd HH:mm:ss");
        String time = df.format(new Date());

        Map<String, String> map = new LinkedHashMap<>();
        map.put("value", brand + "拼单");
        dataMap.put("keyword1", map);
        Map<String, String> map2 = new LinkedHashMap<>();
        map2.put("value", brand + " " + size + "杯");
        dataMap.put("keyword2", map2);
        Map<String, String> map3 = new LinkedHashMap<>();
        map3.put("value", price + "元");
        dataMap.put("keyword3", map3);
        Map<String, String> map4 = new LinkedHashMap<>();
        map4.put("value", nickname);
        dataMap.put("keyword4", map4);
        Map<String, String> map5 = new LinkedHashMap<>();
        map5.put("value", time);
        dataMap.put("keyword5", map5);
        return dataMap;
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
