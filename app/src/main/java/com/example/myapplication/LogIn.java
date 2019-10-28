package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LogIn extends AppCompatActivity {
     SQLiteDatabase db;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        Button login = findViewById(R.id.btn_login);
        Button checking = findViewById(R.id.btn_signup);
        final EditText email_field = findViewById(R.id.field_email);
        final EditText password_field = findViewById(R.id.field_password);

        String databaseName = "logIn";//데이터베이스 이름 설정
        final SQLiteHelper helper = new SQLiteHelper(this , databaseName, null, 3); //헬퍼를 생성함

        try{
            db = helper.getReadableDatabase();
        }catch(SQLiteException e){
            db = helper.getWritableDatabase();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = email_field.getText().toString();
                password = password_field.getText().toString();
                db.execSQL("insert into logIn values(null, '"+email+"','"+password+"');");
            }
        });

        checking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 디비 프린트
                System.out.println(helper.showAll());

            }
        });
    }
}
