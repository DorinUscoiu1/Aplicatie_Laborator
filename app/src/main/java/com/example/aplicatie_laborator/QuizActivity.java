package com.example.aplicatie_laborator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aplicatie_laborator.F1Database;
import com.example.aplicatie_laborator.UserScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuizActivity extends AppCompatActivity {

    private int score = 0;
    private int currentQuestionIndex = 0;

    private final String[] allQuestions = {
            "Who won the 2024 Drivers' Championship?",
            "Which team secured the 2024 Constructors' Championship?",
            "Who claimed pole position at the 2024 Italian Grand Prix?",
            "How many races were held in the 2024 season?",
            "Which driver made their debut in the 2024 season?",
            "Who won the 2024 Monaco Grand Prix?",
            "Which circuit hosted the final race of the 2024 season?",
            "Who won the 2024 Belgian Grand Prix?",
            "Which team had the most podiums in 2024?",
            "Who set the fastest lap in the 2024 Brazilian Grand Prix?",
            "Which driver retired mid-season in 2024?",
            "How many points did the Constructors' Champion score?",
            "Which driver won the most races in 2024?",
            "Which rookie scored the highest points in 2024?",
            "What was the length of the 2024 Monaco Grand Prix circuit?"
    };

    private final String[][] allOptions = {
            {"Max Verstappen", "Lewis Hamilton", "Oscar Piastri"},
            {"Red Bull Racing", "Ferrari", "McLaren"},
            {"Charles Leclerc", "Lando Norris", "Carlos Sainz"},
            {"22", "23", "24"},
            {"Liam Lawson", "Logan Sargeant", "Franco Colapinto"},
            {"Lando Norris", "Charles Leclerc", "George Russell"},
            {"Abu Dhabi", "Brazil", "Japan"},
            {"Max Verstappen", "Lewis Hamilton", "Charles Leclerc"},
            {"Ferrari", "Red Bull Racing", "McLaren"},
            {"Lando Norris", "Max Verstappen", "George Russell"},
            {"Fernando Alonso", "Kevin Magnussen", "Daniel Ricciardo"},
            {"600", "666", "700"},
            {"Max Verstappen", "Lando Norris", "Charles Leclerc"},
            {"Franco Colapinto", "Liam Lawson", "Oliver Bearman"},
            {"3.337 km", "3.337 miles", "3.667 km"}
    };

    private final int[] allCorrectAnswers = {0, 2, 1, 1, 2, 1, 0, 1, 0, 1, 2, 1, 0, 2, 0};

    private String[] selectedQuestions;
    private String[][] selectedOptions;
    private int[] selectedCorrectAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        progressBar.setMax(7);

        selectRandomQuestions();
        updateQuestion(progressBar);

        Button submitButton = findViewById(R.id.btn_submit);
        submitButton.setOnClickListener(v -> handleSubmission(progressBar));
    }

    private void selectRandomQuestions() {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < allQuestions.length; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        selectedQuestions = new String[7];
        selectedOptions = new String[7][];
        selectedCorrectAnswers = new int[7];

        for (int i = 0; i < 7; i++) {
            int index = indices.get(i);
            selectedQuestions[i] = allQuestions[index];
            selectedOptions[i] = allOptions[index];
            selectedCorrectAnswers[i] = allCorrectAnswers[index];
        }
    }

    private void handleSubmission(ProgressBar progressBar) {
        RadioGroup optionsGroup = findViewById(R.id.options_group);
        int selectedId = optionsGroup.getCheckedRadioButtonId();

        if (selectedId != -1) {
            RadioButton selectedOption = findViewById(selectedId);
            int selectedIndex = optionsGroup.indexOfChild(selectedOption);

            if (selectedIndex == selectedCorrectAnswers[currentQuestionIndex]) {
                score++;
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < selectedQuestions.length) {
                updateQuestion(progressBar);
            } else {
                saveScore(score);
                Intent intent = new Intent(QuizActivity.this, LeaderboardActivity.class);
                intent.putExtra("FINAL_SCORE", score);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(QuizActivity.this, "You must select an answer to continue!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateQuestion(ProgressBar progressBar) {
        if (currentQuestionIndex >= selectedQuestions.length) {
            Toast.makeText(this, "No more questions available.", Toast.LENGTH_SHORT).show();
            return;
        }

        TextView questionTextView = findViewById(R.id.question_text);
        RadioGroup optionsGroup = findViewById(R.id.options_group);

        questionTextView.setText(selectedQuestions[currentQuestionIndex]);
        optionsGroup.removeAllViews();
        optionsGroup.clearCheck();

        for (String option : selectedOptions[currentQuestionIndex]) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            radioButton.setTextSize(16);
            radioButton.setPadding(8, 16, 8, 16);
            optionsGroup.addView(radioButton);
        }

        if (progressBar != null) {
            progressBar.setProgress(currentQuestionIndex + 1);
        }
    }

    private void saveScore(int score) {
        String username = "User_" + System.currentTimeMillis();
        UserScore userScore = new UserScore(0, username, score);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        executorService.execute(() -> {
            F1Database.getDatabase(QuizActivity.this).userScoreDao().insert(userScore);
            mainThreadHandler.post(() ->
                    Toast.makeText(QuizActivity.this, "Final Score: " + score, Toast.LENGTH_SHORT).show()
            );
        });
    }
}
