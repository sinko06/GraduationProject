package com.example.gogoooma.graduationproject;

import java.io.Serializable;

public class Friend implements Serializable {
    String name;
    String phone;
    String picture;


    public Friend(String name, String phone, String picture) {
        this.name = name;
        this.phone = phone;
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

