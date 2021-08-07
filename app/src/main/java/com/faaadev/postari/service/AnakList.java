package com.faaadev.postari.service;

import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Layanan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnakList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Anak> anak;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Anak> getAnak() {
        return anak;
    }

    public void setAnak(List<Anak> anak) {
        this.anak = anak;
    }
}
