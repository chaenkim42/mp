package com.example.myapplication.Schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText title;
    ImageView sche_photo;
    DatePicker datePicker;
    Button add_btn, btn_make_sche;
    int dateID;
    int year, month, day;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_schedule_date);

        title = findViewById(R.id.title);
        start_date = findViewById(R.id.start);
        finish_date = findViewById(R.id.finish);
        add_btn = findViewById(R.id.add_user);
        btn_make_sche = findViewById(R.id.btn_make_sche);
        sche_photo = findViewById(R.id.sche_photo);

        title.setOnClickListener(this);
        start_date.setOnClickListener(this);
        finish_date.setOnClickListener(this);
        add_btn.setOnClickListener(this);
        btn_make_sche.setOnClickListener(this);
        getCurrentDate();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.title:
                title.setText("");
                break;

            case R.id.start:
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        start_date.setText(y + "/" + m + "/" + d );
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(this, callback, year, month, day);
                dialog.show();

                break;
            case R.id.finish:
                DatePickerDialog.OnDateSetListener callback_f = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        finish_date.setText(y + "/" + m + "/" + d );
                    }
                };
                DatePickerDialog dialog_f = new DatePickerDialog(this, callback_f, year, month, day);
                dialog_f.show();
                break;

            case R.id.add_user:
                break;

            case R.id.btn_make_sche:
                Intent intent = new Intent(AskScheduleDate.this, ScheduleForm.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("start_date", start_date.getText().toString());
                intent.putExtra("finish_date", finish_date.getText().toString());
                intent.putExtra("sche_n", 0);
                startActivity(intent);
                break;
        }
    }

    public void getCurrentDate(){
        Date current = Calendar.getInstance().getTime();
        SimpleDateFormat dayf = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat monthf = new SimpleDateFormat("mm", Locale.getDefault());
        SimpleDateFormat yearf = new SimpleDateFormat("yyyy", Locale.getDefault());

        year = Integer.parseInt(yearf.format(current));
        month = Integer.parseInt(monthf.format(current));
        day = Integer.parseInt(dayf.format(current));
    }
}
