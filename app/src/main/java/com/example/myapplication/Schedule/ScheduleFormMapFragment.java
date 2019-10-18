package com.example.myapplication.Schedule;

import android.os.Bundle;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import net.daum.mf.map.api.MapView;


public class ScheduleFormMapFragment extends Fragment {
    static View v;
    public ScheduleFormMapFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            v = inflater.inflate(R.layout.fragment_schedule_form_map, container, false);
            ViewGroup mapContainer = v.findViewById(R.id.fragment_scheduleFormMap_constraintLayout);
            // 카카오맵은 두 개 이상의 맵뷰를 한 개의 프로세스 내에서 띄우는 것을 지원하지 않기 때문에
            // ScheduleForm.java 의 mapView 변수를 static 으로 하여 가져왔다.
            MapView mapView = ScheduleForm.mapView;
            // mapView가 원래 포함되어 있던 parent view 에서 removeView() 호출
            ScheduleForm.mapViewContainer.removeView(mapView);
            mapView = new MapView(getActivity());
            mapContainer.addView(mapView);
        }catch (InflateException e){
            // 구글맵 View가 이미 inflate되어 있는 상태이므로, 에러를 무시합니다.
        }
        return v;
    }


}
