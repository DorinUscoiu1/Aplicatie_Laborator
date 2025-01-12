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
            "Who won the 2024 Drivers' Championship?",
            "Which team secured the 2024 Constructors' Championship?",
            "Who claimed pole position at the 2024 Italian Grand Prix?",
            "How many races were held in the 2024 season?",
            "Which driver made their debut in the 2024 season?"
    };

    private final String[][] options = {
            {"Max Verstappen", "Lewis Hamilton", "Oscar Piastri"},
            {"Red Bull Racing", "Ferrari", "Mclaren"},
            {"Charles Leclerc", "Lando Norris", "Carlos Sainz"},
            {"22", "23", "24"},
            {"Liam Lawson", "Logan Sargeant", "Franco Colapinto"}
    };

    private final int[] correctAnswers = {0, 2, 1, 1, 2};



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
        String username = "User " + System.currentTimeMillis();
        UserScore userScore = new UserScore(0, username, score);

        executorService.execute(() -> {
            F1Database.getDatabase(QuizActivity.this).userScoreDao().insert(userScore);
            mainThreadHandler.post(() -> Toast.makeText(QuizActivity.this, "Score: "+score, Toast.LENGTH_SHORT).show());
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
