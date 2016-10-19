package com.findmytoilet.callback;

import android.util.Log;

import com.findmytoilet.activity.MainActivity;
import com.findmytoilet.controller.MapController;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class AutocompletePlaceCallback implements PlaceSelectionListener {

    private static final String TAG = AutocompletePlaceCallback.class.getName();

    @Override
    public void onPlaceSelected(Place place) {

        MapController.getInstance().animateCameraPosition(place.getLatLng());

        Log.i("PLACES", "Place: " + place.getName());
    }

    @Override
    public void onError(Status status) {
        Log.i("PLACES", "An error occurred: " + status);
    }
}
