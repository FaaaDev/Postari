package com.faaadev.postari.service;

import com.faaadev.postari.model.User;
import com.google.gson.annotations.SerializedName;

public class Auth {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    User user;
    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
