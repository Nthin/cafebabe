package com.xxf.service;

import com.xxf.entity.auth.AuthResponse;

import java.util.Map;

public interface AuthService {

    /**
     * 获取openId（临时方案）
     *
     * @param code
     * @return
     */
    AuthResponse login(String code);

    void sendUniformMsg(String openId, String formId, Map<String, String> params);

}
