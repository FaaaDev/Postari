package com.faaadev.postari.service;

import com.faaadev.postari.model.Anak;
import com.faaadev.postari.model.Chat;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatList {
    @SerializedName("status")
    boolean success;
    @SerializedName("data")
    List<Chat> chat;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Chat> getChat() {
        return chat;
    }

    public void setChat(List<Chat> chat) {
        this.chat = chat;
    }
}
