package com.example.aplicatie_laborator;
import java.io.Serializable;
import java.util.List;

public class Race implements Serializable {
    private String raceName;
    private String date;
    private String circuitName;
    private List<DriverResult> results;

    public Race(String raceName, String date, String circuitName, List<DriverResult> results) {
        this.raceName = raceName;
        this.date = date;
        this.circuitName = circuitName;
        this.results = results;
    }

    public String getRaceName() {
        return raceName;
    }

    public String getDate() {
        return date;
    }

    public String getCircuitName() {
        return circuitName;
    }

    public List<DriverResult> getResults() {
        return results;
    }

    public void setResults(List<DriverResult> results) {
        this.results = results;
    }
}
