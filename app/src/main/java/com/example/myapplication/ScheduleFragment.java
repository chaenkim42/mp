package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ScheduleFragment extends Fragment implements View.OnClickListener{
    private FloatingActionButton button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schdeule, container, false);

        button = view.findViewById(R.id.btn_floating);
        button.setOnClickListener(this);

        //리사이클러뷰에 리니어 레이아웃 메니저 설정
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<String> list = new ArrayList<>();
        list.add("Seoul") ;
        list.add("Suwon") ;

        ScheduleAdapter adapter = new ScheduleAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
