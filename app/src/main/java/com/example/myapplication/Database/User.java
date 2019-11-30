package com.example.myapplication.Database;

import android.graphics.Bitmap;
import android.location.Location;

import com.example.myapplication.Schedule.MyData;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

public class User {
    private static User user = null;

    public String u_id;
    private String email;
    private String password;
    private String name;
    private String sex;
    private int age;
    public HashSet<String> schedules = new HashSet<>();
    public ArrayList<String>  preferences = new ArrayList<>();
    private ArrayList<String>  locations = new ArrayList<>();
    public ArrayList<ScheduleDb> scheduleDbs = new ArrayList<>();
    public ArrayList<Diary> diaries = new ArrayList<>();
    private Bitmap user_image= null;

    private User(){

    }

    private static class LazyHolder {
        public static final User user = new User();
    }
    public static User getInstance() {
        return LazyHolder.user;
    }

    public void setData(String email, String password, String name, int age, String sex){
                this.email = email;
                this.password = password;
                this.name = name;
                this.age = age;
                this.sex = sex;
    }

    public void setUser_image(Bitmap img){
        this.user_image = img;
    }


    public void setSchedule(String schedule){
        this.schedules.add(schedule);
    }

    public void setScheduleDB(ScheduleDb scheduleDB){
        this.scheduleDbs.add(scheduleDB);
    }
    public void setPreferences(String preference){
        this.preferences.add(preference);
    }
    public String getU_id(){ return this.u_id;}

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }

    public String getName(){
        return this.name;
    }

    public int getAge(){
        return this.age;
    }

    public String getSex(){
        return this.sex;
    }

    public HashSet<String> getSchedules(){
        return this.schedules;
    }

    public ArrayList<String> getPreferences(){
        return this.preferences;
    }

    public ArrayList<String> getLocations(){
        return this.locations;
    }

    public Bitmap getUser_image(){return this.user_image;}

    public void setU_id(String key){ this.u_id = key;}

}
