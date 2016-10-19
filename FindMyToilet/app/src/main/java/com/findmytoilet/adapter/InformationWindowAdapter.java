package com.findmytoilet.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import com.findmytoilet.R;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.fragment.ActionButtonFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InformationWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = InformationWindowAdapter.class.getName();

    private Context context;

    public InformationWindowAdapter(Context context) {
        this.context = context;
    }

    private View loadView(Marker marker) {

        Object tag = marker.getTag();
        int viewId = 0;

        if (tag != null)
            switch ((MarkerTags) tag) {

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

        return ((Activity) context).getLayoutInflater().inflate(viewId, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return this.loadView(marker);
    }

    private void toggleEmergencyButton(boolean state) {

        ActionButtonFragment actionButtons = (ActionButtonFragment) ((Activity) context).
                getFragmentManager().findFragmentById(R.id.actions);

        actionButtons.changeActionState(state);
    }
}
