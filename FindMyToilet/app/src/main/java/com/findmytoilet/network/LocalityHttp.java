package com.findmytoilet.network;

import android.content.Context;

import com.findmytoilet.R;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LocalityHttp {

    private static final String TAG = LocalityHttp.class.getName();

    private static LocalityHttp instance = null;

    private final String REST_API;
    private final LocalityCallback callback;

    public static LocalityHttp initialize(Context ctx, LocalityCallback callback) {

        if (instance == null)
            instance = new LocalityHttp(ctx, callback);

        return getInstance();
    }

    public static LocalityHttp getInstance() {
        return instance;
    }

    protected LocalityHttp(Context ctx, LocalityCallback callback) {
        REST_API = ctx.getString(R.string.url_api);
        this.callback = callback;
    }

    public void loadLocalities() {

        //TODO: LOAD FROM DATABASE
        List<Locality> localities = new ArrayList<>();

        localities.add(new Toilet(new LatLng(-22.832587, -47.051994), Sex.MALE, true, false));
        localities.add(new Toilet(new LatLng(-22.833585, -47.055992), Sex.MALE, true, false));
        localities.add(new Toilet(new LatLng(-22.834582, -47.054990), Sex.MALE, true, false));

        localities.add(new Water(new LatLng(-22.832587, -47.05299), true));
        localities.add(new Water(new LatLng(-22.835589, -47.05095), true));

        if (callback != null)
            callback.OnLocalitiesLoaded(localities);
    }

    public void createLocality(Locality locality) {
        if (callback != null)
            callback.OnLocalityCreated(locality);
    }

    public interface LocalityCallback {
        void OnLocalitiesLoaded(List<Locality> localities);

        void OnLocalityCreated(Locality locality);
    }
}
