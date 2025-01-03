package com.example.aplicatie_laborator;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        createNotificationChannel();
        showNextRaceNotification();

        Button startQuizButton = findViewById(R.id.btn_start_quiz);
        Button viewLeaderboardButton = findViewById(R.id.btn_view_leaderboard);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Team> teamList = new ArrayList<>();
        teamList.add(new Team("Mercedes", "Details about Mercedes", R.drawable.mercedes));
        teamList.add(new Team("Red Bull", "Details about Red Bull", R.drawable.redbull));
        teamList.add(new Team("Ferrari", "Details about Ferrari", R.drawable.ferrari));

        TeamAdapter adapter = new TeamAdapter(teamList, team -> {
            Intent intent = new Intent(MainActivity.this, TeamDetailsActivity.class);
            intent.putExtra("team", team);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        startQuizButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });

        viewLeaderboardButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(intent);
        });
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
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
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
