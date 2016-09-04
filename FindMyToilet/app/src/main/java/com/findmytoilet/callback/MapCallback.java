package com.findmytoilet.callback;

import android.content.Context;
import android.content.Intent;

import com.findmytoilet.activity.MainActivity;
import com.findmytoilet.activity.NewLocationActivity;
import com.findmytoilet.controller.MapController;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapCallback implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private MapController mapController;
    private Context context;

    public MapCallback(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapController = new MapController(context, googleMap);

        // Map listeners registered
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        // Draw user marker
        mapController.drawInitialPosition();
    }

    @Override
    public void onMapLongClick (LatLng point) {
        mapController.drawNewPin(point);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        context.startActivity(new Intent(context, NewLocationActivity.class));
    }
}