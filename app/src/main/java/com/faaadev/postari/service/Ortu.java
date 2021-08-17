package com.faaadev.postari.service;

import com.google.gson.annotations.SerializedName;

public class Ortu {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    com.faaadev.postari.model.Ortu ortu;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public com.faaadev.postari.model.Ortu getOrtu() {
        return ortu;
    }

    public void setOrtu(com.faaadev.postari.model.Ortu ortu) {
        this.ortu = ortu;
    }
}
