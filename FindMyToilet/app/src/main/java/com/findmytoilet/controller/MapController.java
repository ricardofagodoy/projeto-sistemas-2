package com.findmytoilet.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

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
    private Float maxDistanceWalk;
    private Marker pin;

    private List<LocalityMarker> localityMarkers;

    private Bitmap toiletImage;
    private Bitmap waterImage;

    private Location userLocation;

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

        this.maxDistanceWalk = Float.parseFloat(ctx.getString(R.string.max_distance_walk));
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
                    position(l.getLocation()).
                    icon(BitmapDescriptorFactory.fromBitmap(toiletImage)));

            marker.setTag(MarkerTags.TOILET);

        } else if (l instanceof Water) {

            marker = map.addMarker(new MarkerOptions().
                    position(l.getLocation()).
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

    @Override
    public void onEmergencyClick() {

        float[] results = new float[1];
        float minDistance = -1;
        LocalityMarker closest = null;
        userLocation = this.user.getUserPosition();

        for (LocalityMarker lm : localityMarkers)
            if (lm.getLocality() instanceof Toilet)
                if (lm.matchesFilter(filter)) {
                    Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                            lm.getLocality().getLocation().latitude, lm.getLocality().getLocation().longitude, results);

                    if ((results[0] < minDistance) || (minDistance == -1)) {
                        minDistance = results[0];
                        closest = lm;
                    }
                }

        if (closest != null) {
            closest.getMarker().showInfoWindow();
            this.animateCameraPositionZoom(closest.getMarker().getPosition(), null);
        }
        else
            Toast.makeText(context, "Nenhum banheiro encontrado!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTraceRouteClick(Locality locality) {

        char mode;
        float[] results = new float[1];

        userLocation = this.user.getUserPosition();

        Location.distanceBetween(userLocation.getLatitude(), userLocation.getLongitude(),
                locality.getLocation().latitude, locality.getLocation().longitude, results);
        if(results[0] <= maxDistanceWalk)
            mode = 'w';
        else
            mode = 'd';


        Uri gmmIntentUri = Uri.parse("google.navigation:q=" + locality.getLocation().latitude + "," + locality.getLocation().longitude + "&mode=" + mode);


        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
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

        applyFilter();
    }

    @Override
    public void OnLocalityCreated(Locality locality) {

        if (locality == null)
            return;

        LocalityMarker lm = this.addLocality(locality);

        // Apply filter to new locality
        lm.getMarker().setVisible(lm.matchesFilter(filter));
    }

    @Override
    public void OnLocalityUpdated(Locality locality) {

        if (locality == null)
            return;

        LocalityMarker lm = this.getLocalityMarkerFromLocality(locality);

        // Apply filter to the updated locality
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

    public Locality getLocalityFromMarker(Marker marker){
        for (int i = 0;i < localityMarkers.size(); i++)
            if (localityMarkers.get(i).getMarker().equals(marker))
                return localityMarkers.get(i).getLocality();

        return null;
    }

    public LocalityMarker getLocalityMarkerFromLocality(Locality locality){
        for (int i = 0;i < localityMarkers.size(); i++)
            if (localityMarkers.get(i).getLocality().equals(locality))
                return localityMarkers.get(i);

        return null;
    }
}
