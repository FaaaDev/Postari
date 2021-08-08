package com.faaadev.postari.http;

import com.faaadev.postari.service.AnakList;
import com.faaadev.postari.service.Auth;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.service.ImunisasiList;
import com.faaadev.postari.service.LayananList;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.service.OrtuList;
import com.faaadev.postari.service.PenimbanganList;
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
    @POST("add-lokasi.php")
    Call<BasicResponse> addLoc(@Field("nama_posyandu") String name,
                               @Field("alamat") String alamat);

    @GET("get-lokasi-list.php")
    Call<LokasiList> getLokasiList();

    @FormUrlEncoded
    @POST("add-ortu.php")
    Call<BasicResponse> addOrtu(@Field("user_id") String user_id,
                                @Field("nama_ibu") String mom_name,
                                @Field("nama_suami") String dad_name,
                                @Field("alamat") String alamat,
                                @Field("posyandu") String posyandu,
                                @Field("layanan") String layanan);

    @GET("get-ortu-list.php")
    Call<OrtuList> getOrtuList();

    @FormUrlEncoded
    @POST("get-ortu-list.php")
    Call<OrtuList> getOrtuListWithParam(@Field("layanan") String layanan);

    @FormUrlEncoded
    @POST("get-layanan-list.php")
    Call<LayananList> getLayananList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("get-anak-list.php")
    Call<AnakList> getAnakWithParam(@Field("user_id") String user_id,
                                   @Field("layanan") String layanan);

    @FormUrlEncoded
    @POST("add-anak.php")
    Call<BasicResponse> addAnak(@Field("user_id") String user_id,
                                @Field("nama") String nama,
                                @Field("birthdate") String birthdate,
                                @Field("gender") String gender);

    @GET("get-anak-list.php")
    Call<AnakList> getAnakList();

    @FormUrlEncoded
    @POST("add-penimbangan.php")
    Call<BasicResponse> addPenimbangan(@Field("id_anak") String id_anak,
                                       @Field("bb_anak") String bb_anak,
                                       @Field("tb_anak") int tb_anak,
                                       @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("add-imunisasi.php")
    Call<BasicResponse> addImunisasi(@Field("id_anak") String id_anak,
                                     @Field("type") String type,
                                     @Field("keterangan") String keterangan,
                                     @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("get-penimbangan-list.php")
    Call<PenimbanganList> getPenimbanganList(@Field("id_anak") String id_anak);

    @FormUrlEncoded
    @POST("get-imunisasi-list.php")
    Call<ImunisasiList> getImunisasiList(@Field("id_anak") String id_anak);
}
