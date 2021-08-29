package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    private String id;
    @SerializedName("id_sender")
    private String idSender;
    @SerializedName("id_receiver")
    private String idReceiver;

    public Chat(String id, String idSender, String idReceiver) {
        this.id = id;
        this.idSender = idSender;
        this.idReceiver = idReceiver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }
}
