package com.faaadev.postari.service;

import com.faaadev.postari.model.Layanan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LayananList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Layanan> layanan;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Layanan> getLayanan() {
        return layanan;
    }

    public void setLayanan(List<Layanan> layanan) {
        this.layanan = layanan;
    }
}
