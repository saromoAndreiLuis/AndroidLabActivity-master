package com.example.androidlabactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ComputeAverage extends AppCompatActivity {

    EditText mathGrade, filipinoGrade, scienceGrade, mapehGrade, englishGrade;
    Button submitButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compute_average);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mathGrade = findViewById(R.id.mathGrade);
        filipinoGrade = findViewById(R.id.filipinoGrade);
        scienceGrade = findViewById(R.id.scienceGrade);
        mapehGrade = findViewById(R.id.mapehGrade);
        englishGrade = findViewById(R.id.englishGrade);
        submitButton = findViewById(R.id.submitButton);
        backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                computeAverage();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void computeAverage() {
        String mathStr = mathGrade.getText().toString();
        String filipinoStr = filipinoGrade.getText().toString();
        String scienceStr = scienceGrade.getText().toString();
        String mapehStr = mapehGrade.getText().toString();
        String englishStr = englishGrade.getText().toString();

        if (TextUtils.isEmpty(mathStr) || TextUtils.isEmpty(filipinoStr) || TextUtils.isEmpty(scienceStr) || TextUtils.isEmpty(mapehStr) || TextUtils.isEmpty(englishStr)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double math = Double.parseDouble(mathStr);
        double filipino = Double.parseDouble(filipinoStr);
        double science = Double.parseDouble(scienceStr);
        double mapeh = Double.parseDouble(mapehStr);
        double english = Double.parseDouble(englishStr);

        if (math < 65 || math > 100 || filipino < 65 || filipino > 100 || science < 65 || science > 100 || mapeh < 65 || mapeh > 100 || english < 65 || english > 100) {
            Toast.makeText(this, "Grades must be between 65 and 100", Toast.LENGTH_SHORT).show();
            return;
        }

        double average = (math + filipino + science + mapeh + english) / 5;

        Intent intent = new Intent(ComputeAverage.this, AverageResult.class);
        intent.putExtra("average", average);
        startActivity(intent);
    }
}