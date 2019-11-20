package com.example.myapplication.Database;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private static User user = null;

    public String email;
    public String password;
    public String name;
    public String sex;
    public int age;
    public ArrayList<String>  schedules_id;

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

    public void setData(int age, String email, String name, String password, String sex, ArrayList<String> schedule_id){
        schedules_id = schedule_id;
//         스케줄 아이디에 따라서 스케줄을 넣어준다

        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

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

}
