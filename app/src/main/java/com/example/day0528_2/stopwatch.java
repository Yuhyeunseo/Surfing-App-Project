package com.example.day0528_2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class stopwatch extends AppCompatActivity {

    private TextView stopwatchTextView;
    private Button startButton, resetButton, homeBtn;
    private RecyclerView recordRecyclerView;
    private StoppedTimeAdapter adapter;

    private boolean isRunning = false;
    private long startTime = 0L;
    private long elapsedTime = 0L;
    private Handler handler = new Handler();
    private Runnable runnable;

    private ArrayList<String> stoppedTimes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        stopwatchTextView = findViewById(R.id.stopwatchTextView);
        startButton = findViewById(R.id.startButton);
        resetButton = findViewById(R.id.resetButton);
        recordRecyclerView = findViewById(R.id.recordRecyclerView);
        homeBtn = findViewById(R.id.homeBtn);

        // RecyclerView 설정
        adapter = new StoppedTimeAdapter(stoppedTimes);
        recordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recordRecyclerView.setAdapter(adapter);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        // 스톱워치 업데이트 Runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                elapsedTime = now - startTime;
                updateStopwatchText(elapsedTime);
                handler.postDelayed(this, 100);
            }
        };

        // 버튼 클릭 리스너 설정
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleStart();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReset();
            }
        });
    }

    private void handleStart() {
        if (!isRunning) {
            startTime = System.currentTimeMillis() - elapsedTime;
            handler.post(runnable);
            isRunning = true;
            startButton.setText("정지"); // 시작 버튼 텍스트 변경
        } else {
            handleStop();
        }
    }

    private void handleStop() {
        if (isRunning) {
            handler.removeCallbacks(runnable);
            isRunning = false;
            startButton.setText("시작"); // 정지 후 버튼 텍스트 변경
            // 시간 기록 추가
            String formattedTime = formatTime(elapsedTime);
            adapter.addTime(formattedTime);
        }
    }

    private void handleReset() {
        handler.removeCallbacks(runnable);
        isRunning = false;
        elapsedTime = 0L;
        updateStopwatchText(elapsedTime);
        stoppedTimes.clear();
        adapter.notifyDataSetChanged();
        startButton.setText("시작"); // 리셋 후 버튼 텍스트 초기화
    }

    private void updateStopwatchText(long time) {
        String formattedTime = formatTime(time);
        stopwatchTextView.setText(formattedTime);
    }

    private String formatTime(long time) {
        long totalSeconds = time / 1000;
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        // 밀리초는 표시하지 않음, 필요 시 추가 가능
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
}
