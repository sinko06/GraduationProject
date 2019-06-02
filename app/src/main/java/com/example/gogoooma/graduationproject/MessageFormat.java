package com.example.gogoooma.graduationproject;

public class MessageFormat {

    private String uniqueID;
    private String sender;
    private String receiver;
    private String type;
    private String data;
    private long time;

    public MessageFormat(String uniqueID, String sender, String receiver
            , String type, String data, long time) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.data = data;
        this.uniqueID = uniqueID;
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
