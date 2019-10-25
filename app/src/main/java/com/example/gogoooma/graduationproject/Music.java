package com.example.gogoooma.graduationproject;

import java.util.Timer;

public class Music {
    String Title;
    float happy;

    public Music (String Title, float happy){
        this.Title = Title;
        this.happy = happy;
    }

    public String getTitle() {
        return Title;
    }

    public float getHappy() {
        return happy;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setHappy(float happy) {
        this.happy = happy;
    }
}
