package com.example.myapplication.Schedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.Main.MyPagePlansFragment;
import com.example.myapplication.R;

public class MyDiary extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiary);

//        RelativeLayout button = (RelativeLayout) findViewById(R.id.button);

//        button.setBackgroundColor(getResources().getColor(R.color.mainBlue));
//
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction transaction = fm.beginTransaction();
//        transaction.add(R.id.button, new MyPagePlansFragment());
//        transaction.commit();
//        Fragment fragment1 = new MyPagePlansFragment();
//        fragmentTransaction.add(R.id.mypage_container, fragment1);
//        fragmentTransaction.commit();

    }
}
