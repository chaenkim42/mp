package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.User;
import com.example.myapplication.Main.Main;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AskLocation extends AppCompatActivity implements View.OnClickListener {
    Button nextBtn, prevBtn;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference user_ref = myRef.child("user");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_location);

//        int numberOfPlaces = placeName.length;
//        Place[] locationList = new Place[numberOfPlaces];

        nextBtn = findViewById(R.id.askLocation_nextPageBtn);
        nextBtn.setOnClickListener(this);

        prevBtn = findViewById(R.id.askLocation_prevPageBtn);
        prevBtn.setOnClickListener(this);

//        for(int i=0; i<numberOfPlaces; i++){
//            String name = placeFileName[i];
//            Place l = new Place(placeName[i], i);
//            l.setName(name);
//            locationList[i] = l;
//        }

//        locationList[0].setDrawableId(R.drawable.location0);
//        locationList[1].setDrawableId(R.drawable.location1);
//        locationList[2].setDrawableId(R.drawable.location2);
//        locationList[3].setDrawableId(R.drawable.location3);
//        locationList[4].setDrawableId(R.drawable.location4);
//        locationList[5].setDrawableId(R.drawable.location5);
//        locationList[6].setDrawableId(R.drawable.location6);
//        locationList[7].setDrawableId(R.drawable.location7);



//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.askLocation_constraintlayout);
//        ToggleButton[] toggleButtons = new ToggleButton[numberOfPlaces];
//        ConstraintSet set = new ConstraintSet();

        ExpandableHeightGridView gridView = (ExpandableHeightGridView) findViewById(R.id.ask_location_gridView);
        gridView.setExpanded(true);
        GridViewImageAdapter adapter = new GridViewImageAdapter(this); //가로크기의 정보를 같이 넘긴다.
        gridView.setAdapter(adapter);

//        gridView.setItem


//        int tbWidth = constraintLayout.getWidth();
//        for(int i=0; i<numberOfPlaces; i++){
//            if(i==0){
//                ToggleButton tb = findViewById(R.id.askLocation_toggleBtn_first);
//                tb.setTextOn(placeName[i]);
//                tb.setTextOff(placeName[i]);
//                tb.setOnCheckedChangeListener(this);
//                tb.setId(i+200);
//                tb.setChecked(false);
//                tb.setTextSize(20);
//                tb.setTextColor(getResources().getColor(R.color.colorPrimary));
//                tb.setBackgroundResource(locationList[i].getDrawableId());
//                toggleButtons[i] = tb;
//            }else{
//                ToggleButton tb = new ToggleButton(this);
//                tb.setWidth(tbWidth);
//                tb.setTextOn(placeName[i]);
//                tb.setTextOff(placeName[i]);
//                tb.setOnCheckedChangeListener(this);
//                tb.setId(i+200);
//                tb.setVisibility(View.VISIBLE);
//                tb.setChecked(false);
//                tb.setTextSize(20);
//                tb.setTextColor(getResources().getColor(R.color.colorPrimary));
//                tb.setBackgroundResource(locationList[i].getDrawableId());
//                toggleButtons[i] = tb;
//                constraintLayout.addView(toggleButtons[i]);
//                set.clone(constraintLayout); //clone after addview to the constraint layout
//
//                set.connect(toggleButtons[i].getId(), ConstraintSet.TOP, toggleButtons[i-1].getId(), ConstraintSet.BOTTOM, 30);
//                set.connect(toggleButtons[i].getId(), ConstraintSet.LEFT, toggleButtons[i-1].getId(), ConstraintSet.LEFT, 0);
//                set.connect(toggleButtons[i].getId(), ConstraintSet.RIGHT, toggleButtons[i-1].getId(), ConstraintSet.RIGHT, 0);
//
//                set.applyTo(constraintLayout); // important
//            }
//        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.askLocation_nextPageBtn:
                // user의 정보를 디비에 넣는다
                User user = User.getInstance();

                    DatabaseReference temp_ref = user_ref.push();
                    DatabaseReference email_ref = temp_ref.child("email");
                    DatabaseReference password_ref = temp_ref.child("password");
                    DatabaseReference name_ref = temp_ref.child("name");
                    DatabaseReference age_ref = temp_ref.child("age");
                    DatabaseReference sex_ref = temp_ref.child("sex");
                    DatabaseReference sche_ref = temp_ref.child("schedules");
                    DatabaseReference pref_ref = temp_ref.child("preferences");
                    DatabaseReference loca_ref = temp_ref.child("locations");
                    email_ref.setValue(user.getEmail());
                    password_ref.setValue(user.getPassword());
                    name_ref.setValue(user.getName());
                    sex_ref.setValue(user.getSex());
                    age_ref.setValue(user.getAge());

                    for(int i=0; i<user.preferences.size(); i++){
                        pref_ref.push().setValue(user.preferences.get(i));
                    }

                for(int i=0; i<user.locations.size(); i++){
                    loca_ref.push().setValue(user.locations.get(i));
                }

                // 메인 화면으로 넘어간다
                startActivity(new Intent(AskLocation.this, Main.class));
                break;
            case R.id.askLocation_prevPageBtn:
                onBackPressed();
                break;
        }
    }
}
