package com.example.myapplication.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.example.myapplication.R;

import java.util.ArrayList;

public class SearchFilter extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    ImageButton nextBtn;
    // 1.관광지 2.전시관람 3.자연휴양 4.캠핑 5.역사유적 6.지역특화거리
    ToggleButton t1, t2, t3, t4, t5, t6;
    static boolean[] checkBoolean ={false, false, false, false, false, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        checkBoolean = new boolean[]{false, false, false, false, false, false};

        nextBtn = findViewById(R.id.searchfilter_next_btn);
        t1 = findViewById(R.id.searchfilter_t1);
        t2 = findViewById(R.id.searchfilter_t2);
        t3 = findViewById(R.id.searchfilter_t3);
        t4 = findViewById(R.id.searchfilter_t4);
        t5 = findViewById(R.id.searchfilter_t5);
        t6 = findViewById(R.id.searchfilter_t6);

        nextBtn.setOnClickListener(this);
        t1.setOnCheckedChangeListener(this);
        t2.setOnCheckedChangeListener(this);
        t3.setOnCheckedChangeListener(this);
        t4.setOnCheckedChangeListener(this);
        t5.setOnCheckedChangeListener(this);
        t6.setOnCheckedChangeListener(this);


    }

    public static boolean[] getCheckBoolean(){
        return checkBoolean;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchfilter_next_btn:
                Intent intent = new Intent(SearchFilter.this, SearchMap.class);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            buttonView.setBackgroundColor(getResources().getColor(R.color.mainBlue));
            buttonView.setTextColor(getResources().getColor(R.color.white));
        }else{
            buttonView.setBackgroundResource(R.drawable.box_map3);
            buttonView.setTextColor(getResources().getColor(R.color.black));
        }
        switch (buttonView.getId()) {
            case R.id.searchfilter_t1:
                checkBoolean[0] = !checkBoolean[0];
                break;
            case R.id.searchfilter_t2:
                checkBoolean[1] = !checkBoolean[1];
                break;
            case R.id.searchfilter_t3:
                checkBoolean[2] = !checkBoolean[2];
                break;
            case R.id.searchfilter_t4:
                checkBoolean[3] = !checkBoolean[3];
                break;
            case R.id.searchfilter_t5:
                checkBoolean[4] = !checkBoolean[4];
                break;
            case R.id.searchfilter_t6:
                checkBoolean[5] = !checkBoolean[5];
                break;
        }
//        Log.d("filter test", String.valueOf(checkBoolean[0])+","+
//                String.valueOf(checkBoolean[1])+ ","+
//                String.valueOf(checkBoolean[2])+ ","+
//                String.valueOf(checkBoolean[3])+ ","+
//                String.valueOf(checkBoolean[4])+ ","+
//                String.valueOf(checkBoolean[5]));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SearchFilter.this, SearchMap.class);
        intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

}
