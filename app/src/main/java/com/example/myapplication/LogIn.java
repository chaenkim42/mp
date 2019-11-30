package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.DayDb;
import com.example.myapplication.Database.Diary;
import com.example.myapplication.Database.DiaryDb;
import com.example.myapplication.Database.Place;
import com.example.myapplication.Database.ScheduleDb;
import com.example.myapplication.Database.User;
import com.example.myapplication.Main.Main;
import com.example.myapplication.Main.MyPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.StringTokenizer;


public class LogIn extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

    Button btn_signup;
    EditText email_field;
    EditText password_field;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    User user = User.getInstance();
    String current;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        Button login = findViewById(R.id.btn_login);
        btn_signup = findViewById(R.id.btn_ask_sign_up);
        email_field = findViewById(R.id.field_email);
        password_field = findViewById(R.id.field_password);
        email_field.setOnClickListener(this);
        password_field.setOnClickListener(this);
        login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                final String given_email = email_field.getText().toString();
                final String given_password = password_field.getText().toString();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    Iterator<DataSnapshot> child;
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                child = dataSnapshot.getChildren().iterator();
                                while(child.hasNext()){
                                    DataSnapshot temp = child.next(); //각각의 객체
                                    current = temp.getKey();
                                    if(temp.getKey().equals("user")){
                                        Iterator<DataSnapshot> user_iterator = temp.getChildren().iterator();

                                        while(user_iterator.hasNext()){
                                            DataSnapshot user_temp = user_iterator.next(); //각각의 유저 객체

                                            if(user_temp.child("email").getValue().toString().equals(given_email)){
                                                if(user_temp.child("password").getValue().toString().equals(given_password)){
                                                    user.setU_id(user_temp.getKey());
                                                    user.setData(given_email, given_password, user_temp.child("name").getValue().toString(),
                                                            Integer.parseInt(user_temp.child("age").getValue().toString()), user_temp.child("sex").getValue().toString());

                                                    Iterator<DataSnapshot> pref_iterator = user_temp.child("preferences").getChildren().iterator();
                                                    while(pref_iterator.hasNext()){
                                                        user.setPreferences(pref_iterator.next().getValue().toString()); // 아이터레이터로 받기!!
                                                    }

                                                    Iterator<DataSnapshot> sche_iterator = user_temp.child("schedules").getChildren().iterator();
                                                    while(sche_iterator.hasNext()){
                                                        user.getSchedules().add(sche_iterator.next().getValue().toString());// 아이터레이터로 받기!!
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }

                                Iterator<DataSnapshot> child2 = dataSnapshot.getChildren().iterator();
                                while(child2.hasNext()){
                                    DataSnapshot temp = child2.next(); //각각의 객체
                                    current = temp.getKey();
                                    if(temp.getKey().equals("schedules")){
                                        Iterator<DataSnapshot> sche_iterator = temp.getChildren().iterator();

                                        while(sche_iterator.hasNext()){
                                            DataSnapshot tmp = sche_iterator.next();
                                            //유저 스케줄 아이디랑 일치하면 넣는다
                                            if(user.getSchedules().contains(tmp.getKey())){
                                                ScheduleDb scheduleDb = new ScheduleDb(tmp.child("title").getValue().toString(),
                                                        tmp.child("start_date").getValue().toString(),
                                                        tmp.child("end_date").getValue().toString(),
                                                        Integer.parseInt(tmp.child("period").getValue().toString()),
                                                        user.getU_id());
                                                scheduleDb.sche_id = tmp.getKey();
                                                user.setScheduleDB(scheduleDb);
                                                user.getSchedules().add(tmp.getKey());

                                                Iterator<DataSnapshot> c = tmp.child("days").getChildren().iterator(); // 데이
                                                ArrayList<Place> places;
                                                while(c.hasNext()){
                                                    DataSnapshot tmp2 = c.next(); //데이 하나

                                                    Iterator<DataSnapshot> tmp_spot = tmp2.child("spots").getChildren().iterator(); // 스팟
                                                    places = new ArrayList<>();
                                                    while(tmp_spot.hasNext()){
                                                        DataSnapshot tmp3 = tmp_spot.next(); //스팟 객체 하나
                                                        places.add(new Place(tmp3.child("name").getValue().toString(),
                                                                Double.valueOf(tmp3.child("latitude").getValue().toString()),
                                                                Double.valueOf(tmp3.child("longitude").getValue().toString())));
                                                        Log.d("spot name: ", tmp3.child("name").getValue().toString());
                                                    }

                                                    try{
                                                        scheduleDb.days.add(new DayDb(Integer.parseInt(tmp2.child("order").getValue().toString()), places));
                                                    }catch(Exception e) {
                                                        Log.d("error: ", e.getMessage());
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }

                                Iterator<DataSnapshot> child3 = dataSnapshot.getChildren().iterator();
                                while(child3.hasNext()){
                                    DataSnapshot temp = child3.next(); //각각의 객체
                                    current = temp.getKey();
                                    if(temp.getKey().equals("diaries")){
                                        Iterator<DataSnapshot> diary_iterator = temp.getChildren().iterator();

                                        while(diary_iterator.hasNext()){
                                            DataSnapshot tmp = diary_iterator.next(); //스케줄이랑 같음
                                            //sche 아이디끼리 같으면
                                            String sche_id = tmp.child("user").getValue().toString();
                                            if(user.getSchedules().contains(sche_id)){
                                                Iterator<DataSnapshot> t = tmp.child("diary").getChildren().iterator();
                                                while(t.hasNext()){
                                                    DataSnapshot d = t.next();
                                                    Diary diary = new Diary(d.child("title").getValue().toString(),
                                                            d.child("contents_text").getValue().toString());
                                                    DiaryDb diaryDb = new DiaryDb(user.getU_id(), sche_id);
                                                    diaryDb.diaries.add(diary);
                                                    user.diaries.add(diaryDb);
                                                }

                                            }

                                        }
                                    }
                                }
                                Intent i = new Intent(LogIn.this, Main.class);
                                startActivity(i);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                break;

            case R.id.btn_ask_sign_up:
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
        } else {
            signInAnonymously();
        }
    }

    private void signInAnonymously() {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("error: ", "exception");
                    }
                });
    }

    public void setScheDB(){
        DatabaseReference schedules = FirebaseDatabase.getInstance().getReference().child("schedules");
        schedules.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator(); // 스케줄
                while(child.hasNext()){
                    DataSnapshot tmp = child.next();
                    //유저 스케줄 아이디랑 일치하면 넣는다
                    if(user.getSchedules().contains(tmp.getKey())){

                        ScheduleDb scheduleDb = new ScheduleDb(tmp.child("title").getValue().toString(),
                                tmp.child("start_date").getValue().toString(),
                                tmp.child("end_date").getValue().toString(),
                                Integer.parseInt(tmp.child("period").getValue().toString()),
                                user.getU_id());
                        scheduleDb.sche_id = tmp.getKey();
                        user.setScheduleDB(scheduleDb);

                        Iterator<DataSnapshot> c = tmp.child("days").getChildren().iterator(); // 데이
                        ArrayList<Place> places;
                        while(c.hasNext()){
                            DataSnapshot tmp2 = c.next(); //데이 하나

                            Iterator<DataSnapshot> tmp_spot = tmp2.child("spots").getChildren().iterator(); // 스팟
                            places = new ArrayList<>();
                            while(tmp_spot.hasNext()){
                                DataSnapshot tmp3 = tmp_spot.next(); //스팟 객체 하나
                                places.add(new Place(tmp3.child("name").getValue().toString(),
                                        Double.valueOf(tmp3.child("latitude").getValue().toString()),
                                        Double.valueOf(tmp3.child("longitude").getValue().toString())));
                                Log.d("spot name: ", tmp3.child("name").getValue().toString());
                            }

                            try{
                                scheduleDb.days.add(new DayDb(Integer.parseInt(tmp2.child("order").getValue().toString()), places));
                            }catch(Exception e) {
                                Log.d("error: ", e.getMessage());
                            }

                            Intent i = new Intent(getApplicationContext(), Main.class);
                            startActivity(i);
                            break;
                        }
//                        setDiaryDB();

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setDiaryDB(){

        DatabaseReference diaries_ref = FirebaseDatabase.getInstance().getReference().child("diaries");
        diaries_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator(); // 스케줄 당 다이어리들
                while(child.hasNext()){
                    DataSnapshot tmp = child.next(); //스케줄이랑 같음
                    //유저 아이디끼리 같으면
                    String sche_id = tmp.child("user").getValue().toString();
                    if(user.getSchedules().contains(sche_id)){
                        Iterator<DataSnapshot> temp = tmp.child("diary").getChildren().iterator();
                        while(temp.hasNext()){
                            DataSnapshot d = temp.next();
                            Diary diary = new Diary(d.child("title").getValue().toString(),
                                    d.child("contents_text").getValue().toString());
                            DiaryDb diaryDb = new DiaryDb(user.getU_id(), sche_id);
                            diaryDb.diaries.add(diary);
                            user.diaries.add(diaryDb);
                        }

//                        Intent i = new Intent(getApplicationContext(), Main.class);
//                        startActivity(i);
                        break;

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
