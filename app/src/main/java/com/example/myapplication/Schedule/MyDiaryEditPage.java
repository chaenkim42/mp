package com.example.myapplication.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import static com.example.myapplication.Schedule.MyDiaryPage.diaryexample;

public class MyDiaryEditPage extends AppCompatActivity implements View.OnClickListener {

    String data_title, data_content;
    EditText starttime, title, content;
    Button savebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_edit_page);

        data_title = getIntent().getStringExtra("title");
        data_content = getIntent().getStringExtra("content");

        starttime = findViewById(R.id.editpage_timestart);
        title = findViewById(R.id.editpage_title);
        content = findViewById(R.id.editpage_content);
        savebtn = findViewById(R.id.editpage_save_btn);

        title.setText(data_title);
        content.setText(data_content);

        savebtn.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(starttime.getWindowToken(), 0);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyDiaryEditPage.this, MyDiaryPage.class);
        diaryexample.title = String.valueOf(title.getText());
        diaryexample.contents_text = String.valueOf(content.getText());
//        intent.putExtra("title", title.getText());
//        intent.putExtra("content", content.getText());
//        intent.putExtra("isFromEditPage", true);
        startActivity(intent);
        finish();
    }
}
