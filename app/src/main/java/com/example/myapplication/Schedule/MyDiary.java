package com.example.myapplication.Schedule;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Main.MyPagePlansFragment;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MyDiary extends AppCompatActivity {

    public static class Diary{
        public String title;
        public String contents_text;
        public List<String> photoIds = new ArrayList<>();
        public Diary(String title, String contents_text, List<String> photoIds){
            this.title = title;
            this.contents_text = contents_text;
            this.photoIds = photoIds;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiary);

        List<Diary> tmpDiaryList = new ArrayList<>();
        List<String> emptyString = new ArrayList<>();
        Diary d = new Diary("호텔 조식", "생각보다 너무 맛있었음\n다시 올만한 곳\n아싸사사샷", emptyString);
        Diary dd = new Diary("여수 세계 박람회", "너무 너무 재밌었당\n블라블라\n살라살라 쿵따리", emptyString);
        Diary ddd = new Diary("한화 아쿠아 플라넷", "", emptyString);
        Diary dddd = new Diary("공원", "", emptyString);

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
        recyclerView.setAdapter(new MyDiary_Adapter( tmpDiaryList,this));


    }
}
