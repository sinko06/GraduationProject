package com.example.gogoooma.graduationproject;

import java.util.Comparator;

public class Emotion implements Comparable<Emotion> {
    String name;
    int score;

    public Emotion(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Emotion e) {
        if (this.score < e.getScore()) {
            return -1;
        } else if (this.score > e.getScore()) {
            return 1;
        }
        return 0;
    }
}
