package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Anak {
    @SerializedName("id")
    private String id;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("nama")
    private String nama;
    @SerializedName("birthdate")
    private String birthdate;
    @SerializedName("gender")
    private String gender;

    public Anak(String id, String user_id, String nama, String birthdate, String gender) {
        this.id = id;
        this.user_id = user_id;
        this.nama = nama;
        this.birthdate = birthdate;
        this.gender = gender;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
