package com.findmytoilet.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.findmytoilet.R;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.findmytoilet.util.BitmapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapController {

    private static final Float INITIAL_ZOOM = 16.0f;
    private static MapController instance = null;

    private GoogleMap map;
    private Context context;
    private List<Locality> localityList;

    private UserController user;

    private Marker pin;
    private Bitmap toiletImage;
    private Bitmap waterImage;

    public static MapController getInstance() {
        return MapController.instance;
    }

    public static MapController getInstance(Context ctx, GoogleMap map) {

        if (instance == null)
            MapController.instance = new MapController(ctx, map);

        return MapController.getInstance();
    }

    protected MapController(Context ctx, GoogleMap map) {

        this.map = map;
        this.context = ctx;

        // Takes care of user ID, location and preferences
        user = new UserController(ctx, this);

        //Create Toilet and Water images
        toiletImage = BitmapUtils.bitmapFromResource(context, R.drawable.toilet);
        toiletImage = BitmapUtils.toMapMarkerSize(toiletImage);

        waterImage = BitmapUtils.bitmapFromResource(context, R.drawable.water);
        waterImage = BitmapUtils.toMapMarkerSize(waterImage);

        // Create PIN marker
        Bitmap pinBitmap = BitmapUtils.bitmapFromResource(context, R.drawable.pin);
        pinBitmap = BitmapUtils.toMapMarkerSize(pinBitmap);

        this.pin = map.addMarker(new MarkerOptions().
                position(new LatLng(0, 0)).
                icon(BitmapDescriptorFactory.fromBitmap(pinBitmap)));

        this.pin.setTag(MarkerTags.PIN);

        // Map general settings
        UiSettings settings = map.getUiSettings();

        settings.setZoomControlsEnabled(false);
        settings.setCompassEnabled(false);
        settings.setMapToolbarEnabled(false);
        settings.setMyLocationButtonEnabled(false);

        // Blue dot
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            Log.e("ERROR", Log.getStackTraceString(e));
        }
    }

    public void addLocalities(Locality locality) {
        localityList.add(locality);

        if (locality instanceof Toilet) {
            map.addMarker(new MarkerOptions().
                    position(locality.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(toiletImage))).setTag(MarkerTags.TOILET);
        }

        if (locality instanceof Water) {
            map.addMarker(new MarkerOptions().
                    position(locality.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(waterImage))).setTag(MarkerTags.WATER);
        }
    }

    public void loadLocalities() {

        //TODO: LOAD FROM DATABASE
        localityList = new ArrayList<>();

        localityList.add(new Toilet(new LatLng(-22.832587, -47.051994), Sex.MALE, true, false, false));
        localityList.add(new Toilet(new LatLng(-22.833585, -47.055992), Sex.MALE, true, false, false));
        localityList.add(new Toilet(new LatLng(-22.834582, -47.054990), Sex.MALE, true, false, false));

        localityList.add(new Water(new LatLng(-22.832587, -47.05299), true));
        localityList.add(new Water(new LatLng(-22.835589, -47.05095), true));
    }

    public void drawLocalities() {

        for (Locality locality : localityList) {

            if (locality instanceof Toilet) {
                map.addMarker(new MarkerOptions().
                        position(locality.getCurrentLocation()).
                        icon(BitmapDescriptorFactory.fromBitmap(toiletImage))).setTag(MarkerTags.TOILET);
            }

            if (locality instanceof Water) {
                map.addMarker(new MarkerOptions().
                        position(locality.getCurrentLocation()).
                        icon(BitmapDescriptorFactory.fromBitmap(waterImage))).setTag(MarkerTags.WATER);
            }
        }
    }

    public void drawPin(LatLng point) {
        this.pin.setPosition(point);
        this.pin.showInfoWindow();
        this.animateCameraPosition(point);
    }

    public LatLng getPinPosition() {
        return this.pin.getPosition();
    }

    public void clearPin() {
        this.pin.setPosition(new LatLng(0, 0));
        this.pin.hideInfoWindow();
    }

    public void centerOnUser() {
        this.user.centerOnUser();
    }

    public void animateCameraPosition(LatLng position) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, INITIAL_ZOOM));
    }
}
