package com.faaadev.postari.service;

import com.faaadev.postari.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserList {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<User> user;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

}
