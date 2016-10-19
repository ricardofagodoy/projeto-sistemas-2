package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;
import com.google.android.gms.maps.model.Marker;

public class LocalityMarker {

    private Marker marker;
    private Locality locality;

    public LocalityMarker(Marker marker, Locality locality) {
        this.marker = marker;
        this.locality = locality;
    }

    public Marker getMarker() {
        return marker;
    }

    public Locality getLocality() {
        return locality;
    }

    public boolean matchesFilter(Filter filter) {

        if (filter == null)
            return true;

        // If it's WATER
        if (this.getLocality() instanceof Water) {

            // COLD is active
            if (filter.isCold())
                return ((Water) this.getLocality()).isCold();

            return true;
        }

        // If it's TOILET
        Toilet toilet = (Toilet) this.getLocality();

        if (filter.isWheel() && !toilet.isWheel())
            return false;

        if (filter.isBaby() && !toilet.isBaby())
            return false;

        if (!filter.getSex().equals(Sex.UNISEX) && !toilet.getSex().equals(filter.getSex()))
            return false;

        return true;
    }
}
