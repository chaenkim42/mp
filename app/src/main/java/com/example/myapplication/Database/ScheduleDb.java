package com.example.myapplication.Database;

import java.sql.Date;
import java.util.ArrayList;

public class ScheduleDb {
    public String title;
    public String start_date, end_date; //format yyyy/MM/dd
    public UserDb manager;
    public int period;
    public ArrayList<User> participants;
    public ArrayList<DayDb> days;
    public String[] diaries;

    public ScheduleDb(String title,
                      String start_date,
                      String end_date,
                      int period,
                      UserDb manager){
        this.title = title;
        this.start_date = start_date;
        this.end_date = end_date;
        this.period = period;
        this.manager = manager;
    }
}
