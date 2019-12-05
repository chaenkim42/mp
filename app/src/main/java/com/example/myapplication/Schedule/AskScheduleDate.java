package com.example.myapplication.Schedule;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.Day;
import com.example.myapplication.Database.DayDb;
import com.example.myapplication.Database.NewPlace;
import com.example.myapplication.Database.Place;
import com.example.myapplication.Database.ScheduleDb;
import com.example.myapplication.Database.User;
import com.example.myapplication.Database.UserDb;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class AskScheduleDate extends AppCompatActivity implements View.OnClickListener {
    TextView start_date, finish_date;
    EditText title;
    ImageView sche_photo;
    DatePicker datePicker;
    Button add_btn, btn_make_sche;
    int dateID;
    int year, month, day;
    User user = User.getInstance();

    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    InputMethodManager imm;


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

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        title.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    imm.hideSoftInputFromWindow(title.getWindowToken(),0);
                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        start_date.setText(y + "/" + (m+1) + "/" + d );
                    }
                };
                DatePickerDialog dialog = new DatePickerDialog(this, callback, year, month, day);
                dialog.show();

                break;
            case R.id.finish:
                DatePickerDialog.OnDateSetListener callback_f = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        finish_date.setText(y + "/" + (m+1) + "/" + d );
                    }
                };
                DatePickerDialog dialog_f = new DatePickerDialog(this, callback_f, year, month, day);
                dialog_f.show();
                break;

            case R.id.add_user:
                break;

            case R.id.btn_make_sche:
                try {
                    Date startDate = new SimpleDateFormat("yyyy/MM/dd").parse(String.valueOf(start_date.getText()));
                    Date endDate  =new SimpleDateFormat("yyyy/MM/dd").parse(String.valueOf(finish_date.getText()));
                    int period = Math.abs((int)(startDate.getTime() - endDate.getTime())/(24*60*60*1000)+1)+2;
                    ScheduleDb tmp = new ScheduleDb(title.getText().toString(),
                                                     String.valueOf(start_date.getText()),
                                                    String.valueOf(finish_date.getText()),
                                                    period,
                                                    user.getU_id());
                    DatabaseReference schedulesRef = myRef.child("schedules");
                    Log.d("new", schedulesRef.toString());
                    DatabaseReference thisScheduleRef = schedulesRef.push();
                    final String key = thisScheduleRef.getKey();
                    thisScheduleRef.setValue(tmp);

                    DatabaseReference scheduleDaysRef = thisScheduleRef.child("days");
                    for(int i=0; i<period; i++){
                        DayDb day = new DayDb(i+1);
                        DatabaseReference dayRef = scheduleDaysRef.push();
                        dayRef.setValue(day);
                    }

                    //유저디비에 스케줄 아이디 넣어줌
                    DatabaseReference userRef = myRef.child("user").child(user.getU_id());
                    userRef.child("schedules").push().setValue(key);

                    // 유저한테 스케줄 새로 넣음
                    user.scheduleDbs.add(new ScheduleDb(title.getText().toString(), start_date.getText().toString(),
                            finish_date.getText().toString(), period, user.getU_id()));
                    user.getSchedules().add(key);


                    NewPlace newPlace = NewPlace.getInstance();
                    newPlace.setSelectedTripName(title.getText().toString());

                    Intent intent = new Intent(AskScheduleDate.this, ScheduleForm.class);
                    finish();
                    startActivity(intent);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "스케줄 추가를 완료해주세요.", Toast.LENGTH_SHORT).show();
    }
}
