package com.findmytoilet.callback;

import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class AutocompletePlaceCallback implements PlaceSelectionListener {

    @Override
    public void onPlaceSelected(Place place) {
        Log.i("PLACES", "Place: " + place.getName());
    }

    @Override
    public void onError(Status status) {
        Log.i("PLACES", "An error occurred: " + status);
    }
}
