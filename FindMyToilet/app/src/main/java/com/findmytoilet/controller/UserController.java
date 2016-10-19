package com.findmytoilet.controller;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

public class UserController implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = UserController.class.getName();

    private GoogleApiClient mGoogleApiClient;
    private MapController map;

    private Boolean firstPosition;

    public UserController(Context context, MapController map) {

        this.map = map;
        this.firstPosition = true;

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }

    public void centerOnUser() {

        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

            Location current = null;

            // Try to get current location, may be null
            try {
                current = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            } catch (SecurityException se) {
                Log.e("UserController", "Error on LocationServices", se);
            }

            if (current != null) {
                this.onLocationChanged(current);
                return;
            }

            // If it' really null, fire location update
            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            try {
                LocationServices.FusedLocationApi.requestLocationUpdates(
                        mGoogleApiClient, mLocationRequest, this);
            } catch (SecurityException se) {
                Log.e("UserController", "Error on LocationServices", se);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("UserController", "GoogleApiClient connection is active!");

        this.centerOnUser();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("UserController", "GoogleApiClient connection has been suspend");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("UserController", "GoogleApiClient connection has failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("UserController", "GoogleApiClient location changed!");

        if (this.map != null && location != null) {

            // First time, when open app, no animation
            if (this.firstPosition) {

                this.map.moveCameraPositionZoom(new LatLng
                        (location.getLatitude(), location.getLongitude()), null);

                this.firstPosition = false;
            } else
                this.map.animateCameraPositionZoom(new LatLng
                        (location.getLatitude(), location.getLongitude()), null);

            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }
}
