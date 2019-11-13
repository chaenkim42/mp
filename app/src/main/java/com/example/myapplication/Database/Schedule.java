package com.example.myapplication.Database;
import java.sql.Date;

public class Schedule {
    public String title;
    public Date start_date, end_date;
    public String manager;
    public String[] participants;
    public String[] days;
    public String[] diaries;

    public Schedule(String title, Date start_date, Date end_date, String manager, String[] participants,
                    String[] days, String[] diaries){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.manager = manager;
        this.participants = participants;
        this.days = days;
        this.diaries = diaries;
    }
}
