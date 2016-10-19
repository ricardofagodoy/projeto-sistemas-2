package com.findmytoilet.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Filter;

public class FilterFragment extends Fragment {

    private Context context;

    private Filter filter;

    private boolean toiletActive;
    private boolean waterActive;
    private boolean sexActive;
    private boolean toiletRatingActive;
    private boolean waterRatingActive;

    public FilterFragment() {

        // TODO: LOAD USER PREFERENCES
        this.filter = new Filter();

        this.toiletActive = false;
        this.waterActive = false;
        this.sexActive = false;
        this.toiletRatingActive = false;
        this.waterRatingActive = false;
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

        final Sex[] sexPositions = {Sex.UNISEX, Sex.MALE, Sex.FEMALE};
        final FloatingActionButton sex = (FloatingActionButton) view.findViewById(R.id.unisex);
        final FloatingActionButton man = (FloatingActionButton) view.findViewById(R.id.man);
        final FloatingActionButton woman = (FloatingActionButton) view.findViewById(R.id.woman);

        final View ratingToilet = view.findViewById(R.id.ratingToilet);
        final View toiletHappy = view.findViewById(R.id.toiletHappy);
        final View toiletSad = view.findViewById(R.id.toiletSad);

        final FloatingActionButton paid = (FloatingActionButton) view.findViewById(R.id.paid);
        final FloatingActionButton baby = (FloatingActionButton) view.findViewById(R.id.baby);

        final View waterFilters = view.findViewById(R.id.waterFilters);
        final FloatingActionButton cold = (FloatingActionButton) view.findViewById(R.id.cold);

        final View ratingWater = view.findViewById(R.id.ratingWater);
        final View waterHappy = view.findViewById(R.id.waterHappy);
        final View waterSad = view.findViewById(R.id.waterSad);


        toiletFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                baby.setVisibility(state);
            }
        });

        waterFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        ratingToilet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = toiletRatingActive ? View.GONE : View.VISIBLE;

                toiletRatingActive = !toiletRatingActive;

                toiletHappy.setVisibility(state);
                toiletSad.setVisibility(state);
            }
        });

        ratingWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state = waterRatingActive ? View.GONE : View.VISIBLE;

                waterRatingActive = !waterRatingActive;

                waterHappy.setVisibility(state);
                waterSad.setVisibility(state);
            }
        });

        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.getBaby() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleBaby();
                applyFilter();

                baby.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.getPaid() ? R.color.filterColor : R.color.filterColorSelected;

                filter.togglePaid();
                applyFilter();

                paid.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filter.getCold() ? R.color.filterColor : R.color.filterColorSelected;

                filter.toggleCold();
                applyFilter();

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));

            }
        });

        return view;
    }

    private void applyFilter() {
        MapController.getInstance().applyFilter(filter);
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
}
