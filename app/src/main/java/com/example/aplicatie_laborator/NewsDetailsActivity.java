package com.example.aplicatie_laborator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        String title = getIntent().getStringExtra("title");
        String details = getIntent().getStringExtra("details");

        TextView newsTitle = findViewById(R.id.news_title);
        TextView newsDetails = findViewById(R.id.news_details);

        newsTitle.setText(title);
        newsDetails.setText(details);
    }
}
