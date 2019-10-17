package com.example.myapplication.Schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ScheduleForm extends AppCompatActivity {
    ViewGroup mapContainer;
    private RecyclerView recyclerView;

    private String planExample[] = {"여수 식도락 여행", //제목
                                        "2019.09.01", // 시작일
                                        "4" //기간(일)
                                     };
    private class TripDays{
        
        public TripDays(){

        }
    }

    private class AOneDay{

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_form);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.scheduleForm_mapContainer, new MapFragment());
        fragmentTransaction.commit();

        recyclerView = findViewById(R.id.scheduleForm_planRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<ExpandableListAdapter.Item> data = new ArrayList<>();

        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Day "+String.valueOf(1)));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Apple"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Orange"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Banana"));
        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Cars"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Audi"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Aston Martin"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "BMW"));
//        data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Cadillac"));

        ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, "Places");
        places.invisibleChildren = new ArrayList<>();
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Kerala"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Tamil Nadu"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Karnataka"));
        places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, "Maharashtra"));

        data.add(places);

        recyclerView.setAdapter(new ExpandableListAdapter(data));

    }
}
