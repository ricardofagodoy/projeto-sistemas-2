package com.findmytoilet.model;

import com.google.android.gms.maps.model.Marker;

public class LocalityMarker {

    private Marker marker;
    private Locality locality;

    public LocalityMarker(Marker marker, Locality locality) {
        this.marker = marker;
        this.locality = locality;
    }

    public LocalityMarker() {
        this(null, null);
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }
}
