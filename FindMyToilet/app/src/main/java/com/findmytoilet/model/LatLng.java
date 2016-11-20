package com.findmytoilet.model;

import java.io.Serializable;

public class LatLng implements Serializable {

    private Double latitude;
    private Double longitude;

    public LatLng(){

    }

    public LatLng(Double latitude, Double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public com.google.android.gms.maps.model.LatLng getLatLng(){
        return new com.google.android.gms.maps.model.LatLng(this.latitude ,this.longitude);
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String toString(){
        return "Latitude: " + this.latitude + " Longitude: " + this.longitude;

    }


}
