package com.example.myapplication.Database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {
    private String title;
    private int period;
    private Date startDate;
    public List<Day> days = new ArrayList<>();
    public String s_date, e_date;

    public Trip(String title, Date startDate, int period){
        this.title = title;
        this.period = period;
        this.startDate = startDate;
        for(int i=0; i<period; i++){
            days.add(new Day(i));
        }
    }
    public void addDay(int onWhichIndex, Day day){
        this.days.add(onWhichIndex, day);
    }

    public void editDay(int onWhichIndex, Day day){
        this.days.remove(onWhichIndex);
        this.days.add(onWhichIndex, day);
    }

    public String getTitle() {return title;}
    public Date getStartDate() {return startDate;}
    public int getPeriod() {return period;}
    public List<Day> getDays() {return days;}
    public Day getDay(int i){return days.get(i);}
}
