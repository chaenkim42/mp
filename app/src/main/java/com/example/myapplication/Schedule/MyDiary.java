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
import com.example.myapplication.Database.User;
import com.example.myapplication.Main.MyPagePlansFragment;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class MyDiary extends AppCompatActivity implements View.OnClickListener {
    ImageButton add_diary;
    ImageButton edit_diary;
    User user = User.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mydiary);
        add_diary = findViewById(R.id.mydiary_btn_add);
        edit_diary = findViewById(R.id.mydiary_btn_edit);
        add_diary.setOnClickListener(this);
        edit_diary.setOnClickListener(this);

        List<Diary> tmpDiaryList = new ArrayList<>();

        for(int i=0; i<user.diaries.size(); i++){

        }
        Diary d = new Diary("호텔 조식", "생각보다 너무 맛있었음\n다시 올만한 곳\n아싸사사샷");
        Diary dd = new Diary("여수 세계 박람회", "너무 너무 재밌었당\n블라블라\n살라살라 쿵따리");
        Diary ddd = new Diary("한화 아쿠아 플라넷", "");
        tmpDiaryList.add(d);
        tmpDiaryList.add(dd);
        tmpDiaryList.add(ddd);

        RecyclerView recyclerView = findViewById(R.id.mydiary_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager( this, RecyclerView.VERTICAL, false));
        MyDiary_Adapter adapter = new MyDiary_Adapter( tmpDiaryList,this);
        recyclerView.setAdapter(adapter);

        adapter.setItemClick(new MyDiary_Adapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(MyDiary.this, MyDiaryPage.class);
                i.putExtra("from", 1);
                i.putExtra("position", position);
                startActivity(i);
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mydiary_btn_add:
                Intent intent = new Intent(MyDiary.this, MyDiaryEditPage.class);
                if(user.diaries.isEmpty()){
                    intent.putExtra("key", "0");
                }else{
                    // 키값으로 diraies 의 키값을 보내준다
                }
                startActivity(intent);
                break;
            case R.id.mydiary_btn_edit:
                // 삭제할수있는게 뜸
                break;
        }
    }
}
