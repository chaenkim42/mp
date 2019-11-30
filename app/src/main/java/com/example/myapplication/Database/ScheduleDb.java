package com.example.myapplication.Database;

import java.sql.Date;
import java.util.ArrayList;

public class ScheduleDb {
    public String title;
    public String start_date, end_date; //format yyyy/MM/dd
    public String manager;
    public int period;
    public ArrayList<DayDb> days;
    public ArrayList<DiaryDb> diaries;
    public String sche_id;


    public ScheduleDb(String title,
                      String start_date,
                      String end_date,
                      int period,
                      String u_id){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.period = period;
        this.manager = u_id;
    }

    public String getTitle(){
        return this.title;
    }

    public void setDiaries(DiaryDb diaryDb){
        this.diaries.add(diaryDb);
    }
}
