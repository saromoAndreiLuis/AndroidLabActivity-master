package com.example.androidlabactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AverageResult extends AppCompatActivity {

    TextView resultTextView;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_average_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTextView = findViewById(R.id.averageResult);
        backButton = findViewById(R.id.backButton);


        Intent intent = getIntent();
        double average = intent.getDoubleExtra("average", 0);

        String result = "Average: " + average + "\n" + (average >= 75 ? "Passed" : "Failed");

        resultTextView.setText(result);

        backButton.setOnClickListener(v -> finish());
    }

}