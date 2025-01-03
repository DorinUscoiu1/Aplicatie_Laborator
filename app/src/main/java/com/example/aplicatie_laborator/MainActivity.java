package com.example.aplicatie_laborator;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://ergast.com/api/f1/"; // BASE_URL corectat (fără fișierul JSON)
    private List<Team> arrTeams = new ArrayList<>();
    private TeamAdapter teamAdapter;
    private TeamDatabase teamDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        createNotificationChannel();
        showNextRaceNotification();
        Button startQuizButton = findViewById(R.id.btn_start_quiz);
        Button viewLeaderboardButton = findViewById(R.id.btn_view_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inițializare baza de date locală
        teamDatabase = Room.databaseBuilder(getApplicationContext(), TeamDatabase.class, "team.db").build();

        // Apel API pentru a obține constructorii
        String url = BASE_URL + "2024/constructors.json";
        new AsyncTaskHandleJson().execute(url);

        // Inițializare adapter
        teamAdapter = new TeamAdapter(arrTeams, team -> {
            Intent intent = new Intent(MainActivity.this, TeamDetailsActivity.class);
            intent.putExtra("team", team);
            startActivity(intent);
        });

        recyclerView.setAdapter(teamAdapter);

        // Buton pentru a începe quiz-ul
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        // Buton pentru a vizualiza leaderboard-ul
        viewLeaderboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });

        // Încărcarea echipelor din baza de date locală
        loadTeamsFromDatabase();
    }

    // AsyncTask pentru a prelua datele de la API
    private class AsyncTaskHandleJson extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);

                InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
                int data = inputStreamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = inputStreamReader.read();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            handleJson(result);
        }
    }

    private void handleJson(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            Toast.makeText(this, "Received empty data", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            // Analizează JSON-ul și obține echipele
            JsonObject jsonObject = JsonParser.parseString(jsonString).getAsJsonObject();
            JsonObject constructorTable = jsonObject.getAsJsonObject("MRData")
                    .getAsJsonObject("ConstructorTable");
            JsonArray constructors = constructorTable.getAsJsonArray("Constructors");

            List<Team> teamList = new ArrayList<>();

            // Prelucrarea fiecărei echipe
            for (int i = 0; i < constructors.size(); i++) {
                JsonObject constructor = constructors.get(i).getAsJsonObject();
                String name = constructor.get("name").getAsString();
                String nationality = constructor.get("nationality").getAsString();
                String logoUrl = "URL_LOGO"; // Placeholder pentru logo, trebuie adăugat mai târziu

                teamList.add(new Team(name, nationality, logoUrl));
            }

            arrTeams.clear();
            arrTeams.addAll(teamList);
            teamAdapter.notifyDataSetChanged();

            // Salvăm echipele în baza de date
            saveTeamsToDatabase(teamList);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse data", Toast.LENGTH_SHORT).show();
        }
    }


    private void saveTeamsToDatabase(List<Team> teams) {
        new Thread(() -> {
            teamDatabase.teamDao().insert(teams);
        }).start();
    }

    private void loadTeamsFromDatabase() {
        new Thread(() -> {
            List<Team> teams = teamDatabase.teamDao().getAllTeams();
            arrTeams.clear();
            arrTeams.addAll(teams);

            runOnUiThread(() -> {
                if (teamAdapter != null) {
                    teamAdapter.notifyDataSetChanged();
                }
            });
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

            String raceTitle = "Next F1 Race";
            String raceDetails = "16 Mar 2025, To be announced, Australia";

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "F1_NOTIFICATIONS")
                    .setSmallIcon(R.drawable.f1)
                    .setContentTitle(raceTitle)
                    .setContentText(raceDetails)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }
    }
}
