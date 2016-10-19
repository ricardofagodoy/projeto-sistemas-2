package com.findmytoilet.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.findmytoilet.R;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.model.Filter;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.LocalityMarker;
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

import java.util.List;

public class MapController {

    private static MapController instance = null;

    private GoogleMap map;
    private Context context;

    private UserController user;
    private LocalityController localities;

    private Float initialZoom;
    private Marker pin;
    private Bitmap toiletImage;
    private Bitmap waterImage;

    /* Constructors */
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

        this.initialZoom = Float.parseFloat(ctx.getString(R.string.initial_zoom));

        // Takes care of user ID, location and preferences
        user = new UserController(ctx, this);

        // Takes care of all localities
        localities = new LocalityController();


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

    /* Draw things on map */
    public void drawPin(LatLng point) {
        this.pin.setPosition(point);
        this.pin.showInfoWindow();
        this.animateCameraPosition(point);
    }

    protected void drawLocality(Locality locality) {

        // TODO: APPLY FILTERS
        if (locality instanceof Toilet) {

            map.addMarker(new MarkerOptions().
                    position(locality.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(toiletImage))).
                    setTag(MarkerTags.TOILET);

        } else if (locality instanceof Water) {

            map.addMarker(new MarkerOptions().
                    position(locality.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(waterImage))).
                    setTag(MarkerTags.WATER);
        }
    }

    public void drawLocalities() {

        List<Locality> all = localities.getAll();

        for (Locality loc : all)
            if (loc != null)
                this.drawLocality(loc);

    }

    /* Related methods */
    public void addLocality(Locality locality) {

        if (locality == null)
            return;

        this.localities.addLocality(locality);
        this.drawLocality(locality);
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

    public void applyFilter(Filter filter) {
        Log.i("Filters", filter.toString());
    }

    /* Camera methods */
    public void animateCameraPosition(LatLng position) {
        map.animateCamera(CameraUpdateFactory.newLatLng(position));
    }

    public void animateCameraPositionZoom(LatLng position, Float zoom) {

        if (zoom == null)
            zoom = initialZoom;

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    }

    public void moveCameraPositionZoom(LatLng position, Float zoom) {

        if (zoom == null)
            zoom = initialZoom;

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoom));
    }
}
