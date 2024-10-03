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
    Button btnWrite;
    String fileName;
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        dp = findViewById(R.id.datePicker);
        edtDialy = findViewById(R.id.edtDialy);
        btnWrite = findViewById(R.id.btnWrite2);
        homeBtn = findViewById(R.id.homeBtn);

        Calendar cal = Calendar.getInstance();
        int cYear = cal.get(Calendar.YEAR);
        int cMonth = cal.get(Calendar.MONTH);
        int cDay = cal.get(Calendar.DAY_OF_MONTH);
        dp.init(cYear, cMonth, cDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                fileName = Integer.toString(year) + "_" + Integer.toString(monthOfYear + 1)+ "_" + Integer.toString(dayOfMonth) + ".txt";
                String str = readDialy(fileName);
                edtDialy.setText(str);
                btnWrite.setEnabled(true);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileOutputStream outFs = openFileOutput(fileName, Context.MODE_PRIVATE);
                    String str = edtDialy.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), fileName+"이 저장됨", Toast.LENGTH_LONG).show();
                }catch (IOException e){

                }

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
    String readDialy(String fName)
    {
        String dialyStr = null;
        FileInputStream inf;
        try{
            inf = openFileInput(fName);
            byte[] txt = new byte[500];
            inf.read(txt);
            inf.close();
            dialyStr=(new String(txt)).trim();
            btnWrite.setText("수정하기");
        }catch (IOException e){
            edtDialy.setHint("일기 없음");
            btnWrite.setText("새로 저장");
        }
        return dialyStr;
    }
}