package com.example.myapplication.Database;

public class NewPlace {

    private static NewPlace newPlace = null;
    private int selectedDay;
    private Place selectedPlace;
    private String selectedTripName;

    public static NewPlace getInstance(){
        if(newPlace ==null){
            newPlace = new NewPlace(-1, null, "tmp");
        }
        return newPlace;
    }

    private NewPlace(int selectedDay, Place selectedPlace, String selectedTripName){
        this.selectedDay = selectedDay;
        this.selectedPlace = selectedPlace;
        this.selectedTripName = selectedTripName;
    }

    public int getSelectedDay() {
        return selectedDay;
    }

    public Place getSelectedPlace() {
        return selectedPlace;
    }

    public String getSelectedTripName() {
        return selectedTripName;
    }

    public void setSelectedDay(int selectedDay) {
        this.selectedDay = selectedDay;
    }

    public void setSelectedPlace(Place selectedPlace) {
        this.selectedPlace = selectedPlace;
    }

    public void setSelectedTripName(String selectedTripName) {
        this.selectedTripName = selectedTripName;
    }
}
