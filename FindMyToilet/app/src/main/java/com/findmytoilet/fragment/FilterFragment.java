package com.findmytoilet.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.findmytoilet.R;

public class FilterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private boolean toiletActive;
    private boolean waterActive;
    private boolean sexActive;
    private boolean toiletRatingActive;
    private boolean waterRatingActive;
    private boolean coldActive;
    private boolean cleanActive;
    private boolean paidActive;
    private Context context;

    public FilterFragment() {
        this.toiletActive = false;
        this.waterActive = false;
        this.sexActive = false;
        this.toiletRatingActive = false;
        this.waterRatingActive = false;
        this.coldActive = false;
        this.cleanActive = false;
        this.paidActive = false;}

    public static FilterFragment newInstance() {
        return new FilterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        final View toiletFilters = view.findViewById(R.id.toiletFilters);

        final View sex = view.findViewById(R.id.unisex);
        final View man = view.findViewById(R.id.man);
        final View woman = view.findViewById(R.id.woman);

        final View ratingToilet = view.findViewById(R.id.ratingToilet);
        final View toiletHappy = view.findViewById(R.id.toiletHappy);
        final View toiletSad = view.findViewById(R.id.toiletSad);

        final FloatingActionButton paid = (FloatingActionButton) view.findViewById(R.id.paid);
        final FloatingActionButton clean = (FloatingActionButton) view.findViewById(R.id.clean);

        final View waterFilters = view.findViewById(R.id.waterFilters);
        final FloatingActionButton cold = (FloatingActionButton) view.findViewById(R.id.cold);

        final View ratingWater = view.findViewById(R.id.ratingWater);
        final View waterHappy = view.findViewById(R.id.waterHappy);
        final View waterSad = view.findViewById(R.id.waterSad);


        toiletFilters.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int state = toiletActive ? View.GONE : View.VISIBLE;

                if (waterActive)
                    waterFilters.callOnClick();

                if (sexActive)
                    sex.callOnClick();

                if (toiletRatingActive)
                    ratingToilet.callOnClick();



                toiletActive = !toiletActive;

                sex.setVisibility(state);
                ratingToilet.setVisibility(state);
                paid.setVisibility(state);
                clean.setVisibility(state);
            }
        });

        waterFilters.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int state = waterActive ? View.GONE : View.VISIBLE;

                if (toiletActive)
                    toiletFilters.callOnClick();

                if (waterRatingActive)
                    ratingWater.callOnClick();

                waterActive = !waterActive;

                cold.setVisibility(state);
                ratingWater.setVisibility(state);
            }
        });

        sex.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int state = sexActive ? View.GONE : View.VISIBLE;

                sexActive = !sexActive;

                man.setVisibility(state);
                woman.setVisibility(state);
            }
        });

        ratingToilet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int state = toiletRatingActive ? View.GONE : View.VISIBLE;

                toiletRatingActive = !toiletRatingActive;

                toiletHappy.setVisibility(state);
                toiletSad.setVisibility(state);
            }
        });

        ratingWater.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int state = waterRatingActive ? View.GONE : View.VISIBLE;

                waterRatingActive = !waterRatingActive;

                waterHappy.setVisibility(state);
                waterSad.setVisibility(state);
            }
        });

        clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = cleanActive ? R.color.filterColor : R.color.filterColorSelected;

                cleanActive = !cleanActive;

                clean.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));

            }
        });

        paid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = paidActive ? R.color.filterColor : R.color.filterColorSelected;

                paidActive = !paidActive;

                paid.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));

            }
        });

        cold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = coldActive ? R.color.filterColor : R.color.filterColorSelected;

                coldActive = !coldActive;

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context,color)));

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
                 //   + " must implement OnFragmentInteractionListener");
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
