package com.findmytoilet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findmytoilet.R;
import com.findmytoilet.model.Locality;
import com.google.android.gms.maps.model.LatLng;

public class ActionButtonFragment extends Fragment {

    private static final String TAG = ActionButtonFragment.class.getName();
    private Locality locality;
    private View emergencyButton;
    private View route;

    private ActionButtonCallback mListener;

    public ActionButtonFragment() {
    }

    public void addListener(ActionButtonCallback listener) {
        this.mListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_action_button, container, false);

        View userLocation = view.findViewById(R.id.userLocation);
        emergencyButton = view.findViewById(R.id.emergency);
        route = view.findViewById(R.id.traceRoute);

        emergencyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onEmergencyClick();
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onTraceRouteClick(locality);
            }
        });

        userLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null)
                    mListener.onCenterUserClick();
            }
        });

        return view;
    }

    public void changeActionState(boolean state, Locality locality) {

        this.locality = locality;

        if (state) {
            emergencyButton.setVisibility(View.GONE);
            route.setVisibility(View.VISIBLE);
        } else {
            emergencyButton.setVisibility(View.VISIBLE);
            route.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface ActionButtonCallback {
        void onCenterUserClick();
        void onEmergencyClick();
        void onTraceRouteClick(Locality locality);
    }
}
