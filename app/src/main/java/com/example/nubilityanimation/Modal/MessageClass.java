package com.example.nubilityanimation.Modal;

public class MessageClass {
    private String sender_name,sender_message,send_time ,key;

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_message() {
        return sender_message;
    }

    public void setSender_message(String sender_message) {
        this.sender_message = sender_message;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageClass(String sender_name, String sender_message, String send_time) {
        this.sender_name = sender_name;
        this.sender_message = sender_message;
        this.send_time = send_time;
        this.key=key;
    }

    public MessageClass() {

    }

}
