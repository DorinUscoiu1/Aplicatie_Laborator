package com.example.aplicatie_laborator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.aplicatie_laborator.DriverAdapter;
import com.example.aplicatie_laborator.Driver;
import com.example.aplicatie_laborator.Team;
import java.util.ArrayList;
import java.util.List;

public class TeamDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        Team team = (Team) getIntent().getSerializableExtra("team");

        TextView teamNameTextView = findViewById(R.id.team_name);
        TextView teamDescriptionTextView = findViewById(R.id.team_description);

        teamNameTextView.setText(team.getName());
        teamDescriptionTextView.setText(team.getDescription());

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Driver> driverList = new ArrayList<>();
        driverList.add(new Driver("Lewis Hamilton", "Details about Lewis Hamilton", R.drawable.lewis_hamilton));
        driverList.add(new Driver("George Russell", "Details about George Russell", R.drawable.george_russell));

        DriverAdapter adapter = new DriverAdapter(driverList, driver -> {
            Intent intent = new Intent(TeamDetailsActivity.this, DriverDetailsActivity.class);
            intent.putExtra("driver", driver);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }
}
