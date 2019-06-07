package com.example.gogoooma.graduationproject;

public class Talk {
    String dbName;
    String friendName;
    String lastMsg;
    long time;

    public Talk(String dbName, String friendName, String lastMsg, long time) {
        this.dbName = dbName;
        this.lastMsg = lastMsg;
        this.time = time;
        this.friendName = friendName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
