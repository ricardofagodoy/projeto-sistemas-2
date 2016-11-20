package com.findmytoilet.model;

import com.google.android.gms.maps.model.LatLng;

public class Water extends Locality{

    private Boolean cold;
    private Boolean filtered;

    public Water(){
    }

    public Water(LatLng location, Boolean cold, Boolean filtered, Integer rating){
        super(location);
        this.cold = cold;
        this.filtered = filtered;
        this.setRating(rating);
    }

    public Boolean isCold() {
        return cold;
    }

    public void setCold(Boolean cold) {
        this.cold = cold;
    }

    public Boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(Boolean filtered) {
        this.filtered = filtered;
    }

    public String toString(){
        return "id: " + this.getId() + " Location: " + this.getLocation() + " Cold:" + this.isCold() + " Filtered: " + this.isFiltered();
    }

}
