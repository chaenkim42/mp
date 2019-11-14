package com.example.myapplication.Database;

import java.util.List;

public class Day {

    private int order;
    private List<Location> spots;

    public Day(int order){
        this.order = order;
    }

    public Day(int order,  List<Location> locationList){
        this.order = order;
        this.spots = locationList;
    }

    public void addSpot(int onWhichIndex, Location location){
        this.spots.add(onWhichIndex, location);
//            int newSpotLength = spots.length + 1;
//            Location newSpots[] = new Location[newSpotLength];
//            for(int i=0; i<onWhichOrder; i++){
//                if(i == onWhichOrder-1){
//                    newSpots[i] = location;
//                }else{
//                    newSpots[i] = this.spots[i];
//                }
//            }
//            for(int i=onWhichOrder; i<newSpotLength; i++){
//                newSpots[i] = this.spots[i-1];
//            }
//            this.spots = newSpots;
    }

    public List<Location> getSpots() {
        return spots;
    }
}
