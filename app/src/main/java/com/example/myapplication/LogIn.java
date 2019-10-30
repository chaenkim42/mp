package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class LogIn extends AppCompatActivity {
    SQLiteDatabase db;
    String email, password;
    EditText email_field, password_field;
    SQLiteHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        Button login = findViewById(R.id.btn_login);
        Button checking = findViewById(R.id.btn_print);
        EditText email_field = findViewById(R.id.field_email);
        EditText password_field = findViewById(R.id.field_password);

        String databaseName = "logIn";//데이터베이스 이름 설정
        helper = new SQLiteHelper(this , databaseName, null, 3); //헬퍼를 생성함

        try{
            db = helper.getReadableDatabase();
        }catch(SQLiteException e){
            db = helper.getWritableDatabase();
        }


    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:
                email = email_field.getText().toString();
                password = password_field.getText().toString();
                if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password)){
                    Toast.makeText(this, "fill the contents!", Toast.LENGTH_SHORT).show();
                }else{
                    db.execSQL("insert into logIn values(null, '"+email+"','"+password+"');");
                }
                break;
            case R.id.btn_print:
                // 디비 프린트
                System.out.println(helper.showAll());
                break;
            case R.id.ask_sign_up:
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
        }
    }
}
