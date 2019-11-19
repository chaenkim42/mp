package com.example.myapplication.Database;

public class User {
    public String email;
    public String password;
    public String name;
    public String sex;
    public int age;

    public User(int age, String email, String name, String password, String sex){
        this.email = email;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
}
