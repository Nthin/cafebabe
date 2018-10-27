package com.xxf.service;

import com.xxf.entity.auth.AuthResponse;

public interface AuthService {

    /**
     * 获取openId（临时方案）
     * @param code
     * @return
     */
    AuthResponse login(String code);

}
