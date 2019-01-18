package com.xxf.client;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RequestUtil {

    public static <T> T sendRequest(String url, Class<T> resp, String params) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        AuthClient authClient = retrofit.create(AuthClient.class);
        return null;
    }

}
