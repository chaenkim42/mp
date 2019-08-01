package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapView;

//지도로 검색결과 나오는 액티비티

public class SearchMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);

        MapView mapView = new MapView(this);
        ViewGroup container = (ViewGroup)findViewById(R.id.map_view);
        container.addView(mapView);
    }
}
