package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Layanan {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("nama")
    private String nama;

    public Layanan(String id, String user_id, String nama) {
        this.id = id;
        this.user_id = user_id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
