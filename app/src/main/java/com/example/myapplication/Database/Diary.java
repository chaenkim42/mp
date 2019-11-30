package com.example.myapplication.Database;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Diary {
        public String title;
        public String contents_text;
        public List<String> photoIds;
        public int order;
        public String diary_key;

        public Diary(String title, String contents_text){
            this.title = title;
            this.contents_text = contents_text;
        }
}
