package com.findmytoilet.model;

import com.google.android.gms.maps.model.LatLng;

public class Water extends Locality{

    private Boolean cold;
    private Boolean filtered;
    private Boolean like;

    public Water(){
    }

    public Water(LatLng location, Boolean cold, Boolean filtered, Boolean like){
        super(location);
        this.cold = cold;
        this.filtered = filtered;
        this.like = like;
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

    public Boolean isLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }
}
