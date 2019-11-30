package com.example.myapplication.Main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Database.DayDb;
import com.example.myapplication.Database.Diary;
import com.example.myapplication.Database.DiaryDb;
import com.example.myapplication.Database.Place;
import com.example.myapplication.Database.ScheduleDb;
import com.example.myapplication.Database.User;
import com.example.myapplication.LogIn;
import com.example.myapplication.R;
import com.example.myapplication.Schedule.AskScheduleDate;
import com.example.myapplication.Schedule.MyData;
import com.example.myapplication.Schedule.ScheduleAdapter;
import com.example.myapplication.Schedule.ScheduleForm;
import com.example.myapplication.Search.SearchMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Main extends AppCompatActivity implements View.OnClickListener {
    //뒤로가기
    private BackPressCloseHandler backPress;

    //Fragment Manager, Fragment Transaction to handle Fragment
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Button searchBtn;
    private Button mytripBtn;
    private TextView name;
    private ImageButton addScheBtn;
    private ImageView profile;

    User user = User.getInstance();

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<MyData> samples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setScheDB();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        double width = size.x;
        double height = size.y;

        //뒤로가기
        backPress = new BackPressCloseHandler(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        name = findViewById(R.id.main_username_txt);
        searchBtn = findViewById(R.id.main_search_btn);
        mytripBtn = findViewById(R.id.main_mytrip_btn);
        addScheBtn = findViewById(R.id.main_addSche_btn);
        profile = findViewById(R.id.main_profile_img_btn);
        recyclerView = findViewById(R.id.recyclerView);

        name.setText(user.getName());

        searchBtn.setOnClickListener(this);
        mytripBtn.setOnClickListener(this);
        addScheBtn.setOnClickListener(this);
        profile.setOnClickListener(this);

        //recyclerview
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(samples.size()-1);

        adapter = new ScheduleAdapter(this, 0);
        recyclerView.setAdapter(adapter);
//        Log.d("schedules ", user.scheduleDbs.get(0).title);
    }

    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
                System.out.println("MY KEY HASH: "+Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
    //뒤로가기 버튼
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Log.i("pop stack", "ok");
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
        }else {
            backPress.onBackPressed();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_search_btn:
                startActivity(new Intent(Main.this, SearchMap.class));
                break;
            case R.id.main_mytrip_btn:
                startActivity(new Intent(Main.this, AskScheduleDate.class));
                break;
            case R.id.main_profile_img_btn:
                startActivity(new Intent(Main.this, MyPage.class));
                break;
            case R.id.main_addSche_btn:
                startActivity(new Intent(Main.this, AskScheduleDate.class));
                break;
        }
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
