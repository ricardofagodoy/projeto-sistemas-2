package com.findmytoilet.callback;

import android.content.Context;
import android.graphics.Bitmap;
import com.findmytoilet.R;
import com.findmytoilet.adapter.InformationWindowAdapter;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.dialog.LocationTypeDialog;
import com.findmytoilet.dialog.ToiletEditDialog;
import com.findmytoilet.dialog.WaterEditDialog;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.util.BitmapUtils;
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

        mapController = MapController.getInstance(context, googleMap);

        // Map listeners registered
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        // Adapter to customized InfoWindow
        googleMap.setInfoWindowAdapter(new InformationWindowAdapter(context));

        // Draw user marker
        mapController.drawInitialPosition();

        // TODO: REMOVE FAKE CODE
        // CREATE FAKE TOILET
        Bitmap testToilet = BitmapUtils.bitmapFromResource(context, R.drawable.toilet);
        testToilet = BitmapUtils.toMapMarkerSize(testToilet);

        Marker marker = googleMap.addMarker(new MarkerOptions().
                position(new LatLng(-22.832587, -47.051994)).
                icon(BitmapDescriptorFactory.fromBitmap(testToilet)));

        marker.setTag(MarkerTags.TOILET);

        // CREATE FAKE WATER FOUNTAIN
        Bitmap testWater = BitmapUtils.bitmapFromResource(context, R.drawable.water);
        testWater = BitmapUtils.toMapMarkerSize(testWater);

        marker = googleMap.addMarker(new MarkerOptions().
                position(new LatLng(-22.832587, -47.052999)).
                icon(BitmapDescriptorFactory.fromBitmap(testWater)));

        marker.setTag(MarkerTags.WATER);
    }

    @Override
    public void onMapLongClick (LatLng point) {
        mapController.drawPin(point);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Object tag = marker.getTag();

        if (tag != null)
            switch ((MarkerTags)tag) {

                case TOILET:
                    new ToiletEditDialog(context).show();
                break;

                case WATER:
                    new WaterEditDialog(context).show();
                break;

                case PIN:
                    new LocationTypeDialog(context).show();
                break;

                default:
                    new LocationTypeDialog(context).show();
                break;
            }
    }
}