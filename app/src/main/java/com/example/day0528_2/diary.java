package com.example.day0528_2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class diary extends AppCompatActivity {
    DatePicker dp;
    EditText edtDialy;
    Button btnSave;
    String fileName;
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dp = findViewById(R.id.datePicker);
        edtDialy = findViewById(R.id.edtDialy);
        btnSave = findViewById(R.id.btnWrite2);
        homeBtn = findViewById(R.id.homeBtn);


        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 날짜가 선택될 때마다 파일 이름 생성 및 기존 일기 읽기
        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = year + "_" + (monthOfYear + 1) + "_" + dayOfMonth + ".txt";
                String str = readDialy(fileName);
                edtDialy.setText(str);
                btnSave.setEnabled(true);
            }
        });

        // 저장 버튼 클릭 시 파일에 일기 저장
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDiary(fileName);
            }
        });


    }

    // 일기 읽기 함수
    String readDialy(String fName) {
        String dialyStr = null;
        FileInputStream inf;
        try {
            inf = openFileInput(fName);
            byte[] txt = new byte[500];
            inf.read(txt);
            inf.close();
            dialyStr = (new String(txt)).trim();
            btnSave.setText("수정");  // 이미 일기가 있으면 "수정"으로 변경
        } catch (IOException e) {
            edtDialy.setHint("No diary found");
            btnSave.setText("저장");  // 새로 작성할 경우 "저장"으로 설정
        }
        return dialyStr;
    }

    // 일기 저장 함수
    void saveDiary(String fName) {
        // 파일 이름이 null이 아닌지 확인
        if (fName == null || fName.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Invalid file name", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 파일 저장 경로 확인 및 저장
            FileOutputStream outFs = openFileOutput(fName, Context.MODE_PRIVATE);
            String str = edtDialy.getText().toString();

            // 입력란이 비어있지 않은지 확인
            if (str.trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Diary content is empty", Toast.LENGTH_SHORT).show();
                return;
            }

            outFs.write(str.getBytes());
            outFs.close();
            Toast.makeText(getApplicationContext(), fName + " saved", Toast.LENGTH_LONG).show();
            btnSave.setText("수정");  // 저장 후에는 "수정"으로 변경
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error saving diary", Toast.LENGTH_LONG).show();  // 오류 발생 시 토스트 메시지 출력
        }
    }

}
