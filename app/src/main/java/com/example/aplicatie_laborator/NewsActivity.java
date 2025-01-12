package com.example.aplicatie_laborator;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }

    public void openNewsDetails1(android.view.View view) {
        openNewsDetails("Red Bull Dominates Pre-Season Testing",
                "Red Bull has shown impressive speed during the 2025 pre-season testing in Bahrain. " +
                        "Max Verstappen set the fastest time of the weekend, and Sergio Perez was close behind. " +
                        "Analysts believe Red Bull is the team to beat this season.");
    }

    public void openNewsDetails2(android.view.View view) {
        openNewsDetails("Ferrari Introduces New Power Unit",
                "Ferrari has unveiled a revolutionary power unit for the 2025 season. " +
                        "The team promises better reliability and more horsepower, addressing their weaknesses from last year. " +
                        "Fans are eager to see if this will bring them closer to the top.");
    }

    public void openNewsDetails3(android.view.View view) {
        openNewsDetails("Mercedes Tests New Aerodynamic Package",
                "Mercedes is experimenting with a new aerodynamic package that could improve cornering speed significantly. " +
                        "The team is optimistic about their chances for the 2025 season after a challenging 2024.");
    }

    public void openNewsDetails4(android.view.View view) {
        openNewsDetails("Alpine Focuses on Performance in 2025",
                "Alpine is focusing on improving their overall performance in 2025. With a revamped chassis and enhanced power unit, " +
                        "the team hopes to challenge for higher positions in the standings.");
    }

    public void openNewsDetails5(android.view.View view) {
        openNewsDetails("Aston Martin's New Strategy for 2025",
                "Aston Martin has unveiled a new strategy for the 2025 season. The team is focusing on improving car stability " +
                        "and aerodynamics to catch up with the front-runners.");
    }

    public void openNewsDetails6(android.view.View view) {
        openNewsDetails("McLaren Launches Bold New Livery",
                "McLaren has revealed a bold new livery for their 2025 F1 car. The new design is said to improve visibility " +
                        "and align with the team's refreshed brand identity.");
    }

    private void openNewsDetails(String title, String details) {
        Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("details", details);
        startActivity(intent);
    }
}
