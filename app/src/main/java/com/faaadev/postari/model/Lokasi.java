package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Lokasi {
    @SerializedName("id")
    private String id;
    @SerializedName("nama_posyandu")
    private String namaPosyandu;
    @SerializedName("alamat")
    private String alamat;

    public Lokasi(String id, String namaPosyandu, String alamat) {
        this.id = id;
        this.namaPosyandu = namaPosyandu;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaPosyandu() {
        return namaPosyandu;
    }

    public void setNamaPosyandu(String namaPosyandu) {
        this.namaPosyandu = namaPosyandu;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
