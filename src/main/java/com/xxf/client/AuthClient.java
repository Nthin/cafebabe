package com.xxf.client;

import com.xxf.entity.auth.AuthResponse;
import retrofit2.Call;
import retrofit2.http.HTTP;
import retrofit2.http.Query;

public interface AuthClient {

    @HTTP(method = "GET", path = "sns/jscode2session")
    Call<AuthResponse> getResponse(@Query("appid") String appId, @Query("secret") String secret, @Query("jscode") String jsCode, @Query("granttype") String grantType);
}
