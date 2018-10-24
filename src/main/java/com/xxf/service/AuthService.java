package com.xxf.service;

import com.xxf.entity.auth.AuthResponse;

public interface AuthService {

    AuthResponse login(String code);

}
