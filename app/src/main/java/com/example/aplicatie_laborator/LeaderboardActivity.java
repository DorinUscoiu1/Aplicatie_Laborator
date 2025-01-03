package com.example.aplicatie_laborator;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicatie_laborator.F1Database;
import com.example.aplicatie_laborator.UserScore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LeaderboardActivity extends AppCompatActivity {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        ListView leaderboardListView = findViewById(R.id.leaderboard_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>());
        leaderboardListView.setAdapter(adapter);

        executorService.execute(() -> {
            List<UserScore> scores = F1Database.getDatabase(LeaderboardActivity.this).userScoreDao().getAllScoresSorted();
            List<String> leaderboard = new ArrayList<>();
            for (UserScore score : scores) {
                leaderboard.add(score.getUsername() + ": " + score.getScore());
            }

            mainThreadHandler.post(() -> {
                adapter.clear();
                adapter.addAll(leaderboard);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}