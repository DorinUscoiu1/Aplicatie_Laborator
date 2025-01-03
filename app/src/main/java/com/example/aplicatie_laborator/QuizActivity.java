package com.example.aplicatie_laborator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicatie_laborator.F1Database;
import com.example.aplicatie_laborator.UserScore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizActivity extends AppCompatActivity {

    private int score = 0;
    private int currentQuestionIndex = 0;

    private final String[] questions = {
            "Who won the 2021 F1 Championship?",
            "Which team has the most F1 championships?",
            "What is the maximum number of points a driver can score in one race?",
            "Which country hosts the Monaco Grand Prix?",
            "What year was the first F1 season?"
    };

    private final String[][] options = {
            {"Lewis Hamilton", "Max Verstappen", "Sebastian Vettel"},
            {"Ferrari", "Mercedes", "Red Bull"},
            {"25", "26", "30"},
            {"France", "Monaco", "Italy"},
            {"1948", "1950", "1952"}
    };

    private final int[] correctAnswers = {1, 0, 1, 1, 1};

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        updateQuestion();

        Button submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> {
            RadioGroup optionsGroup = findViewById(R.id.options_group);
            int selectedId = optionsGroup.getCheckedRadioButtonId();

            if (selectedId != -1) {
                RadioButton selectedOption = findViewById(selectedId);
                int selectedIndex = optionsGroup.indexOfChild(selectedOption);

                if (selectedIndex == correctAnswers[currentQuestionIndex]) {
                    score++;
                }

                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    updateQuestion();
                } else {
                    saveScore(score);
                    Intent intent = new Intent(QuizActivity.this, LeaderboardActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(QuizActivity.this, "Please select an answer!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateQuestion() {
        TextView questionTextView = findViewById(R.id.question_text);
        RadioGroup optionsGroup = findViewById(R.id.options_group);

        questionTextView.setText(questions[currentQuestionIndex]);
        optionsGroup.removeAllViews();

        for (String option : options[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsGroup.addView(radioButton);
        }
    }

    private void saveScore(int score) {
        String username = "User " + System.currentTimeMillis(); // Replace with actual user input if needed
        UserScore userScore = new UserScore(0, username, score);

        executorService.execute(() -> {
            F1Database.getDatabase(QuizActivity.this).userScoreDao().insert(userScore);
            mainThreadHandler.post(() -> Toast.makeText(QuizActivity.this, "Score saved successfully!", Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
