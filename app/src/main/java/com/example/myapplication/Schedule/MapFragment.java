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


public class MapFragment extends Fragment {
    static View v;
    public MapFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try{
            v = inflater.inflate(R.layout.fragment_map, container, false);
            MapView mapView = new MapView(getActivity());
            ViewGroup mapContainer = v.findViewById(R.id.container);
            mapContainer.addView(mapView);
        }catch (InflateException e){
            // 구글맵 View가 이미 inflate되어 있는 상태이므로, 에러를 무시합니다.
        }
        return v;
    }


}
