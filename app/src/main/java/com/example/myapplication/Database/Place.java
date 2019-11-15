package com.example.myapplication.Database;

public class Place {
    private String name;
    private double latitude;
    private double longitude;
    private String address;
    private String tel;
    private String time;
    private String fee;

    private int id;
    private int drawableId; // should be R.drawable.blahblah


    public Place(String name){
        this.name = name;
    }

    public Place(String name, double lat, double lon){
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
    }

    public Place(String name, double lat, double lon, String address,String tel){
        this.name = name;
        this.latitude = lat;
        this.longitude = lon;
        this.address = address;
        this.tel = tel;
    }
    public Place(){
        this.name = "tmp";
        this.latitude = 34.969347;
        this.longitude = 127.579537;
        this.address = "tmp";
        this.tel = "tmp";
    }

    public Place(String name, int id){
        this.name = name;
        this.id = id;
    }

    public Place(String name, int id, int drawableId){
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

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
