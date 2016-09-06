package com.findmytoilet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.findmytoilet.R;
import com.findmytoilet.dialog.LocationTypeDialog;
import com.findmytoilet.enums.MarkerTags;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InformationWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InformationWindowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        Object tag = marker.getTag();
        int viewId = 0;

        if (tag != null)
            switch ((MarkerTags)tag) {

                case TOILET:
                    viewId = R.layout.toilet_window;
                break;

                case WATER:
                    viewId = R.layout.water_window;
                break;

                case PIN:
                    viewId = R.layout.new_location_window;
                break;

                default:
                    viewId = R.layout.new_location_window;
                break;
            }

        return ((Activity)context).getLayoutInflater().inflate(viewId, null);
    }
}
