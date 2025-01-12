package com.example.aplicatie_laborator;

import java.io.Serializable;

public class DriverResult implements Serializable {
    private String driverName;
    private String position;
    private String team;

    public DriverResult(String driverName, String position, String team) {
        this.driverName = driverName;
        this.position = position;
        this.team = team;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getPosition() {
        return position;
    }

    public String getTeam() {
        return team;
    }
}
