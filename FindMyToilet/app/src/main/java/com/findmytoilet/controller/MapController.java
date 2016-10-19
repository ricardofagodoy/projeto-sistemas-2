package com.findmytoilet.controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.findmytoilet.R;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.fragment.ActionButtonFragment;
import com.findmytoilet.fragment.FilterFragment;
import com.findmytoilet.model.Filter;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.LocalityMarker;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.findmytoilet.network.LocalityHttp;
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

public class MapController implements LocalityHttp.LocalityCallback,
        ActionButtonFragment.ActionButtonCallback,
        FilterFragment.FilterCallback {

    private static final String TAG = MapController.class.getName();

    private static MapController instance = null;

    private GoogleMap map;
    private Context context;

    private UserController user;
    private Filter filter;

    private Float initialZoom;
    private Marker pin;

    private List<LocalityMarker> localityMarkers;

    private Bitmap toiletImage;
    private Bitmap waterImage;

    /* Constructors */
    public static MapController getInstance() {
        return MapController.instance;
    }

    public static MapController initialize(Context ctx, GoogleMap map) {

        if (instance == null)
            MapController.instance = new MapController(ctx, map);

        return MapController.getInstance();
    }

    protected MapController(Context ctx, GoogleMap map) {

        this.map = map;
        this.context = ctx;

        this.initialZoom = Float.parseFloat(ctx.getString(R.string.initial_zoom));

        this.localityMarkers = new ArrayList<>();

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

    /* Draw things on map */
    public void drawPin(LatLng point) {
        this.pin.setPosition(point);
        this.pin.showInfoWindow();
        this.animateCameraPosition(point);
    }

    private LocalityMarker addLocality(Locality l) {

        Marker marker = null;

        if (l instanceof Toilet) {

            marker = map.addMarker(new MarkerOptions().
                    position(l.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(toiletImage)));

            marker.setTag(MarkerTags.TOILET);

        } else if (l instanceof Water) {

            marker = map.addMarker(new MarkerOptions().
                    position(l.getCurrentLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(waterImage)));

            marker.setTag(MarkerTags.WATER);
        }

        LocalityMarker lm = new LocalityMarker(marker, l);
        localityMarkers.add(lm);

        return lm;
    }

    /* Callback implementations */
    @Override
    public void onCenterUserClick() {
        this.user.centerOnUser();
    }

    private void applyFilter() {

        if (filter == null)
            return;

        for (LocalityMarker loc : localityMarkers)
            if (loc != null && loc.getMarker() != null)
                loc.getMarker().setVisible(loc.matchesFilter(filter));
    }

    @Override
    public void onFilterChanged(Filter filter) {
        Log.i("Filters", filter.toString());
        this.filter = filter;
        this.applyFilter();
    }

    @Override
    public void OnLocalitiesLoaded(List<Locality> localities) {

        if (localities == null)
            return;

        if (localityMarkers != null && !localityMarkers.isEmpty())
            localityMarkers.clear();

        // Load all localities to marker list
        for (Locality l : localities)
            addLocality(l);
    }

    @Override
    public void OnLocalityCreated(Locality locality) {

        if (locality == null)
            return;

        LocalityMarker lm = this.addLocality(locality);

        // Apply filter to new locality
        lm.getMarker().setVisible(lm.matchesFilter(filter));
    }


    /* Helpers */
    public LatLng getPinPosition() {
        return this.pin.getPosition();
    }

    public void clearPin() {
        this.pin.setPosition(new LatLng(0, 0));
        this.pin.hideInfoWindow();
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
