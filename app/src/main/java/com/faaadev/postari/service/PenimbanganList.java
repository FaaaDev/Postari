package com.faaadev.postari.service;

import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Penimbangan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PenimbanganList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Penimbangan> penimbangan;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Penimbangan> getPenimbangan() {
        return penimbangan;
    }

    public void setPenimbangan(List<Penimbangan> penimbangan) {
        this.penimbangan = penimbangan;
    }
}
