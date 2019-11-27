package com.example.myapplication.Search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class AddToList extends AppCompatActivity implements View.OnClickListener {

    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_list);

        backbtn = findViewById(R.id.addtolist_backbtn);
        backbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onBackPressed();
    }
}
