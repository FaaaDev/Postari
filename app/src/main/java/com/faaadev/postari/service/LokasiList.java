package com.faaadev.postari.service;

import com.faaadev.postari.model.Lokasi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LokasiList {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<Lokasi> lokasi;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Lokasi> getLokasi() {
        return lokasi;
    }

    public void setLokasi(List<Lokasi> lokasi) {
        this.lokasi = lokasi;
    }
}
