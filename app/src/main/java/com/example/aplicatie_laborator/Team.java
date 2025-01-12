package com.example.aplicatie_laborator;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "teams")
public class Team implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String nationality;
    private int logoResourceId;


    // Constructor
    public Team(String name, String nationality, int logoResourceId) {
        this.name = name;
        this.nationality = nationality;
        this.logoResourceId = logoResourceId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getLogoResourceId() {
        return logoResourceId;
    }

    public void setLogoResourceId(int logoResourceId) {
        this.logoResourceId = logoResourceId;
    }
}
