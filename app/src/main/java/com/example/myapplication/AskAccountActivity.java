package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AskAccountActivity extends AppCompatActivity {
    Button btn_login;
    Button btn_signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_askaccount);

        /*   signUp button   */
        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
            }
        });
        /*   login button   */
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AskAccountActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

    }
}
