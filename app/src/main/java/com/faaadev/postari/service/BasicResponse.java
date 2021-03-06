package com.faaadev.postari.service;


import com.google.gson.annotations.SerializedName;

public class BasicResponse {
    @SerializedName("status")
    Boolean status;
    @SerializedName("message")
    String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
