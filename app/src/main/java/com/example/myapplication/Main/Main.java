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
}
