package com.faaadev.postari.http;

import com.faaadev.postari.service.Auth;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.service.UserList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @GET("get-user-list.php")
    Call<UserList> getUserList();

    @FormUrlEncoded
    @POST("auth.php")
    Call<Auth> goAuth(@Field("user_id") String user_id,
                      @Field("password") String password);

    @FormUrlEncoded
    @POST("add-user.php")
    Call<BasicResponse> addUser(@Field("user_id") String user_id,
                                @Field("username") String username,
                                @Field("password") String password,
                                @Field("role") String role,
                                @Field("image") String image);

    @FormUrlEncoded
    @POST("auth.php")
    Call<BasicResponse> addLoc(@Field("nama_posyandu") String name,
                      @Field("alamat") String alamat);
}
