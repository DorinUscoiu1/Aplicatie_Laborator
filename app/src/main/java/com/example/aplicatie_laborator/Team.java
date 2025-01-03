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
    private String logoUrl;


    // Constructor
    public Team(String name, String nationality, String logoUrl) {
        this.name = name;
        this.nationality = nationality;
        this.logoUrl = logoUrl;
    }

    // Getter și setter pentru id, name, nationality și logoUrl
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
