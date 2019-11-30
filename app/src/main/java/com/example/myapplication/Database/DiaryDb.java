package com.example.myapplication.Database;

import java.util.ArrayList;

public class DiaryDb {
    public String sche_id;
    public String u_id;
    public ArrayList<Diary> diaries = new ArrayList<>();

    public DiaryDb(String u_id, String sche_id){
        this.u_id = u_id;
        this.sche_id = sche_id;
    }

    public void setDiary(Diary diary){
        this.diaries.add(diary);
    }
}
