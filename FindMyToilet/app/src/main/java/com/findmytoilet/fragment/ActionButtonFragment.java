package com.findmytoilet.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.google.android.gms.maps.model.LatLng;

public class ActionButtonFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ActionButtonFragment() {}

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

                // TODO: GET POSITION FROM GPS
                LatLng current = new LatLng(-22.832587, -47.052924);
                MapController.getInstance().animateCameraPosition(current);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {}
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
