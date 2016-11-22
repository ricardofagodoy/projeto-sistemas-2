package com.findmytoilet.callback;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.findmytoilet.R;
import com.findmytoilet.adapter.InformationWindowAdapter;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.dialog.LocationTypeDialog;
import com.findmytoilet.dialog.ToiletEditDialog;
import com.findmytoilet.dialog.WaterEditDialog;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.fragment.ActionButtonFragment;
import com.findmytoilet.fragment.FilterFragment;
import com.findmytoilet.network.LocalityHttp;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class MapCallback implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener, GoogleMap.OnMapClickListener {

    private static final String TAG = MapCallback.class.getName();

    private MapController mapController;
    private Context context;

    private ActionButtonFragment actionButtons;

    public MapCallback(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mapController = MapController.initialize(context, googleMap);

        // Map listeners registered
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnInfoWindowClickListener(this);

        // Adapter to customized InfoWindow
        googleMap.setInfoWindowAdapter(new InformationWindowAdapter(context));

        // Load localities first time
        LocalityHttp.initialize(context, mapController).loadLocalities();

        // Register action button callback
        actionButtons = (ActionButtonFragment) ((Activity) context).
                getFragmentManager().findFragmentById(R.id.actions);

        actionButtons.addListener(mapController);

        // Register filter buttons callback
        ((FilterFragment) ((FragmentActivity) context).
                getSupportFragmentManager().
                findFragmentById(R.id.filters)).addListener(mapController);
    }

    @Override
    public void onMapLongClick(LatLng point) {
        mapController.drawPin(point);
        this.onMapClick(point);
    }

    @Override
    public void onMapClick(LatLng point) {
        actionButtons.changeActionState(false, null);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        Object tag = marker.getTag();

        if (tag != null)
            switch ((MarkerTags) tag) {

                case TOILET:
                    new ToiletEditDialog(context, mapController.getLocalityFromMarker(marker)).show();
                    marker.hideInfoWindow();
                    actionButtons.changeActionState(false, null);
                    break;

                case WATER:
                    new WaterEditDialog(context, mapController.getLocalityFromMarker(marker)).show();
                    marker.hideInfoWindow();
                    actionButtons.changeActionState(false, null);
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