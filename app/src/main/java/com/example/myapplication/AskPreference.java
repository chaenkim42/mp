package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.myapplication.GridViewTextAdapter.selectedHash;

public class AskPreference extends AppCompatActivity implements View.OnClickListener {
//    int selected = 0;
    public static Button askPref_nextBtn;

    public String[] categories = {
            "#드라이브","#해수욕장","#미술관","#수산물시장",
            "#역사탐방","#성지순례","#등산","#목장","#계곡",
            "#박물관","#동물원","#놀이공원","#체험학습",
            "#식물원","#농산물시장","#절","#산책","#휴양림",
            "#액티비티"
    };

    //선택된 카테고리 표시
    int[] selectedBoolean = new int[categories.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_preference);

        askPref_nextBtn = findViewById(R.id.askPreference_nextPageBtn);
//        askPref_nextBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        askPref_nextBtn.setOnClickListener(this);

        ExpandableHeightGridView gridView = (ExpandableHeightGridView) findViewById(R.id.askPreference_gridView);
        gridView.setExpanded(true);
        GridViewTextAdapter adapter = new GridViewTextAdapter(this); //가로크기의 정보를 같이 넘긴다.
        gridView.setAdapter(adapter);

//        //처음엔 모두 0(비선택)
//        for(int i=0; i< selectedBoolean.length; i++){
//            selectedBoolean[i] = 0;
//        }
//
//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.askPreference_constraintlayout);
//        ConstraintSet set = new ConstraintSet();
//        int categoryLength = categories.length;
//        ToggleButton[] toggleButtons = new ToggleButton[categoryLength];
//
//        //categories 배열에 있는 element들  togglebutton으로 constraintLayout에 추가
//        for(int i=0; i<categories.length; i++){
//            if(i==0){
//                ToggleButton tb = findViewById(R.id.askPreference_toggleBtn_first);
//                tb.setTextOff(categories[i]);
//                tb.setTextOn(categories[i]);
//                tb.setId(i+100);
//                tb.setChecked(false);
//                tb.setTextSize(18);
//                tb.setBackgroundResource(R.drawable.hash_unselected2);
//                toggleButtons[i] = tb;
//            }else {
//                ToggleButton tb = new ToggleButton(this);
//                tb.setTextOn(categories[i]);
//                tb.setTextOff(categories[i]);
////                tb.setTextOff("해제");
////                Log.e("tb",categories[i]);
//                tb.setId(i+100);
//                tb.setVisibility(View.VISIBLE);
//                tb.setChecked(false);
//                tb.setTextSize(18);
//                tb.setBackgroundResource(R.drawable.hash_unselected2);
//                toggleButtons[i] = tb;
//                constraintLayout.addView(toggleButtons[i]);
//                set.clone(constraintLayout); //clone after addview to the constraint layout
//                set.connect(toggleButtons[i].getId(), ConstraintSet.LEFT, toggleButtons[i-1].getId(), ConstraintSet.RIGHT, 10);
//                set.connect(toggleButtons[i].getId(), ConstraintSet.TOP, toggleButtons[i-1].getId(),ConstraintSet.TOP);
//                set.connect(toggleButtons[i].getId(), ConstraintSet.BOTTOM,toggleButtons[i-1].getId(),ConstraintSet.BOTTOM);
//               //줄바꿈
//                if(i%3 == 2){
////                   set.connect(toggleButtons[i].getId(),ConstraintSet.RIGHT, R.id.askPreference_talkbox, ConstraintSet.RIGHT,24);
//                   set.connect(toggleButtons[i-1].getId(),ConstraintSet.RIGHT, toggleButtons[i].getId(), ConstraintSet.LEFT);
//               }
//                //Log.e("tb getRight", String.valueOf(toggleButtons[i].getRight()));
//                if(i%3 == 0){
//                    set.connect(toggleButtons[i].getId(), ConstraintSet.LEFT, toggleButtons[i-3].getId(), ConstraintSet.LEFT,0);
//                    set.connect(toggleButtons[i].getId(), ConstraintSet.TOP, toggleButtons[i-3].getId(), ConstraintSet.BOTTOM, 170);
//                }
//                set.applyTo(constraintLayout);
//            }
//            //모든 togglebutton에 onCheckedChangeListenr 달기
//            toggleButtons[i].setOnCheckedChangeListener(this);
//        }
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        //클릭될 때마다 background resource(버튼 배경) 바꾸고 selectedBoolean에 반영
//        if(isChecked){
//            selected ++;
//            buttonView.setBackgroundResource(R.drawable.hash_selected2);
//            selectedBoolean[Integer.valueOf(buttonView.getId())-100] = 1;
//        }else{
//            selected --;
//            buttonView.setBackgroundResource(R.drawable.hash_unselected2);
//            selectedBoolean[Integer.valueOf(buttonView.getId())-100] = 0;
//        }
//        if(selected >=5){
//            nextBtn.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary));
//        }else{
//            nextBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//        }
//        //test 용 코드
//        String s = "";
//        for(int i=0; i< selectedBoolean.length; i++){
//            s+= String.valueOf(selectedBoolean[i]);
//        }
//        Log.e("select",s);
//    }

    @Override
    public void onClick(View v) {
        if(selectedHash >= 5){
            startActivity(new Intent(AskPreference.this, AskLocation.class));
        }else{
            Toast.makeText(this, "5개 이상 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
