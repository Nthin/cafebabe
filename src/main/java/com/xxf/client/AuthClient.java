package com.xxf.client;

import com.xxf.entity.auth.AccessResponse;
import com.xxf.entity.auth.AuthResponse;
import com.xxf.entity.auth.TemplateMsg;
import com.xxf.entity.auth.UniformMsgResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthClient {

    @GET("jscode2session")
    Call<AuthResponse> getAuthResponse(@Query("appid") String appId, @Query("secret") String secret, @Query("js_code") String jsCode, @Query("grant_type") String grantType);

    @GET("token")
    Call<AccessResponse> getAccessResponse(@Query("appid") String appId, @Query("secret") String secret, @Query("grant_type") String grantType);

    @POST("message/wxopen/template/uniform_send")
    Call<UniformMsgResponse> sendUniformMsg(@Query("access_token") String accessToken, @Body TemplateMsg msg);

}
