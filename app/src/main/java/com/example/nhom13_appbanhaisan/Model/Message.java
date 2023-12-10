package com.example.nhom13_appbanhaisan.Model;

import com.google.firebase.database.Exclude;

public class Message {
    @Exclude
    private String id;
    private String senderId;
    private String text;

    public Message() {
    }

    public Message(String senderId, String text) {
        this.senderId = senderId;
        this.text = text;
    }

    @Exclude public String getId() {
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}

