package com.example.myapplication.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Database.Diary;
import com.example.myapplication.Main.MyPagePlansFragment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyDiary extends AppCompatActivity implements View.OnClickListener {
    ImageButton add_diary;
    ImageButton edit_diary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiary);
        add_diary = findViewById(R.id.mydiary_btn_add);
        edit_diary = findViewById(R.id.mydiary_btn_edit);
        add_diary.setOnClickListener(this);
        edit_diary.setOnClickListener(this);

        List<Diary> tmpDiaryList = new ArrayList<>();
        List<String> emptyString = new ArrayList<>();
        Diary d = new Diary("호텔 조식", "생각보다 너무 맛있었음\n다시 올만한 곳\n아싸사사샷", emptyString,0);
        Diary dd = new Diary("여수 세계 박람회", "너무 너무 재밌었당\n블라블라\n살라살라 쿵따리", emptyString, 1);
        Diary ddd = new Diary("한화 아쿠아 플라넷", "", emptyString, 2);
        Diary dddd = new Diary("공원", "", emptyString, 3);

        tmpDiaryList.add(d);
        tmpDiaryList.add(dd);
        tmpDiaryList.add(ddd);
        tmpDiaryList.add(dddd);
        tmpDiaryList.add(d);
        tmpDiaryList.add(dd);
        tmpDiaryList.add(ddd);
        tmpDiaryList.add(dddd);

        RecyclerView recyclerView = findViewById(R.id.mydiary_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager( this, RecyclerView.VERTICAL, false));
        MyDiary_Adapter adapter = new MyDiary_Adapter( tmpDiaryList,this);
        recyclerView.setAdapter(adapter);

        adapter.setItemClick(new MyDiary_Adapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                startActivity(new Intent(MyDiary.this, MyDiaryPage.class));
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mydiary_btn_add:
                startActivity(new Intent(MyDiary.this, MyDiaryEditPage.class));
                break;
            case R.id.mydiary_btn_edit:
                // 삭제할수있는게 뜸
                break;
        }
    }
}
