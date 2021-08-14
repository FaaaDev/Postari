package com.faaadev.postari.service;

import com.faaadev.postari.model.Jadwal;
import com.faaadev.postari.model.Layanan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JadwalList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Jadwal> jadwal;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Jadwal> getJadwal() {
        return jadwal;
    }

    public void setJadwal(List<Jadwal> jadwal) {
        this.jadwal = jadwal;
    }
}
