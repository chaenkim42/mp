package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inSampleSize = 8;
        //BitmapFactory.decodeFile(R.drawable.seoul, options);
        setContentView(R.layout.activity_splash);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2000);
                    startActivity(new Intent(Splash.this, LogIn.class));
                    finish();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
