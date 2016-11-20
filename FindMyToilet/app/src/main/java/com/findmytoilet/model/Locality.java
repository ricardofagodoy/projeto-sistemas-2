package com.findmytoilet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Locality {

    private LatLng location;

    @JsonProperty ("_id")
    private String id;
    private Integer rating;
    private String streetName;

    public Locality(){

    }

    public Locality(com.google.android.gms.maps.model.LatLng location){
        this.location = new LatLng(location.latitude, location.longitude);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLocation(Double latitude, Double longitude) {
        this.location = new LatLng(latitude, longitude);
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public com.google.android.gms.maps.model.LatLng getLocation() {
        return this.location.getLatLng();
    }

    public Integer getRating(){   return this.rating;  }

    public void setRating(Integer rating){   this.rating = rating;   }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
}