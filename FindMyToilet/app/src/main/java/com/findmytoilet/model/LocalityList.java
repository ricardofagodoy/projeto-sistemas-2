package com.findmytoilet.model;


import java.util.List;

public class LocalityList {

    private List<Toilet> toilet;
    private List<Water> water;

    public LocalityList(){
    }

    public List<Toilet> getToilet() {
        return toilet;
    }

    public void setToilet(List<Toilet> toilet) {
        this.toilet = toilet;
    }

    public List<Water> getWater() {
        return water;
    }

    public void setWater(List<Water> water) {
        this.water = water;
    }
}
