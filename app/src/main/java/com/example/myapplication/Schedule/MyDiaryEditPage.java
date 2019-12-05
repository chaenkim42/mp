package com.example.myapplication.Schedule;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.Diary;
import com.example.myapplication.Database.DiaryDb;
import com.example.myapplication.Database.NewPlace;
import com.example.myapplication.Database.User;
import com.example.myapplication.R;
import com.firebase.client.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;


public class MyDiaryEditPage extends AppCompatActivity implements View.OnClickListener {

    String data_title, data_content;
    EditText starttime, title, content;
    Button savebtn;
    ImageView imageView;
    String key_diaries;
    User user = User.getInstance();
    NewPlace newPlace = NewPlace.getInstance();
    public static Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diary_edit_page);
        key_diaries = getIntent().getStringExtra("key");

        title = findViewById(R.id.editpage_title);
        content = findViewById(R.id.editpage_content);
        savebtn = findViewById(R.id.editpage_save_btn);
        imageView = findViewById(R.id.editpage_image);

        title.setText(data_title);
        content.setText(data_content);

        savebtn.setOnClickListener(this);
        imageView.setOnClickListener(this);

        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title.getWindowToken(), 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editpage_save_btn:
                // 디비에 저장
                DatabaseReference diary_ref = FirebaseDatabase.getInstance().getReference().child("diaries");
                if(key_diaries.equals("0")){
//                     기존 diaries 없는 상황
                    final Diary diary = new Diary(title.getText().toString(), content.getText().toString());
                    DatabaseReference temp = diary_ref.push();
                    temp.setValue(new DiaryDb(user.getU_id(),newPlace.sche_id));
                    temp.child("diary").push().setValue(diary);
                    temp.child("user").setValue(user.getU_id());

                    Diary diary1 = new Diary(title.getText().toString(),
                            content.getText().toString());
                    if (newPlace.sche_pos==0){
                        user.diaries.add(new DiaryDb(user.getU_id(), newPlace.sche_id));
                    }
                    user.diaries.get(newPlace.sche_pos).diaries.add(diary1);



                }else{
                    // 기존 diaries 있음
                }

                    Intent i = new Intent(MyDiaryEditPage.this, MyDiaryPage.class);
                    i.putExtra("title", title.getText().toString());
                    i.putExtra("content", content.getText().toString());
                    i.putExtra("from", 0);
                    startActivity(i);
                    finish();

                finish();
                break;

            case R.id.editpage_image:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType
                        (android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 1);
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지뷰에 세팅
                    imageView.setImageBitmap(img);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
