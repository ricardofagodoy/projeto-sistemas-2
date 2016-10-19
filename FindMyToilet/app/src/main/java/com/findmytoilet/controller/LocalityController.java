package com.findmytoilet.controller;

import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class LocalityController {

    private List<Locality> localities;

    public LocalityController() {
        this.localities = new ArrayList<>();
        this.loadLocalities();
    }

    public void addLocality(Locality locality) {
        localities.add(locality);
    }

    public List<Locality> getAll() {
        return this.localities;
    }

    public void loadLocalities() {

        //TODO: LOAD FROM DATABASE
        localities.add(new Toilet(new LatLng(-22.832587, -47.051994), Sex.MALE, true, false, false));
        localities.add(new Toilet(new LatLng(-22.833585, -47.055992), Sex.MALE, true, false, false));
        localities.add(new Toilet(new LatLng(-22.834582, -47.054990), Sex.MALE, true, false, false));

        localities.add(new Water(new LatLng(-22.832587, -47.05299), true));
        localities.add(new Water(new LatLng(-22.835589, -47.05095), true));
    }
}
