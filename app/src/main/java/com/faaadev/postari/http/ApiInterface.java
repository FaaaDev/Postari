package com.faaadev.postari.http;

import com.faaadev.postari.service.Auth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("auth.php")
    Call<Auth> goAuth(@Field("user_id") String user_id,
                      @Field("password") String password);
}
