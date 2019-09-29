package com.example.myapplication.Schedule;

import android.graphics.drawable.Drawable;

public class MyData {
    String text;
    int imgNum;
    int viewType;

    public MyData(String text, int imgNum, int viewType){
        this.text = text;
        this.imgNum = imgNum;
        this.viewType = viewType;
    }

    public MyData(String text, int viewType){
        this.text = text;
        this.viewType = viewType;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setImgNum(int imgNum) {
        this.imgNum = imgNum;
    }

    public int getImgNum() {
        return imgNum;
    }

    public int getViewType(){
        return this.viewType;
    }

    public void setViewType(int viewType){
        this.viewType = viewType;
    }
}
