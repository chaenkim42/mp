package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

import net.daum.mf.map.api.MapLayout;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class MapActivity extends FragmentActivity
    {

    private static final int MENU_MAP_TYPE = Menu.FIRST + 1;
    private static final int MENU_MAP_MOVE = Menu.FIRST + 2;

    private static final String LOG_TAG = "MapViewDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapView mapView = new MapView(this);

        MapLayout mapLayout = new MapLayout(this);
        mapView.setDaumMapApiKey("0b344387456e09c50b0e0d312efe43ae");
//        mapView.setOpenAPIKeyAuthenticationResultListener(this);
//        mapView.setMapViewEventListener(this);
        mapView.setMapType(MapView.MapType.Standard);


        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

//        @Override
////        public boolean onCreateOptionsMenu(Menu menu) {
////            super.onCreateOptionsMenu(menu);
////            menu.add(0, MENU_MAP_TYPE, Menu.NONE, "MapType");
////            menu.add(0, MENU_MAP_MOVE, Menu.NONE, "Move");
////
////            return true;
////        }



    }
}
