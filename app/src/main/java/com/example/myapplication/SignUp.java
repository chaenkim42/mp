package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Database.User;
import com.example.myapplication.Schedule.ScheduleForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    public static EditText email, password, password_c, name, age, sex;
    public static Button btn_sign_up;
    public static TextView textView;
    ImageView imageView;
    Bitmap img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // setting
        email = findViewById(R.id.signup_email);
        password = findViewById(R.id.signup_password);
        password_c = findViewById(R.id.signup_password_confirm);
        name = findViewById(R.id.signup_name);
        age = findViewById(R.id.signup_age);
        sex = findViewById(R.id.signup_sex);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.sche_photo);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType
                        (android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 1);
            }
        });


        //회원가입
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String p = password.getText().toString();
                String pc = password_c.getText().toString();

                if (p.equals(pc)) {
                    // user initialization
                    User user = User.getInstance();
                    user.setData(email.getText().toString(),  password.getText().toString(), name.getText().toString(),Integer.parseInt(age.getText().toString()),
                            sex.getText().toString());

                    if(img!=null) user.setUser_image(img);

                    startActivity(new Intent(getApplicationContext(), AskPreference.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Check password again!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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
