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

        //메인 홈 레이아웃 (프래그먼트)
        homeLayout = (RelativeLayout)findViewById(R.id.home);
        fragmentMenuLayout = (RelativeLayout)findViewById(R.id.fragment_menu);
        fragmentETCLayout = (RelativeLayout)findViewById(R.id.fragment_etc);
        fragmentManager = getSupportFragmentManager();



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.bottom_nav_home:
                homeLayout.setVisibility(View.VISIBLE);
                fragmentETCLayout.setVisibility(View.GONE);
                fragmentMenuLayout.setVisibility(View.GONE);
                //스택 모두 제거하기
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                return true;
            case R.id.bottom_nav_schedule:
//                fragmentManager.beginTransaction()
//                        .add(R.id.fragment_menu, new AskAccountActivity()).commit();
                homeLayout.setVisibility(View.GONE);
                fragmentETCLayout.setVisibility(View.GONE);
                fragmentMenuLayout.setVisibility(View.VISIBLE);
                //스택 모두 제거하기
                for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                    fragmentManager.popBackStack();
                }
                return true;
            case R.id.bottom_nav_community:
                return true;
            case R.id.bottom_nav_setting:
                return true;
        }
        return false;
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
