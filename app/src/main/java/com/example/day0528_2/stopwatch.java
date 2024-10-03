package com.example.day0528_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class stopwatch extends AppCompatActivity {
    private TextView tvTimer;
    private Button btnStartPause, btnReset, homeBtn;
    private ListView lvRecords;
    private long startTime = 0L;
    private Handler timerHandler = new Handler();
    private boolean isRunning = false;
    private long updateTime = 0L;

    private List<String> records = new ArrayList<>();
    private ArrayAdapter<String> recordsAdapter;

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            tvTimer.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

            if (isRunning) {
                timerHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        tvTimer = findViewById(R.id.tvTimer);
        btnStartPause = findViewById(R.id.btnStartPause);
        btnReset = findViewById(R.id.btnReset);
        lvRecords = findViewById(R.id.lvRecords);
        homeBtn = findViewById(R.id.homeBtn);

        recordsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        lvRecords.setAdapter(recordsAdapter);

        btnStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    timerHandler.removeCallbacks(timerRunnable);
                    updateTime += System.currentTimeMillis() - startTime;
                    records.add(tvTimer.getText().toString());
                    recordsAdapter.notifyDataSetChanged();
                    btnStartPause.setText("Start");
                } else {
                    startTime = System.currentTimeMillis() - updateTime;
                    timerHandler.postDelayed(timerRunnable, 0);
                    btnStartPause.setText("Pause");
                }
                isRunning = !isRunning;
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerHandler.removeCallbacks(timerRunnable);
                isRunning = false;
                updateTime = 0L;
                tvTimer.setText("00:00:00");
                records.clear();
                recordsAdapter.notifyDataSetChanged();
                btnStartPause.setText("Start");
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
}