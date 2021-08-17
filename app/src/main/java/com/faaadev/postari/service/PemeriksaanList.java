package com.faaadev.postari.service;

import com.faaadev.postari.model.Pemeriksaan;
import com.faaadev.postari.model.Penimbangan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PemeriksaanList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Pemeriksaan> pemeriksaan;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Pemeriksaan> getPemeriksaan() {
        return pemeriksaan;
    }

    public void setPemeriksaan(List<Pemeriksaan> pemeriksaan) {
        this.pemeriksaan = pemeriksaan;
    }
}
