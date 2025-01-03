package com.example.aplicatie_laborator;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.aplicatie_laborator.Driver;

public class DriverDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        Driver driver = (Driver) getIntent().getSerializableExtra("driver");

        TextView driverNameTextView = findViewById(R.id.driver_name);
        TextView driverDescriptionTextView = findViewById(R.id.driver_description);
        ImageView driverImageView = findViewById(R.id.driver_image);

        driverNameTextView.setText(driver.getName());
        driverDescriptionTextView.setText(driver.getDescription());
        driverImageView.setImageResource(driver.getImageResourceId());
    }
}
