package com.findmytoilet.callback;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.fragment.LocationTypeDialog;
import com.findmytoilet.util.BitmapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapCallback implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener {

    private MapController mapController;
    private Context context;

    public MapCallback(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mapController = new MapController(context, googleMap);

        // Map listeners registered
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        // Draw user marker
        mapController.drawInitialPosition();

        // TESTE
        /*
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            // Use default InfoWindow frame
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            // Defines the contents of the InfoWindow
            @Override
            public View getInfoContents(Marker arg0) {

                // Getting view from the layout file info_window_layout
                View v = ((Activity)context).getLayoutInflater().inflate(R.layout.windowlayout, null);

                // Getting the position from the marker
                LatLng latLng = arg0.getPosition();

                // Getting reference to the TextView to set latitude
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);


                        // Returning the view containing InfoWindow contents
                return v;
            }
        });

        // Adding and showing marker while touching the GoogleMap
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
                googleMap.clear();

                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position on the MarkerOptions
                markerOptions.position(arg0);

                // Animating to the currently touched position
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));

                // Adding marker on the GoogleMap
                Marker marker = googleMap.addMarker(markerOptions);

                // Showing InfoWindow on the GoogleMap
                marker.showInfoWindow();

            }
        }); */

        Bitmap testToilet = BitmapUtils.bitmapFromResource(context, R.drawable.toilet);
        testToilet = BitmapUtils.toMapMarkerSize(testToilet);

        Marker marker = googleMap.addMarker(new MarkerOptions().
                position(new LatLng(-22.832587, -47.051994)).
                icon(BitmapDescriptorFactory.fromBitmap(testToilet)).
                title("Nova localização").
                snippet("Rua Abde, 65"));

        marker.setTag(2);
        marker.showInfoWindow();

        Bitmap testWater = BitmapUtils.bitmapFromResource(context, R.drawable.water);
        testWater = BitmapUtils.toMapMarkerSize(testWater);

        googleMap.addMarker(new MarkerOptions().
                position(new LatLng(-22.832587, -47.052999)).
                icon(BitmapDescriptorFactory.fromBitmap(testWater)).
                title("Nova localização").
                snippet("Rua Abde, 65")).
                showInfoWindow();

    }

    @Override
    public void onMapLongClick (LatLng point) {
        mapController.drawNewPin(point);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Object tag = marker.getTag();

        if (tag != null && (int)tag == 2)
            new LocationTypeDialog(context).show();
    }
}