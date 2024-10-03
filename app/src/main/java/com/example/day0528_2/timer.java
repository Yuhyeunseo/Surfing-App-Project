package com.example.day0528_2;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class timer extends AppCompatActivity {
    private EditText hourET, minuteET, secondET;
    private TextView hourTV, minuteTV, secondTV, finishTV;
    private LinearLayout timeCountSettingLV, timeCountLV;
    private Button startBtn, stopBtn, resetBtn, homeBtn;

    private CountDownTimer countDownTimer;
    private long timeInMillis;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        hourET = findViewById(R.id.hourET);
        minuteET = findViewById(R.id.minuteET);
        secondET = findViewById(R.id.secondET);
        hourTV = findViewById(R.id.hourTV);
        minuteTV = findViewById(R.id.minuteTV);
        secondTV = findViewById(R.id.secondTV);
        finishTV = findViewById(R.id.finishTV);
        timeCountSettingLV = findViewById(R.id.timeCountSettingLV);
        timeCountLV = findViewById(R.id.timeCountLV);
        startBtn = findViewById(R.id.startBtn);
        stopBtn = findViewById(R.id.stopBtn);
        resetBtn = findViewById(R.id.resettBtn);
        homeBtn = findViewById(R.id.homeBtn);

        startBtn.setOnClickListener(v -> startTimer());
        stopBtn.setOnClickListener(v -> stopTimer());
        resetBtn.setOnClickListener(v -> resetTimer());

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void startTimer() {
        if (!timerRunning) {
            if (timeInMillis == 0) {
                String hours = hourET.getText().toString();
                String minutes = minuteET.getText().toString();
                String seconds = secondET.getText().toString();

                if (TextUtils.isEmpty(hours)) hours = "0";
                if (TextUtils.isEmpty(minutes)) minutes = "0";
                if (TextUtils.isEmpty(seconds)) seconds = "0";

                timeInMillis = Integer.parseInt(hours) * 3600000 +
                        Integer.parseInt(minutes) * 60000 +
                        Integer.parseInt(seconds) * 1000;
            }

            timeCountSettingLV.setVisibility(View.GONE);
            timeCountLV.setVisibility(View.VISIBLE);
            startCountDown();
        }
    }


    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                finishTV.setText("타이머 종료");
                startBtn.setText("시작");
                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
            }
        }.start();

        timerRunning = true;
        startBtn.setText("계속");
        startBtn.setEnabled(false);
        stopBtn.setEnabled(true);
        resetBtn.setEnabled(true);
    }

    private void stopTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            startBtn.setEnabled(true);
            stopBtn.setEnabled(false);
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeInMillis = 0;
        timerRunning = false;
        updateCountDownText();
        timeCountSettingLV.setVisibility(View.VISIBLE);
        timeCountLV.setVisibility(View.GONE);
        startBtn.setText("시작");
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        resetBtn.setEnabled(false);
        finishTV.setText("");

        hourET.setText("00");
        minuteET.setText("00");
        secondET.setText("00");
        hourTV.setText("00");
        minuteTV.setText("00");
        secondTV.setText("00");
    }

    private void updateCountDownText() {
        int hours = (int) (timeInMillis / 1000) / 3600;
        int minutes = (int) ((timeInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        String[] timeParts = timeLeftFormatted.split(":");

        hourTV.setText(timeParts[0]);
        minuteTV.setText(timeParts[1]);
        secondTV.setText(timeParts[2]);
    }
}