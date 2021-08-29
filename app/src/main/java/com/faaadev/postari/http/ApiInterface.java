package com.faaadev.postari.http;

import com.faaadev.postari.service.AnakList;
import com.faaadev.postari.service.Auth;
import com.faaadev.postari.service.BasicResponse;
import com.faaadev.postari.service.ImunisasiList;
import com.faaadev.postari.service.JadwalList;
import com.faaadev.postari.service.LayananList;
import com.faaadev.postari.service.LokasiList;
import com.faaadev.postari.service.Ortu;
import com.faaadev.postari.service.OrtuList;
import com.faaadev.postari.service.PemeriksaanList;
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
    @POST("get-ortu-by-id.php")
    Call<Ortu> getOrtuById(@Field("user_id") String user_id);

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

    @FormUrlEncoded
    @POST("add-jadwal.php")
    Call<BasicResponse> addJadwal(@Field("id_lokasi") String id_lokasi,
                                  @Field("tanggal") String tanggal,
                                  @Field("kegiatan") String kegiatan);

    @GET("get-jadwal-list.php")
    Call<JadwalList> getJadwalList();

    @FormUrlEncoded
    @POST("add-pemeriksaan.php")
    Call<BasicResponse> addPemeriksaan(@Field("user_id") String user_id,
                                       @Field("tanggal") String tanggal,
                                       @Field("keluhan") String keluhan,
                                       @Field("tekanan_darah") String tekanan,
                                       @Field("bb_ibu") String berat,
                                       @Field("umur_hamil") String umur,
                                       @Field("tinggi_fundus") String tinggi,
                                       @Field("letak_janin") String letak,
                                       @Field("denyut_janin") String denyut,
                                       @Field("kaki_bengkak") String bengkak,
                                       @Field("pem_laboratorium") String pemLab,
                                       @Field("tindakan") String tindakan,
                                       @Field("nasihat") String nasihat,
                                       @Field("pemeriksa") String pemeriksa,
                                       @Field("tanggal_periksa_kembali") String periksaKembali);

    @FormUrlEncoded
    @POST("get-pemeriksaan-list.php")
    Call<PemeriksaanList> getPemeriksaanList(@Field("user_id") String user_id);

    @GET("get-petugas-list.php")
    Call<UserList> getPetugas();

    @FormUrlEncoded
    @POST("add-chat-list.php")
    Call<BasicResponse> addChat(@Field("id_sender") String sender_id,
                                @Field("id_receiver") String user_id);

    @FormUrlEncoded
    @POST("add-chat-detail.php")
    Call<BasicResponse> addToChatRoom(@Field("chat_id") String chat_id,
                                      @Field("sender") String sender,
                                      @Field("message") String message);
}
