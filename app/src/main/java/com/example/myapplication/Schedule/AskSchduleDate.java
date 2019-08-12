package com.example.myapplication.Schedule;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class AskSchduleDate extends AppCompatActivity implements View.OnClickListener {
    TextView start_date, finish_date;
    DatePicker datePicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_date);

        start_date = findViewById(R.id.start);
        finish_date = findViewById(R.id.finish);
        datePicker = findViewById(R.id.datePicker);
        start_date.setOnClickListener(this);
        finish_date.setOnClickListener(this);
        datePicker.init(2019, 8, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                datePicker.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        datePicker.setVisibility(View.VISIBLE);
    }
}
