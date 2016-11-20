package com.findmytoilet.network;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.findmytoilet.R;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.LocalityList;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.model.Water;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocalityHttp {

    private static final String TAG = LocalityHttp.class.getName();

    private static LocalityHttp instance = null;

    private final String REST_API;
    private final LocalityCallback callback;
    private final Context context;
    private ObjectMapper mapper;
    private OkHttpClient client;
    private List<Locality> localities;
    private Request request;
    private final MediaType JSON;


    public static LocalityHttp initialize(Context ctx, LocalityCallback callback) {
        if (instance == null)
            instance = new LocalityHttp(ctx, callback);

        return getInstance();
    }

    public static LocalityHttp getInstance() {
        return instance;
    }

    protected LocalityHttp(Context ctx, LocalityCallback callback) {
        this.context = ctx;
        mapper = new ObjectMapper();
        client = new OkHttpClient();
        REST_API = ctx.getString(R.string.url_api);
        JSON = MediaType.parse("application/json; charset=utf-8");
        this.callback = callback;
    }

    public void loadLocalities() {
        try{
            request = new Request.Builder().url(REST_API).build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) {
                    // Error


                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // For the example, you can show an error dialog or a toast
                            // on the main UI thread
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String json = response.body().string();

                    Log.i("loadLocations",json);


                    LocalityList localityList = mapper.readValue(json, LocalityList.class);

                    localities = new ArrayList<Locality>();

                    for (Toilet toilet : localityList.getToilet())
                        localities.add(toilet);



                    for (Water water : localityList.getWater())
                        localities.add(water);

                    Log.i("Estabelecimentos: ",localities.toString());


                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null)
                                callback.OnLocalitiesLoaded(localities);
                        }
                    });


                }
            });

        }catch(Exception e){
            Log.e("loadLocations", "Erro", e);
        }
    }

    public void createLocality(final Locality locality) {

        try{
            //Object to JSON in String
            String jsonInString = mapper.writeValueAsString(locality);

            RequestBody body = RequestBody.create(JSON, jsonInString);
            Request request = new Request.Builder()
                    .url(REST_API)
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, IOException e) {
                    // Error


                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // For the example, you can show an error dialog or a toast
                            // on the main UI thread
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String resp = response.body().string();

                    Log.i("createLocality",resp);

                    if (resp.equals("true"))
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (callback != null)
                                    callback.OnLocalityCreated(locality);
                            }
                        });


                }
            });

        }catch(Exception e){
            Log.e("createLocality", "Erro", e);
        }
    }

    public interface LocalityCallback {
        void OnLocalitiesLoaded(List<Locality> localities);

        void OnLocalityCreated(Locality locality);
    }
}
