package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

    //뒤로가기
    private BackPressCloseHandler backPress;

    //내비게이션(하단바)뷰
    private BottomNavigationView bottomBar;
    //각 내비게이션 버튼
    private BottomNavigationItemView homeBtn, scheduleBtn, communityBtn, settingBtn;

    //Fragment Manager, Fragment Tracsaction to handle Fragement
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private RelativeLayout homeLayout;
    private RelativeLayout fragmentMenuLayout, fragmentETCLayout;

    //fragments
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private RecomFragment recomFragment = new RecomFragment();
    private MapFragment mapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뒤로가기
        backPress = new BackPressCloseHandler(this);

        //내비게이션뷰 assign
        bottomBar = (BottomNavigationView) findViewById(R.id.bottom_nav);
        //내비게이션뷰 onclicklistener
        bottomBar.setOnNavigationItemSelectedListener(this);

        //각 내비게이션 아이템 뷰
        homeBtn = findViewById(R.id.bottom_nav_home);
        scheduleBtn = findViewById(R.id.bottom_nav_schedule);
        communityBtn = findViewById(R.id.bottom_nav_community);
        settingBtn = findViewById(R.id.bottom_nav_setting);

        //하단바
        BottomNavigationViewHelper.disableShiftMode(bottomBar); //애니메이션 없애 주는 것
        bottomBar = (BottomNavigationView) findViewById(R.id.bottom_nav);
        bottomBar.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, mapFragment).commitAllowingStateLoss();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (menuItem.getItemId()){
            case R.id.bottom_nav_home:
                fragmentTransaction.replace(R.id.frameLayout, mapFragment).commitAllowingStateLoss();
                //스택 모두 제거하기
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                 break;
            case R.id.bottom_nav_schedule:
                fragmentTransaction.replace(R.id.frameLayout, scheduleFragment).commitAllowingStateLoss();

                //스택 모두 제거하기
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                break;
            case R.id.bottom_nav_community:
                fragmentTransaction.replace(R.id.frameLayout, recomFragment).commitAllowingStateLoss();
                break;
            case R.id.bottom_nav_setting:
                break;
        }
        return true;
    }


    //뒤로가기 버튼
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            Log.i("pop stack", "ok");
        }
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            homeLayout.setVisibility(View.VISIBLE);
        }else {
            backPress.onBackPressed();
        }

    }


}
