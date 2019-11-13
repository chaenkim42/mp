package com.example.myapplication.Database;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Diary {
        public String title;
        public String contents_text;
        public List<String> photoIds;
        public int order;

        public Diary(String title, String contents_text, List<String> photoIds, int order){
            this.title = title;
            this.contents_text = contents_text;
            this.photoIds = photoIds;
            this.order = order;
        }

}
