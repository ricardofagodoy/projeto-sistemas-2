package com.findmytoilet.model;

import com.google.android.gms.maps.model.LatLng;

public class Water extends Locality{

    private boolean cold;

    public Water(){
    }

    public Water(LatLng location, boolean cold){
        super(location);
        this.cold = cold;
    }

    public boolean isCold() {
        return cold;
    }

    public void setCold(boolean cold) {
        this.cold = cold;
    }
}
