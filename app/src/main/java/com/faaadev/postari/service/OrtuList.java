package com.faaadev.postari.service;

import com.faaadev.postari.model.Ortu;
import com.faaadev.postari.model.User;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrtuList {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<Ortu> ortu;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ortu> getOrtu() {
        return ortu;
    }

    public void setOrtu(List<Ortu> ortu) {
        this.ortu = ortu;
    }
}
