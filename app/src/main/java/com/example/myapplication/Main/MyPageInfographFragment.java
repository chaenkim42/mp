package com.example.myapplication.Main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Database.User;
import com.example.myapplication.R;
import com.ultramegasoft.radarchart.RadarHolder;
import com.ultramegasoft.radarchart.RadarView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MyPageInfographFragment extends Fragment {
    public User user = User.getInstance();
    public HashSet<String> culture, tour, nature, religion, park, sport, history;

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

        hashsets();
        //유저 성향 가져오기
        ArrayList<String> u_pref = user.getPreferences();
        List<RadarHolder> mData = new ArrayList<>();
        for(int i=0; i<u_pref.size(); i++){
            String temp = u_pref.get(i).substring(1);
            if(history.contains(temp)){mData.add(new RadarHolder("역사유적지",1));}
            else if(sport.contains(temp)){mData.add(new RadarHolder("레저활동",2));}
            else if(religion.contains(temp)){mData.add(new RadarHolder("성지순례",3));}
            else if(nature.contains(temp)){mData.add(new RadarHolder("자연/휴양",4));}
            else if(park.contains(temp)){mData.add(new RadarHolder("테마파크",5));}
            else if(culture.contains(temp)){mData.add(new RadarHolder("전시/관람",5));}
            else if (tour.contains(temp)){mData.add(new RadarHolder("시티투어",5));}
        }
        mRadarView.setInteractive(true);
//        mRadarView.setLabelColor(R.color.colorAccent);
        mRadarView.setCircleColor(Color.parseColor("#c1c7c3"));
        mRadarView.setPolygonColor(Color.parseColor("#505050"));
        mRadarView.setMaxValue(10);
        mRadarView.setData(mData);
//        mRadarView.setSelectedValue(30);
        Toast.makeText(getActivity(), String.valueOf(mRadarView.getCircleColor()), Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_page_infograph, container, false);
    }

    public void hashsets(){
        history = new HashSet<>();
        history.add("역사탐방");
        history.add("절");
        sport = new HashSet<>();
        sport.add("체험학습");
        sport.add("등산");
        sport.add("계곡");
        religion = new HashSet<>();
        religion.add("성지순례");
        religion.add("절");
        nature = new HashSet<>();
        nature.add("해수욕장");
        nature.add("휴양림");
        nature.add("드라이브");
        nature.add("산책");
        nature.add("식물원");
        park = new HashSet<>();
        park.add("미술관");
        park.add("식물원");
        park.add("동물원");
        park.add("놀이공원");
        park.add("박물관");
        culture = new HashSet<>();
        culture.add("미술관");
        culture.add("박물관");
        HashSet<String> tour = new HashSet<>();
        tour.add("수산물시장");
        tour.add("농산물시장");
        tour.add("목장");

    }

}
