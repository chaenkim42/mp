package com.example.myapplication.Schedule;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyDiaryPage extends AppCompatActivity {
    ImageButton editbtn;
    public static List<String> emptyList = new ArrayList<>();
    public static MyDiary.Diary diaryexample = new MyDiary.Diary("원조 간장 게장",
            "점심식사~\n사람이 너무 많아서\n반찬리필할 때 애먹음ㅠㅠ\n그래도 JMTGR\n이번에는" +
                    " 친구들과 함께였지만 다음에는 부모님과 함께 오고 싶다! 더 많이 시킬 수 있으니깐ㅎㅎ\n굿!" +
                    "\n굿!\n굿!\n굿!\n굿!\n굿!\n굿!\n굿!\n굿!\n굿!", emptyList);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_page);

        TextView title = findViewById(R.id.mydiarypage_title);
        TextView content = findViewById(R.id.mydiarypage_contents_text);

//        if(getIntent().getExtras().getBoolean("isFromEditPage")==true){
//            d.title = getIntent().getExtras().getString("title");
//            d.contents_text = getIntent().getExtras().getString("content");
//        }

        title.setText(diaryexample.title);
        content.setText(diaryexample.contents_text);


        editbtn = findViewById(R.id.mydiarypage_editbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyDiaryPage .this, MyDiaryEditPage.class);
                intent.putExtra("title", diaryexample.title);
                intent.putExtra("content", diaryexample.contents_text);
                startActivity(intent);
                finish();
            }
        });


    }
}
