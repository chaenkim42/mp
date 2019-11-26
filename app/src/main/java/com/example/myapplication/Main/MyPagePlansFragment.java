package com.example.myapplication.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Trip;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MyPagePlansFragment extends Fragment {

    List<Trip> trips = new ArrayList<>();
    private Trip trip1, trip2, trip3, trip4, trip5;

    public MyPagePlansFragment() {
        // Required empty public constructor
    }

    public static MyPagePlansFragment newInstance(String param1, String param2) {
        MyPagePlansFragment fragment = new MyPagePlansFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.fragmentmypageplans_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity(), RecyclerView.VERTICAL, false));
        {
            try {


                trip1 = new Trip("개강기념 여수 식도락 여행", new SimpleDateFormat("yyyy/MM/dd").parse("2018/09/01"), 4);
                trip2 = new Trip("제주도 with 마미", new SimpleDateFormat("yyyy/MM/dd").parse("2019/05/05"),6);
                trip3= new Trip("부산 당일치기", new SimpleDateFormat("yyyy/MM/dd").parse("2019/08/14"),1);
                trip4 = new Trip("우정여행", new SimpleDateFormat("yyyy/MM/dd").parse("2019/09/22"), 5);
                trip5 = new Trip("가족 장기여행", new SimpleDateFormat("yyyy/MM/dd").parse("2019/11/23"), 16);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        trips.add(trip4);
        trips.add(trip5);
        recyclerView.setAdapter(new MyPagePlansAdapter(trips, getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_page_plans, container, false);
    }
}
