package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;


public class Main extends AppCompatActivity implements View.OnClickListener {

    //뒤로가기
    private BackPressCloseHandler backPress;

    //Fragment Manager, Fragment Transaction to handle Fragment
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button searchBtn;
    private Button mytripBtn;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<String> samples = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뒤로가기
        backPress = new BackPressCloseHandler(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        searchBtn = findViewById(R.id.main_search_btn);
        mytripBtn = findViewById(R.id.main_mytrip_btn);
        recyclerView = findViewById(R.id.recyclerView);
        searchBtn.setOnClickListener(this);
        mytripBtn.setOnClickListener(this);

        //recyclerview
        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        samples.add("문정");
        samples.add("찬주");
        samples.add("채은");

        adapter = new ScheduleAdapter(samples);
        recyclerView.setAdapter(adapter);

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
                startActivity(new Intent(Main.this, SearchPage.class));
                break;
            case R.id.main_mytrip_btn:
                startActivity(new Intent(Main.this, ScheduleForm.class));
                break;
        }
    }
}
