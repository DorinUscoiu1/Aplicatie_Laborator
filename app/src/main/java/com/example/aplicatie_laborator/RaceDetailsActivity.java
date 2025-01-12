package com.example.aplicatie_laborator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RaceDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race_details);

        TextView raceNameTextView = findViewById(R.id.race_name);
        TextView raceDateTextView = findViewById(R.id.race_date);
        TextView circuitNameTextView = findViewById(R.id.circuit_name);
        RecyclerView resultsRecyclerView = findViewById(R.id.results_recycler_view);

        Race race = (Race) getIntent().getSerializableExtra("race");

        if (race != null) {
            raceNameTextView.setText(race.getRaceName());
            raceDateTextView.setText(race.getDate());
            circuitNameTextView.setText(race.getCircuitName());

            resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            resultsRecyclerView.setAdapter(new DriverResultAdapter(race.getResults()));
        }
    }
}
