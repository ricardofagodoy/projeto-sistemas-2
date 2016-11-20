package com.findmytoilet.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.findmytoilet.R;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Filter;

import java.io.IOException;

public class FilterFragment extends Fragment {

    private static final String TAG = FilterFragment.class.getName();

    private Context context;
    private FilterCallback mListener;
    private Filter filter;
    private SharedPreferences pref;
    private ObjectMapper mapper;
    private Editor edit;

    private boolean toiletActive;
    private boolean waterActive;
    private boolean sexActive;
    private boolean toiletLikeActive;
    private boolean waterLikeActive;

    public FilterFragment() {

        this.filter = new Filter();
        this.toiletActive = false;
        this.waterActive = false;
        this.sexActive = false;
        this.toiletLikeActive = false;
        this.waterLikeActive = false;
    }

    public void addListener(FilterCallback listener) {
        this.mListener = listener;
        applyFilter();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            mapper = new ObjectMapper();

            pref = PreferenceManager.getDefaultSharedPreferences(context);

            String filtersJSON = pref.getString("filters", null);

            if (filtersJSON != null)
                filter = mapper.readValue(filtersJSON, Filter.class);

        }catch(IOException e)
        {
            Log.e("Getting user filters",e.getMessage());
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filters, container, false);

        final View toiletFilters = view.findViewById(R.id.toiletFilters);

        final Sex[] sexPositions = {Sex.UNISEX, Sex.MALE, Sex.FEMALE};
        final FloatingActionButton sex = (FloatingActionButton) view.findViewById(R.id.unisex);
        final FloatingActionButton man = (FloatingActionButton) view.findViewById(R.id.man);
        final FloatingActionButton woman = (FloatingActionButton) view.findViewById(R.id.woman);

        final FloatingActionButton toiletLike = (FloatingActionButton) view.findViewById(R.id.toiletLike);

        final FloatingActionButton baby = (FloatingActionButton) view.findViewById(R.id.baby);
        final FloatingActionButton wheel = (FloatingActionButton) view.findViewById(R.id.wheel);

        final View waterFilters = view.findViewById(R.id.waterFilters);
        final FloatingActionButton cold = (FloatingActionButton) view.findViewById(R.id.cold);
        final FloatingActionButton waterFiltered = (FloatingActionButton) view.findViewById(R.id.waterFiltered);

        final FloatingActionButton waterLike = (FloatingActionButton) view.findViewById(R.id.waterLike);



        toiletFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = toiletActive ? View.GONE : View.VISIBLE;

                if (waterActive)
                    waterFilters.callOnClick();

                if (sexActive)
                    sex.callOnClick();

                if (toiletLikeActive)
                    toiletLike.callOnClick();


                toiletActive = !toiletActive;

                sex.setVisibility(state);
                toiletLike.setVisibility(state);
                baby.setVisibility(state);
                wheel.setVisibility(state);
            }
        });

        waterFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = waterActive ? View.GONE : View.VISIBLE;

                if (toiletActive)
                    toiletFilters.callOnClick();

                if (waterLikeActive)
                    waterLike.callOnClick();

                waterActive = !waterActive;

                cold.setVisibility(state);
                waterLike.setVisibility(state);
                waterFiltered.setVisibility(state);
            }
        });

        sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = sexActive ? View.GONE : View.VISIBLE;

                sexActive = !sexActive;

                man.setVisibility(state);
                woman.setVisibility(state);
            }
        });

        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sex temp = sexPositions[1];
                sexPositions[1] = sexPositions[0];
                sexPositions[0] = temp;

                filter.setSex(temp);
                applyFilter();

                Drawable tempDrawable = sex.getDrawable();
                sex.setImageDrawable(man.getDrawable());
                man.setImageDrawable(tempDrawable);
            }
        });

        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Sex temp = sexPositions[2];
                sexPositions[2] = sexPositions[0];
                sexPositions[0] = temp;

                filter.setSex(temp);
                applyFilter();

                Drawable tempDrawable = sex.getDrawable();
                sex.setImageDrawable(woman.getDrawable());
                woman.setImageDrawable(tempDrawable);
            }
        });

        toiletLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isToiletLike() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleToiletLike();
                applyFilter();

                toiletLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        waterLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isWaterLike() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleWaterLike();
                applyFilter();

                waterLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isBaby() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleBaby();
                applyFilter();

                baby.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });

        wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isWheel() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleWheel();
                applyFilter();

                wheel.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isCold() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleCold();
                applyFilter();

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });


        waterFiltered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.isWaterFiltered() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleWaterFiltered();
                applyFilter();

                waterFiltered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });


        //Adjust the filters if they are selected on the user filter settings
        if (filter.isWaterFiltered())
            waterFiltered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));

        if (filter.isCold())
            cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));

        if (filter.isWaterLike())
            waterLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));

        if (filter.getSex() == Sex.MALE) {
            Sex temp = sexPositions[1];
            sexPositions[1] = sexPositions[0];
            sexPositions[0] = temp;

            Drawable tempDrawable = sex.getDrawable();
            sex.setImageDrawable(man.getDrawable());
            man.setImageDrawable(tempDrawable);
        }
        else if (filter.getSex() == Sex.FEMALE) {
            Sex temp = sexPositions[2];
            sexPositions[2] = sexPositions[0];
            sexPositions[0] = temp;

            Drawable tempDrawable = sex.getDrawable();
            sex.setImageDrawable(woman.getDrawable());
            woman.setImageDrawable(tempDrawable);
        }

        if (filter.isBaby())
            baby.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));

        if (filter.isWheel())
            wheel.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));

        if (filter.isToiletLike())
            toiletLike.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));


        return view;
    }

    private void applyFilter() {
        if (mListener != null)
            mListener.onFilterChanged(filter);

        try {
             edit = pref.edit();

            edit.putString("filters", mapper.writeValueAsString(filter));

            edit.commit();
        }catch(Exception e){
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface FilterCallback {
        void onFilterChanged(Filter filter);
    }
}
