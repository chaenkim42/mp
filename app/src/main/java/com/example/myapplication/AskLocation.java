package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.myapplication.Main.Main;

public class AskLocation extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    public String[] placeName = {
            "seoul","gyungju","tongyoung",
            "busan","daegwannyeong","jejudo",
            "kangneung","incheon"
    };
    public String[] placeFileName = {
            "location0","location1","location2",
            "location3","location4","location5",
            "location6","location7"
    };

    //선택된 카테고리 표시
    int[] selectedBoolean = new int[placeName.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_location);

        int numberOfPlaces = placeName.length;
        Location[] locationList = new Location[numberOfPlaces];

        Button nextBtn = findViewById(R.id.askLocation_nextPageBtn);
        nextBtn.setOnClickListener(this);

        for(int i=0; i<numberOfPlaces; i++){
            String name = placeFileName[i];
            Location l = new Location(placeName[i], i);
            l.setName(name);
            locationList[i] = l;
        }

        locationList[0].setDrawableId(R.drawable.location0);
        locationList[1].setDrawableId(R.drawable.location1);
        locationList[2].setDrawableId(R.drawable.location2);
        locationList[3].setDrawableId(R.drawable.location3);
        locationList[4].setDrawableId(R.drawable.location4);
        locationList[5].setDrawableId(R.drawable.location5);
        locationList[6].setDrawableId(R.drawable.location6);
        locationList[7].setDrawableId(R.drawable.location7);

        //처음엔 모두 0(비선택)
        for(int i=0; i< selectedBoolean.length; i++){
            selectedBoolean[i] = 0;
        }

        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.askLocation_constraintlayout);
        ConstraintSet set = new ConstraintSet();
        ToggleButton[] toggleButtons = new ToggleButton[numberOfPlaces];

        for(int i=0; i<numberOfPlaces; i++){
            if(i==0){
                ToggleButton tb = findViewById(R.id.askLocation_toggleBtn_first);
                tb.setTextOn(placeName[i]);
                tb.setTextOff(placeName[i]);
                tb.setOnCheckedChangeListener(this);
                tb.setId(i+200);
                tb.setChecked(false);
                tb.setTextSize(20);
                tb.setTextColor(getResources().getColor(R.color.colorPrimary));
                tb.setBackgroundResource(locationList[i].getDrawableId());
                toggleButtons[i] = tb;
            }else{
                ToggleButton tb = new ToggleButton(this);
                tb.setTextOn(placeName[i]);
                tb.setTextOff(placeName[i]);
                tb.setOnCheckedChangeListener(this);
                tb.setId(i+200);
                tb.setVisibility(View.VISIBLE);
                tb.setChecked(false);
                tb.setTextSize(20);
                tb.setTextColor(getResources().getColor(R.color.colorPrimary));
                tb.setBackgroundResource(locationList[i].getDrawableId());
                toggleButtons[i] = tb;
                constraintLayout.addView(toggleButtons[i]);
                set.clone(constraintLayout); //clone after addview to the constraint layout

                set.connect(toggleButtons[i].getId(), ConstraintSet.TOP, toggleButtons[i-1].getId(), ConstraintSet.BOTTOM, 50);
                set.connect(toggleButtons[i].getId(), ConstraintSet.LEFT, toggleButtons[i-1].getId(), ConstraintSet.LEFT, 0);
                set.connect(toggleButtons[i].getId(), ConstraintSet.RIGHT, toggleButtons[i-1].getId(), ConstraintSet.RIGHT, 0);

                set.applyTo(constraintLayout); // important
            }
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            buttonView.setTextColor(getResources().getColor(R.color.WHITE));
            selectedBoolean[Integer.valueOf(buttonView.getId())-200] = 1;
        }else{
            buttonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            selectedBoolean[Integer.valueOf(buttonView.getId())-200] = 0;
        }
        //test 용 코드
        String s = "";
        for(int i=0; i< selectedBoolean.length; i++){
            s+= String.valueOf(selectedBoolean[i]);
        }
        Log.e("select",s);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(AskLocation.this, Main.class));
    }
}
