package com.findmytoilet.adapter;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.enums.MarkerTags;
import com.findmytoilet.fragment.ActionButtonFragment;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.vision.text.Text;

import java.util.List;
import java.util.Locale;

public class InformationWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private static final String TAG = InformationWindowAdapter.class.getName();

    private Context context;
    private Geocoder geocoder;
    private List<Address> addresses;

    public InformationWindowAdapter(Context context) {
        this.context = context;
        addresses = null;
    }

    private View loadView(Marker marker) {

        Object tag = marker.getTag();

        int viewId = 0;
        Locality locality = MapController.getInstance().getLocalityFromMarker(marker);
        View view = null;

        if (tag != null)
            switch ((MarkerTags) tag) {

                case TOILET:
                    viewId = R.layout.toilet_window;
                    view = ((Activity) context).getLayoutInflater().inflate(viewId, null);

                    if (locality != null) {

                        Toilet toilet = (Toilet) locality;

                        if (!toilet.isBaby())
                            view.findViewById(R.id.baby).setVisibility(View.GONE);

                        if (!toilet.isWheel())
                            view.findViewById(R.id.wheel).setVisibility(View.GONE);

                        if (!toilet.isPaid())
                            view.findViewById(R.id.paid).setVisibility(View.GONE);

                        if (toilet.getRating() >= 0)
                            view.findViewById(R.id.toiletDislike).setVisibility(View.GONE);
                        else
                            view.findViewById(R.id.toiletLike).setVisibility(View.GONE);


                        switch (toilet.getSex()) {
                            case UNISEX:
                                view.findViewById(R.id.male).setVisibility(View.GONE);
                                view.findViewById(R.id.female).setVisibility(View.GONE);
                                break;
                            case MALE:
                                view.findViewById(R.id.unisex).setVisibility(View.GONE);
                                view.findViewById(R.id.female).setVisibility(View.GONE);
                                break;
                            case FEMALE:
                                view.findViewById(R.id.male).setVisibility(View.GONE);
                                view.findViewById(R.id.unisex).setVisibility(View.GONE);
                                break;
                            default:
                                view.findViewById(R.id.unisex).setVisibility(View.GONE);
                                view.findViewById(R.id.male).setVisibility(View.GONE);
                                view.findViewById(R.id.female).setVisibility(View.GONE);
                                break;
                        }

                        ((TextView) view.findViewById(R.id.address)).setText(locality.getStreetName());
                    }
                    toggleEmergencyButton(true, locality);

                    break;

                case WATER:
                    viewId = R.layout.water_window;
                    view = ((Activity) context).getLayoutInflater().inflate(viewId, null);
                    if (locality != null) {
                        Water water = (Water) locality;

                        if (!water.isCold())
                            view.findViewById(R.id.cold).setVisibility(View.GONE);

                        if (!water.isFiltered())
                            view.findViewById(R.id.filtered).setVisibility(View.GONE);

                        if (water.getRating() >= 0)
                            view.findViewById(R.id.waterDislike).setVisibility(View.GONE);
                        else
                            view.findViewById(R.id.waterLike).setVisibility(View.GONE);

                        ((TextView) view.findViewById(R.id.address)).setText(locality.getStreetName());

                    }
                    toggleEmergencyButton(true, locality);
                    break;

                case PIN:
                    viewId = R.layout.new_location_window;
                    view = ((Activity) context).getLayoutInflater().inflate(viewId, null);

                    try {

                        geocoder = new Geocoder(context, Locale.getDefault());
                        addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);

                        if ((addresses != null) && (addresses.size() > 0))
                            ((TextView) view.findViewById(R.id.address)).setText(addresses.get(0).getAddressLine(0));


                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }


                    toggleEmergencyButton(false, null);
                    break;

                default:
                    viewId = R.layout.new_location_window;
                    view = ((Activity) context).getLayoutInflater().inflate(viewId, null);
                    toggleEmergencyButton(false, null);
                    break;
            }

        return view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return this.loadView(marker);
    }

    private void toggleEmergencyButton(boolean state, Locality locality) {

        ActionButtonFragment actionButtons = (ActionButtonFragment) ((Activity) context).
                getFragmentManager().findFragmentById(R.id.actions);

        actionButtons.changeActionState(state, locality);
    }
}
