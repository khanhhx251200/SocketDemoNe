package com.example.socketdemo.model;

public class Message {
    private String username;
    private String message;
    private boolean isMe;

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public Message(String username, String message, boolean isMe) {
        this.username = username;
        this.message = message;
        this.isMe = isMe;
    }

    public Message(String message, boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
