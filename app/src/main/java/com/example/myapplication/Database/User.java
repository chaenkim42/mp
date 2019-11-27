package com.example.myapplication.Database;

import android.location.Location;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private static User user = null;

    public String u_id;
    public String email;
    public String password;
    public String name;
    public String sex;
    public int age;
    public ArrayList<String>  schedules_id = new ArrayList<>();
    public ArrayList<String>  preferences = new ArrayList<>();
    public ArrayList<String>  locations = new ArrayList<>();

    private User(){}
//    private User(int age, String email, String name, String password, String sex, ArrayList<String> schedule_id){
//        schedules = new ArrayList<>();
//        // 스케줄 아이디에 따라서 스케줄을 넣어준다
//
//        this.email = email;
//        this.password = password;
//        this.name = name;
//        this.sex = sex;
//        this.age = age;
//    }

    public static User getInstance(){
        if(user==null){
            user = new User();
        }
        return user;
    }

    public void setData(int age, String email, String name, String password, String sex){
        this.u_id = u_id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;

    }

    public void setLocations(ArrayList<String> locations_id){
        //로케이션 아이디로 받아오는 코드
    }
    public void setPreferences(String preference){
        user.preferences.add(preference);
    }
    public String getU_id(){ return user.u_id;}

    public String getEmail(){
        return user.email;
    }

    public String getPassword(){
        return user.password;
    }

    public String getName(){
        return user.name;
    }

    public int getAge(){
        return user.age;
    }

    public String getSex(){
        return user.sex;
    }

    public ArrayList<String> getSchedules(){
        return user.schedules_id;
    }

    public ArrayList<String> getPreferences(){
        return user.preferences;
    }

    public ArrayList<String> getLocations(){
        return user.locations;
    }

    public void setU_id(String key){ user.u_id = key;}
}
