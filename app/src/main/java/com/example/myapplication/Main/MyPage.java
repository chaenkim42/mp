package com.example.myapplication.Main;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Schedule.MyData;
import com.example.myapplication.Schedule.ScheduleAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyPage extends AppCompatActivity {
    public static RecyclerView recyclerView1, recyclerView2;
    public static RecyclerView.LayoutManager layoutManager;
    public static ScheduleAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        List<MyData> list = new ArrayList<>();
        list.add(new MyData("SEOUL, WITH MY MOM", R.drawable.doggo, 0));
        adapter = new ScheduleAdapter(this, list);

        layoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setOrientation(RecyclerView.HORIZONTAL);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);

        recyclerView1.setLayoutManager(layoutManager);
        recyclerView1.setAdapter(adapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                changeView(pos);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static void changeView(int pos){
        switch (pos){
            case 0:
               recyclerView1.setVisibility(View.VISIBLE);
               recyclerView2.setVisibility(View.INVISIBLE);
               break;
            case 1:
                recyclerView1.setVisibility(View.INVISIBLE);
                recyclerView2.setVisibility(View.VISIBLE);

                break;
        }
    }
}
