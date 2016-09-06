package com.findmytoilet.controller;

import com.google.android.gms.maps.model.LatLng;

// TODO: FusedLocationProviderApi
public class LocationController {

    private static LocationController instance = null;
    private LatLng current;

    protected LocationController() {
        this.current = null;
    }

    public static LocationController getInstance() {

        if (LocationController.instance == null)
            LocationController.instance = new LocationController();

        return LocationController.instance;
    }

    public void setCurrentLocation(LatLng current) {
        this.current = current;
    }

    public LatLng getCurrentLocation() {
        return this.current;
    }
}
