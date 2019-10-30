package com.example.myapplication.Main;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.Schedule.MyData;
import com.example.myapplication.Schedule.ScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyPage extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public final static int FRAGMENT1 = 1;
    public final static int FRAGMENT2 = 2;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        ToggleButton planTap = (ToggleButton) findViewById(R.id.mypage_userplan_tap);
        ToggleButton prefTap = (ToggleButton) findViewById(R.id.mypage_userpref_tap);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.mypage_radiogroup);
//        ConstraintLayout container = (ConstraintLayout) findViewById(R.id.mypage_container);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int i) {
                for (int j = 0; j < group.getChildCount(); j++) {
                    final ToggleButton view = (ToggleButton) group.getChildAt(j);
                    view.setChecked(view.getId() == i);
                }
            }
        });
        planTap.setOnCheckedChangeListener(this);
        prefTap.setOnCheckedChangeListener(this);

        radioGroup.check(R.id.mypage_userplan_tap);
        planTap.setBackgroundColor(getResources().getColor(R.color.mainBlue));
        planTap.setTextColor(getResources().getColor(R.color.white));
        prefTap.setBackgroundColor(getResources().getColor(R.color.white));

        //초반 화면은 지난 일정 표시.
        callFragment(FRAGMENT1);
//        fragmentManager = getSupportFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        Fragment fragment1 = new MyPagePlansFragment();
//        fragmentTransaction.add(R.id.mypage_container, fragment1);
//        fragmentTransaction.commit();


    }

    private void callFragment(int fragmentId) {

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (fragmentId){
            case FRAGMENT1:
                Fragment fragment1 = new MyPagePlansFragment();
                fragmentTransaction.replace(R.id.mypage_container, fragment1);
                fragmentTransaction.commit();
                break;
            case FRAGMENT2:
                Fragment fragment2 = new MyPageInfographFragment();
                fragmentTransaction.replace(R.id.mypage_container, fragment2);
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            ((RadioGroup)buttonView.getParent()).check(buttonView.getId());
            buttonView.setBackgroundColor(getResources().getColor(R.color.mainBlue));
            buttonView.setTextColor(getResources().getColor(R.color.white));
            switch (buttonView.getId()) {
                case R.id.mypage_userplan_tap:
                    callFragment(FRAGMENT1);
                    break;
                case R.id.mypage_userpref_tap:
                    callFragment(FRAGMENT2);
                    break;
            }
        }else{
            buttonView.setBackgroundColor(getResources().getColor(R.color.white));
            buttonView.setTextColor(getResources().getColor(R.color.black));
        }
    }
}
