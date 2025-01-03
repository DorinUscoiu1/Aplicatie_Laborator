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

        // Obținem echipa din intent
        Team team = (Team) getIntent().getSerializableExtra("team");

        // Setăm informațiile despre echipă
        TextView teamNameTextView = findViewById(R.id.team_name);
        TextView teamDescriptionTextView = findViewById(R.id.team_description);

        teamNameTextView.setText(team.getName());
        teamDescriptionTextView.setText(team.getNationality());

        // Obținem lista de piloți pentru echipa respectivă
        List<Driver> driverList = getDriversForTeam(team);

        // Configurăm RecyclerView pentru piloți
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Adăugăm adapterul pentru piloți
        DriverAdapter adapter = new DriverAdapter(driverList, driver -> {
            Intent intent = new Intent(TeamDetailsActivity.this, DriverDetailsActivity.class);
            intent.putExtra("driver", driver);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
    }

    // Metodă pentru a obține piloții în funcție de echipă
    private List<Driver> getDriversForTeam(Team team) {
        List<Driver> driverList = new ArrayList<>();

        // Hardcodăm piloții pentru fiecare echipă
        switch (team.getName()) {
            case "Mercedes":
                driverList.add(new Driver("Lewis Hamilton", "Details about Lewis Hamilton", R.drawable.lewis_hamilton));
                driverList.add(new Driver("George Russell", "Details about George Russell", R.drawable.george_russell));
                break;
            case "Ferrari":
                driverList.add(new Driver("Charles Leclerc", "Details about Charles Leclerc", R.drawable.charles_leclerc));
                driverList.add(new Driver("Carlos Sainz", "Details about Carlos Sainz", R.drawable.carlos_sainz));
                break;
            case "Red Bull":
                driverList.add(new Driver("Max Verstappen", "Details about Max Verstappen", R.drawable.max_verstappen));
                driverList.add(new Driver("Sergio Perez", "Details about Sergio Perez", R.drawable.sergio_perez));
                break;
            case "Alpine F1 Team":
                driverList.add(new Driver("Esteban Ocon", "Details about Esteban Ocon", R.drawable.esteban_ocon));
                driverList.add(new Driver("Pierre Gasly", "Details about Pierre Gasly", R.drawable.pierre_gasly));
                break;
            case "McLaren":
                driverList.add(new Driver("Lando Norris", "Details about Lando Norris", R.drawable.lando_norris));
                driverList.add(new Driver("Oscar Piastri", "Details about Oscar Piastri", R.drawable.oscar_piastri));
                break;
            case "Aston Martin":
                driverList.add(new Driver("Fernando Alonso", "Details about Fernando Alonso", R.drawable.fernando_alonso));
                driverList.add(new Driver("Lance Stroll", "Details about Lance Stroll", R.drawable.lance_stroll));
                break;
            case "Sauber":
                driverList.add(new Driver("Valtteri Bottas", "Details about Valtteri Bottas", R.drawable.valtteri_bottas));
                driverList.add(new Driver("Zhou Guanyu", "Details about Zhou Guanyu", R.drawable.zhou_guanyu));
                break;
            case "Haas F1 Team":
                driverList.add(new Driver("Kevin Magnussen", "Details about Kevin Magnussen", R.drawable.kevin_magnussen));
                driverList.add(new Driver("Nico Hulkenberg", "Details about Nico Hulkenberg", R.drawable.nico_hulkenberg));
                break;
            case "RB F1 Team":
                driverList.add(new Driver("Yuki Tsunoda", "Details about Yuki Tsunoda", R.drawable.yuki_tsunoda));
                driverList.add(new Driver("Daniel Ricciardo", "Details about Daniel Ricciardo", R.drawable.daniel_ricciardo));
                break;
            case "Williams":
                driverList.add(new Driver("Alexander Albon", "Details about Alexander Albon", R.drawable.alex_albon));
                driverList.add(new Driver("Logan Sargeant", "Details about Logan Sargeant", R.drawable.logan_sargeant));
                break;
            default:
                //driverList.add(new Driver("Unknown Driver", "Details about Unknown Driver", R.drawable.placeholder_driver));
                break;
        }

        return driverList;
    }
}
