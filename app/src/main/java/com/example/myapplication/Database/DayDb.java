package com.example.myapplication.Database;

import java.util.ArrayList;

public class DayDb {

    private int order;
    private String name;
    private ArrayList<Place> spots;

    public DayDb(int order){
        this.order = order;
    }

    public DayDb(int order, ArrayList<Place> placeList){
        this.order = order;
        this.spots = placeList;
    }

    public void addSpot(int onWhichIndex, Place place){
        this.spots.add(onWhichIndex, place);
//            int newSpotLength = spots.length + 1;
//            Place newSpots[] = new Place[newSpotLength];
//            for(int i=0; i<onWhichOrder; i++){
//                if(i == onWhichOrder-1){
//                    newSpots[i] = place;
//                }else{
//                    newSpots[i] = this.spots[i];
//                }
//            }
//            for(int i=onWhichOrder; i<newSpotLength; i++){
//                newSpots[i] = this.spots[i-1];
//            }
//            this.spots = newSpots;
    }

    public ArrayList<Place> getSpots() {
        return spots;
    }
    public int getOrder(){return order;}
}
