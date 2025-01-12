package com.example.aplicatie_laborator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_scores")
public class UserScore {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String username;

    private int score;

    public UserScore(int id, String username, int score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
