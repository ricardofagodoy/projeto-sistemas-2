package com.findmytoilet.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.findmytoilet.R;
import com.findmytoilet.callback.AutocompletePlaceCallback;
import com.findmytoilet.callback.MapCallback;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new MapCallback(this));

        // Configure autocomplete search box
        configureAutocompletePlace();
    }

    private void configureAutocompletePlace() {

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        // Create the filter
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_NONE)
                .build();

        // Set it to autocomplete box
        autocompleteFragment.setFilter(typeFilter);

        // Callback for events
        autocompleteFragment.setOnPlaceSelectedListener(new AutocompletePlaceCallback());
    }
}