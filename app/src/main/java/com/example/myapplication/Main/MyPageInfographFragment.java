package com.example.myapplication.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.util.ArrayList;
import java.util.List;

public class MyPageInfographFragment extends Fragment {

    public MyPageInfographFragment() {
        // Required empty public constructor
    }

    public static MyPageInfographFragment newInstance(String param1, String param2) {
        MyPageInfographFragment fragment = new MyPageInfographFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RadarView mRadarView = (RadarView)getActivity().findViewById(R.id.fragmentmypage_radar);
        List<RadarHolder> mData = new ArrayList<>();
        mData.add(new RadarHolder("역사유적지",1));
        mData.add(new RadarHolder("레저활동",2));
        mData.add(new RadarHolder("성지순례",3));
        mData.add(new RadarHolder("자연/휴양",4));
        mData.add(new RadarHolder("테마파크",5));
        mData.add(new RadarHolder("전시/관람",5));
        mData.add(new RadarHolder("시티투어",5));
        mRadarView.setInteractive(true);
//        mRadarView.setLabelColor(R.color.colorAccent);
        mRadarView.setCircleColor(Color.parseColor("#c1c7c3"));
        mRadarView.setPolygonColor(Color.parseColor("#505050"));
        mRadarView.setMaxValue(10);
        mRadarView.setData(mData);
//        mRadarView.setSelectedValue(30);
        Toast.makeText(getActivity(), String.valueOf(mRadarView.getCircleColor()), Toast.LENGTH_SHORT).show();
//        RadarEditWidget mEditWidget = (RadarEditWidget) getActivity().findViewById(R.id.fragmentmypage_radarwidget);
//        mEditWidget.setTarget(mRadarView);
//        mEditWidget.setOnButtonClickListener(new RadarEditWidget.OnButtonClickListener() {
//            @Override
//            public void onSave() {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page_infograph, container, false);
    }


}
