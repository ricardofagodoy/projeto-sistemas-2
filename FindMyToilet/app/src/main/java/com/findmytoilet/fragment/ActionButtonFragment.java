package com.findmytoilet.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.google.android.gms.maps.model.LatLng;

public class ActionButtonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ActionButtonFragment() {
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

        userLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapController.getInstance().centerOnUser();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    public void changeActionState(boolean state) {

        View emergencyButton = this.getView().findViewById(R.id.emergency);
        View route = this.getView().findViewById(R.id.traceRoute);

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
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
