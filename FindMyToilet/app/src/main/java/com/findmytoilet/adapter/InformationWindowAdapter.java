package com.findmytoilet.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import com.findmytoilet.R;
import com.findmytoilet.activity.MainActivity;
import com.findmytoilet.dialog.LocationTypeDialog;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.fragment.ActionButtonFragment;
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
                    toggleEmergencyButton(true);
                break;

                case WATER:
                    viewId = R.layout.water_window;
                    toggleEmergencyButton(true);
                break;

                case PIN:
                    viewId = R.layout.new_location_window;
                    toggleEmergencyButton(false);
                break;

                default:
                    viewId = R.layout.new_location_window;
                    toggleEmergencyButton(false);
                break;
            }

        return ((Activity)context).getLayoutInflater().inflate(viewId, null);
    }

    private void toggleEmergencyButton(boolean state) {

        ActionButtonFragment actionButtons = (ActionButtonFragment)((Activity)context).
                getFragmentManager().findFragmentById(R.id.actions);

        actionButtons.changeActionState(state);
    }
}