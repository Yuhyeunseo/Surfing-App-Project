package com.example.day0528_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class caloriecalculator extends AppCompatActivity {

    private EditText editTextHeight, editTextWeight, editTextDuration;
    private Button buttonCalculate, homeBtn;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caloriecalculator);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextDuration = findViewById(R.id.editTextDuration);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);
        homeBtn = findViewById(R.id.homeBtn);

        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void calculateCalories() {
        String heightStr = editTextHeight.getText().toString();
        String weightStr = editTextWeight.getText().toString();
        String durationStr = editTextDuration.getText().toString();

        if (heightStr.isEmpty() || weightStr.isEmpty() || durationStr.isEmpty()) {
            textViewResult.setText("모든 값을 입력해 주세요.");
            return;
        }

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        double duration = Double.parseDouble(durationStr);

        double caloriesBurned = calculateCaloriesBurned(height, weight, duration);

        textViewResult.setText("소모 칼로리: " + String.format("%.2f", caloriesBurned) + " kcal");
    }

    private double calculateCaloriesBurned(double height, double weight, double duration) {
        double metValue = 7.0;
        double caloriesBurned = (metValue * weight * duration/60);
        return caloriesBurned;
    }
}