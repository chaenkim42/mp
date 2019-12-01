package com.example.myapplication.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Database.Diary;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyDiaryPage extends AppCompatActivity {
    ImageButton editbtn;
    TextView title, content;
    ImageView img_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_page);

         title = findViewById(R.id.mydiarypage_title);
         content = findViewById(R.id.mydiarypage_contents_text);
         img_view = findViewById(R.id.mydiarypage_image);


        switch (getIntent().getExtras().getInt("from")){
            case 0: // edit page 에서 저장해서 넘어옴
                title.setText(getIntent().getExtras().getString("title"));
                content.setText(getIntent().getExtras().getString("content"));
                img_view.setImageBitmap(MyDiaryEditPage.img);
                break;
            case 1: // 다이어리 목록에서 클릭해서 넘어옴
                int position = getIntent().getExtras().getInt("position");

                break;
        }

        editbtn = findViewById(R.id.mydiarypage_editbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDiaryPage .this, MyDiaryEditPage.class);
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("content", content.getText().toString());
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MyDiary.class);
        intent.putExtra("save", "true");
        intent.putExtra("title_d", title.getText().toString());
        intent.putExtra("content_d",  content.getText().toString());
        startActivity(intent);
    }
}
