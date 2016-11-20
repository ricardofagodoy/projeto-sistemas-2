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

            Water water = (Water) this.getLocality();

            // COLD is active
            if (filter.isCold())
                return water.isCold();

            // LIKE is active
            if (filter.isWaterLike() && water.getRating() < 0)
                return false;

            // FILTERED is active
            if (filter.isWaterFiltered())
                return water.isFiltered();

            return true;
        }

        // If it's TOILET
        Toilet toilet = (Toilet) this.getLocality();

        if (filter.isWheel() && !toilet.isWheel())
            return false;

        if (filter.isBaby() && !toilet.isBaby())
            return false;

        if (filter.isToiletLike() && toilet.getRating() < 0)
            return false;

        if (!toilet.getSex().equals(Sex.UNISEX) && !toilet.getSex().equals(filter.getSex()))
            return false;

        return true;
    }
}
