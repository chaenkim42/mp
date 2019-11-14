package com.example.myapplication.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class SearchPage extends AppCompatActivity implements View.OnClickListener {

    private ImageView tree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        tree = findViewById(R.id.search_page_tree_img);
        tree.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.search_page_tree_img:
                startActivity(new Intent(SearchPage.this, SearchMap.class));

                break;
        }
    }
}
