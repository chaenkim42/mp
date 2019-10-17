package com.example.myapplication.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AskScheduleDate extends AppCompatActivity implements View.OnClickListener {
    TextView start_date, finish_date;
    DatePicker datePicker;
    Button add_btn;
    int dateID;
    String year, month, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_schedule_date);

        start_date = findViewById(R.id.start);
        finish_date = findViewById(R.id.finish);
        add_btn = findViewById(R.id.add);
        datePicker = findViewById(R.id.datePicker);

        start_date.setOnClickListener(this);
        finish_date.setOnClickListener(this);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AskScheduleDate.this, ScheduleForm.class));
            }
        });

        getCurrentDate();
        datePicker.init(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                if(dateID==start_date.getId())  start_date.setText(year+"-"+month+"-"+day);
                if(dateID==finish_date.getId()) finish_date.setText(year+"-"+month+"-"+day);

                datePicker.setVisibility(View.GONE);
                add_btn.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onClick(View view) {
        datePicker.setVisibility(View.VISIBLE);
        add_btn.setVisibility(View.GONE);
        dateID=view.getId();
    }

    public void getCurrentDate(){
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat dayf = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthf = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat yearf = new SimpleDateFormat("yyyy", Locale.getDefault());

        year = yearf.format(current);
        month = monthf.format(current);
        day = dayf.format(current);
    }
}
