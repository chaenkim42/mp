package com.example.myapplication;

public class Location {
    private String name;
    private int id;
    private int drawableId; // should be R.drawable.blahblah

    public Location(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Location(String name, int id, int drawableId){
        this.name = name;
        this.id = id;
        this.drawableId = drawableId;
    }

    public void setName(String s){
        this.name = s;
    }

    public void setId(int i){
        this.id = i;
    }

    public void setDrawableId(int i){
        this.drawableId = i;
    }

    public String getName(){return this.name;}
    public int getId(){return this.id;}
    public int getDrawableId(){return this.drawableId;}
}
