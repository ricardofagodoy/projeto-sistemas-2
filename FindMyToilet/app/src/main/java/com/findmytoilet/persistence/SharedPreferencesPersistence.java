package com.findmytoilet.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.findmytoilet.model.Filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesPersistence {

    private static ObjectMapper mapper;
    private static SharedPreferences pref;
    private static Editor edit;
    private static Map<String, Integer> map;

    public SharedPreferencesPersistence(){

    }

    public static void initialize(Context context) {
        pref = PreferenceManager.getDefaultSharedPreferences(context);
        mapper = new ObjectMapper();
        getPreferenceRating();
    }

    public static Filter getPreferenceFilters(){
        try {
            String filtersJSON = pref.getString("filters", null);

            if (filtersJSON != null)
                return mapper.readValue(filtersJSON, Filter.class);

        }catch(IOException e)
        {
            Log.e("Getting user filters",e.getMessage());
        }

        return null;
    }

    public static void setPreferenceFilters(Filter filter) {

        try {
            edit = pref.edit();

            edit.putString("filters", mapper.writeValueAsString(filter));

            edit.commit();
        }catch (IOException e) {
            Log.e("Getting user filters", e.getMessage());
        }
    }

    public static Map<String, Integer> getPreferenceRating(){

        if (map != null)
            return map;

        try {
            String filtersJSON = pref.getString("rating", null);

            if (filtersJSON != null) {
                map = mapper.readValue(filtersJSON, new TypeReference<Map<String, Integer>>() {
                });
                return map;
            }

        }catch(IOException e)
        {
            Log.e("Getting user ratings",e.getMessage());
        }

        map = new HashMap<String, Integer>();

        return map;
    }

    public static void setPreferenceRating(String id, Integer rating) {
        if (map == null)
            return;

        try {
            map.put(id, rating);

            edit = pref.edit();

            edit.putString("rating", mapper.writeValueAsString(map));

            edit.commit();
        }catch (IOException e) {
            Log.e("Getting user ratings", e.getMessage());
        }
    }



}
