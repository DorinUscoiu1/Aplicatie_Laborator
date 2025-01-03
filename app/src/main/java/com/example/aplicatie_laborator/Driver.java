package com.example.aplicatie_laborator;

import java.io.Serializable;

public class Driver implements Serializable {
    private String name;
    private String description;
    private int imageResourceId;

    public Driver(String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
