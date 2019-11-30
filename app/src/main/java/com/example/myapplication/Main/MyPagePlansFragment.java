package com.example.myapplication.Main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.ScheduleDb;
import com.example.myapplication.Database.Trip;
import com.example.myapplication.Database.User;
import com.example.myapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class MyPagePlansFragment extends Fragment {

    List<Trip> trips = new ArrayList<>();
    User user = User.getInstance();

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
                for(int i=0; i<user.scheduleDbs.size(); i++){
                    ScheduleDb temp = user.scheduleDbs.get(i);
                    Trip trip = new Trip(temp.getTitle(),
                            new SimpleDateFormat("yyyy/MM/dd").parse(temp.start_date),
                            temp.period);
                    trips.add(trip);
                    trip.s_date = temp.start_date; trip.e_date = temp.end_date;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        recyclerView.setAdapter(new MyPagePlansAdapter(trips, getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_my_page_plans, container, false);
    }
}
