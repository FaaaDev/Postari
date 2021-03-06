package com.faaadev.postari.service;

import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.model.User;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrtuList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Ortu> ortu;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ortu> getOrtu() {
        return ortu;
    }

    public void setOrtu(List<Ortu> ortu) {
        this.ortu = ortu;
    }
}
