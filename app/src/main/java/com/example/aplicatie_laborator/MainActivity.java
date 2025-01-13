package com.example.aplicatie_laborator;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://ergast.com/api/f1/";
    private List<Team> arrTeams = new ArrayList<>();
    private TeamAdapter teamAdapter;
    private TeamDatabase teamDatabase;
    private SearchView searchView;
    private Switch themeSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
        createNotificationChannel();
        showNextRaceNotification();

        ImageButton startQuizButton = findViewById(R.id.btn_start_quiz);
        ImageButton viewLeaderboardButton = findViewById(R.id.btn_view_leaderboard);
        ImageButton newsButton = findViewById(R.id.btn_news);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        teamDatabase = Room.databaseBuilder(getApplicationContext(), TeamDatabase.class, "team.db")
                .fallbackToDestructiveMigration()
                .build();
        loadTeamsFromFile();
       //loadTeamsFromDatabase();

        String url = BASE_URL + "2024/constructors.json";
        new AsyncTaskHandleJson().execute(url);

        teamAdapter = new TeamAdapter(arrTeams, team -> {
            Intent intent = new Intent(MainActivity.this, TeamDetailsActivity.class);
            intent.putExtra("team", team);
            startActivity(intent);
        });
        recyclerView.setAdapter(teamAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTeams(newText);
                return true;
            }
        });

        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        viewLeaderboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        newsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NewsActivity.class);
            startActivity(intent);
        });

        ImageButton standingsButton = findViewById(R.id.btn_standings);
        standingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StandingsActivity.class);
            startActivity(intent);
        });

        ImageButton calendarButton = findViewById(R.id.btn_calendar);
        calendarButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        ImageButton logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    private void filterTeams(String query) {
        List<Team> filteredTeams = new ArrayList<>();
        for (Team team : arrTeams) {
            if (team.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredTeams.add(team);
            }
        }
        teamAdapter.updateList(filteredTeams);
    }

    private void loadTeamsFromFile() {
        Gson gson = new Gson();
        try {
            FileInputStream fis = openFileInput("teams.json");
            InputStreamReader isr = new InputStreamReader(fis);
            StringBuilder json = new StringBuilder();
            int data = isr.read();
            while (data != -1) {
                json.append((char) data);
                data = isr.read();
            }
            isr.close();
            fis.close();

            Team[] teamArray = gson.fromJson(json.toString(), Team[].class);
            List<Team> teamList = Arrays.asList(teamArray);

            arrTeams.clear();
            arrTeams.addAll(teamList);
            runOnUiThread(() -> teamAdapter.notifyDataSetChanged());

            Toast.makeText(this, "Teams loaded from file", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void saveTeamsToFile(List<Team> teams) {
        Gson gson = new Gson();
        String json = gson.toJson(teams);
        try {
            FileOutputStream fos = openFileOutput("teams.json", MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void saveTeamsToDatabase(List<Team> teams) {
        new Thread(() -> teamDatabase.teamDao().insert(teams)).start();
    }

    private void loadTeamsFromDatabase() {
        new Thread(() -> {
            List<Team> teams = teamDatabase.teamDao().getAllTeams();
            arrTeams.clear();
            arrTeams.addAll(teams);
            runOnUiThread(() -> teamAdapter.notifyDataSetChanged());
        }).start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "F1_NOTIFICATIONS";
            String channelName = "F1 Updates";
            String channelDescription = "Notifications about F1 races";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint({"MissingPermission", "NotificationPermission"})
    private void showNextRaceNotification() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                        == PackageManager.PERMISSION_GRANTED) {

            // Detalii notificare
            String raceTitle = "Next F1 Race";
            String raceDetails = "16 Mar 2025, To be announced, Australia";

            // Creăm Intent pentru MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
            );
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "F1_NOTIFICATIONS")
                    .setSmallIcon(R.drawable.f1)
                    .setContentTitle(raceTitle)
                    .setContentText(raceDetails)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }


    private class AsyncTaskHandleJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) new URL(urls[0]).openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                int data = inputStreamReader.read();
                while (data != -1) {
                    result.append((char) data);
                    data = inputStreamReader.read();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            handleJson(result);
        }
    }

    private void handleJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Toast.makeText(this, "Received empty data", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonObject constructorTable = jsonObject.getAsJsonObject("MRData").getAsJsonObject("ConstructorTable");
            JsonArray constructors = constructorTable.getAsJsonArray("Constructors");

            List<Team> teamList = new ArrayList<>();
            for (int i = 0; i < constructors.size(); i++) {
                JsonObject constructor = constructors.get(i).getAsJsonObject();
                String name = constructor.get("name").getAsString();
                String nationality = constructor.get("nationality").getAsString();
                int logoResource = getTeamLogo(name);
                teamList.add(new Team(name, nationality, logoResource));
            }

            arrTeams.clear();
            arrTeams.addAll(teamList);
            teamAdapter.notifyDataSetChanged();
            saveTeamsToFile(teamList);
            saveTeamsToDatabase(teamList);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse data", Toast.LENGTH_SHORT).show();
        }
    }

    private int getTeamLogo(String teamName) {
        switch (teamName) {
            case "Mercedes":
                return R.drawable.mercedes;
            case "Ferrari":
                return R.drawable.ferrari;
            case "Red Bull":
                return R.drawable.redbull;
            case "McLaren":
                return R.drawable.mclaren;
            case "Aston Martin":
                return R.drawable.astonmartin;
            case "Alpine F1 Team":
                return R.drawable.alpine;
            case "Sauber":
                return R.drawable.sauber;
            case "Haas F1 Team":
                return R.drawable.haas;
            case "RB F1 Team":
                return R.drawable.rb;
            case "Williams":
                return R.drawable.williams;
            default:
                return R.drawable.redbull;
        }
    }
}
