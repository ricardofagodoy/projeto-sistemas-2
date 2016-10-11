package com.findmytoilet.model;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class Locality {

    private LatLng location;
    private Integer id;

    public Locality(){

    }

    public Locality(LatLng location){
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCurrentLocation(LatLng current) {
        this.location = current;
    }

    public LatLng getCurrentLocation() {
        return this.location;
    }
}