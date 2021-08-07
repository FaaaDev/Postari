package com.faaadev.postari.service;

import com.faaadev.postari.model.Imunisasi;
import com.faaadev.postari.model.Penimbangan;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImunisasiList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Imunisasi> imunisasi;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Imunisasi> getImunisasi() {
        return imunisasi;
    }

    public void setImunisasi(List<Imunisasi> imunisasi) {
        this.imunisasi = imunisasi;
    }
}
