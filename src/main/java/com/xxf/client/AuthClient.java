package com.xxf.client;

import com.xxf.entity.auth.AuthResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthClient {

    @GET("jscode2session")
    Call<AuthResponse> getResponse(@Query("appid") String appId, @Query("secret") String secret, @Query("js_code") String jsCode, @Query("grant_type") String grantType);

}
