package com.faaadev.postari.model;

import com.google.gson.annotations.SerializedName;

public class Chat {
    @SerializedName("id")
    private String id;
    @SerializedName("sender_id")
    private String senderId;
    @SerializedName("receiver_id")
    private String receiverId;
    @SerializedName("sender_name")
    private String senderName;
    @SerializedName("receiver_name")
    private String receiverName;
    @SerializedName("sender_image")
    private String senderImage;
    @SerializedName("receiver_image")
    private String receiverImage;
    @SerializedName("sender_role")
    private String senderRole;
    @SerializedName("receiver_role")
    private String receiverRole;
    @SerializedName("messages")
    private String message;

    public Chat(String senderId, String receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getReceiverImage() {
        return receiverImage;
    }

    public void setReceiverImage(String receiverImage) {
        this.receiverImage = receiverImage;
    }

    public String getSenderRole() {
        return senderRole;
    }

    public void setSenderRole(String senderRole) {
        this.senderRole = senderRole;
    }

    public String getReceiverRole() {
        return receiverRole;
    }

    public void setReceiverRole(String receiverRole) {
        this.receiverRole = receiverRole;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
