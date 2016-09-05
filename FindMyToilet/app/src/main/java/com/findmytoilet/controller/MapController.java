package com.findmytoilet.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.findmytoilet.R;
import com.findmytoilet.util.BitmapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapController {

    private GoogleMap map;
    private Context context;

    public MapController(Context ctx, GoogleMap map) {
        this.map = map;
        this.context = ctx;

        UiSettings settings = map.getUiSettings();

        // Map general settings
        settings.setZoomControlsEnabled(false);
        settings.setCompassEnabled(false);
        settings.setMapToolbarEnabled(true);

        //map.setPadding(0, 1500, 0, 0);
    }

    public void drawInitialPosition() {

        // Retrieve from GPS
        LatLng current = new LatLng(-22.832587, -47.051994);
        map.moveCamera(CameraUpdateFactory.newLatLng(current));

        try {
            map.setMyLocationEnabled(false);
        } catch (SecurityException e) {
            Log.e("ERROR", Log.getStackTraceString(e));
        }

        map.setMinZoomPreference(16.0f);
    }

    public void drawNewPin(LatLng point) {

        Bitmap pin = BitmapUtils.bitmapFromResource(context, R.drawable.pin);
        pin = BitmapUtils.toMapMarkerSize(pin);

        map.addMarker(new MarkerOptions().
                position(point).
                icon(BitmapDescriptorFactory.fromBitmap(pin)).
                title("Nova localização").
                snippet("Rua Abde, 65")).
                showInfoWindow();
    }
}
