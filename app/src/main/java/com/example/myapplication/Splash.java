package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    startActivity(new Intent(Splash.this,AskAccountActivity.class));
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
