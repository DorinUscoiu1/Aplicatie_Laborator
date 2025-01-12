package com.example.aplicatie_laborator;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StandingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DriverAdapter driverAdapter;
    private TeamAdapter teamAdapter;
    private List<Driver> driverList = new ArrayList<>();
    private List<Team> teamList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);

        recyclerView = findViewById(R.id.recycler_view_standings);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button driverStandingsButton = findViewById(R.id.btn_driver_standings);
        Button teamStandingsButton = findViewById(R.id.btn_team_standings);

        driverAdapter = new DriverAdapter(driverList, driver -> {});
        teamAdapter = new TeamAdapter(teamList, team -> {});

        driverStandingsButton.setOnClickListener(v -> showDriverStandings());
        teamStandingsButton.setOnClickListener(v -> showTeamStandings());

        showDriverStandings();
    }

    private void showDriverStandings() {
        driverList.clear();
        driverList.addAll(fetchDriverData());
        recyclerView.setAdapter(driverAdapter);
    }

    private void showTeamStandings() {
        teamList.clear();
        teamList.addAll(fetchTeamData());
        recyclerView.setAdapter(teamAdapter);
    }

    private List<Driver> fetchDriverData() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Max Verstappen", "437", R.drawable.max_verstappen));
        drivers.add(new Driver("Lando Norris", "374", R.drawable.lando_norris));
        drivers.add(new Driver("Charles Leclerc", "356", R.drawable.charles_leclerc));
        drivers.add(new Driver("Oscar Piastri", "292", R.drawable.oscar_piastri));
        drivers.add(new Driver("Carlos Sainz", "290", R.drawable.carlos_sainz));
        drivers.add(new Driver("George Russell", "245", R.drawable.george_russell));
        drivers.add(new Driver("Lewis Hamilton", "223", R.drawable.lewis_hamilton));
        drivers.add(new Driver("Sergio Perez", "152", R.drawable.sergio_perez));
        drivers.add(new Driver("Fernando Alonso", "70", R.drawable.fernando_alonso));
        drivers.add(new Driver("Pierre Gasly", "42", R.drawable.pierre_gasly));

        drivers.add(new Driver("Nico Hulkenberg", "41", R.drawable.nico_hulkenberg));

        drivers.add(new Driver("Yuki Tsunoda", "30", R.drawable.yuki_tsunoda));

        drivers.add(new Driver("Lance Stroll", "24", R.drawable.lance_stroll));
        drivers.add(new Driver("Esteban Ocon", "23", R.drawable.esteban_ocon));
        drivers.add(new Driver("Kevin Magnussen","16",R.drawable.kevin_magnussen));

        drivers.add(new Driver("Alexander Albon", "12", R.drawable.alex_albon));

        drivers.add(new Driver("Daniel Ricciardo", "12", R.drawable.alex_albon));

        drivers.add(new Driver("Oliver Bearman", "7", R.drawable.oliver_bearman));

        drivers.add(new Driver("Franco Colapinto", "5", R.drawable.franco_colapinto));

        drivers.add(new Driver("Guanyu Zhou", "4", R.drawable.zhou_guanyu));
        drivers.add(new Driver("Liam Lawson", "4", R.drawable.liam_lawson));
        drivers.add(new Driver("Valtteri Bottas", "0", R.drawable.valtteri_bottas));
        drivers.add(new Driver("Logan Sargeant", "0", R.drawable.logan_sargeant));
        drivers.add(new Driver("Jack Doohan","0",R.drawable.jack_doohan));
        return drivers;
    }

    private List<Team> fetchTeamData() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("McLaren", "666", R.drawable.mclaren));
        teams.add(new Team("Ferrari", "652", R.drawable.ferrari));
        teams.add(new Team("Red Bull Racing", "589", R.drawable.redbull));
        teams.add(new Team("Mercedes", "468", R.drawable.mercedes));
        teams.add(new Team("Aston Martin", "94", R.drawable.astonmartin));
        teams.add(new Team("Alpine", "65", R.drawable.alpine));
        teams.add(new Team("Haas", "58", R.drawable.haas));
        teams.add(new Team("AlphaTauri", "46", R.drawable.rb));
        teams.add(new Team("Williams", "17", R.drawable.williams));
        teams.add(new Team("Alfa Romeo", "4", R.drawable.sauber));
        return teams;
    }
}
