package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Water;
import com.google.android.gms.maps.model.Marker;

public class WaterEditDialog extends Dialog{

    private static final String TAG = WaterEditDialog.class.getName();

    private Context context;
    private Water water;
    private boolean coldActive;
    private boolean filteredActive;

    public WaterEditDialog(final Context context, Locality locality) {
        super(context);
        this.water = (Water) locality;
        coldActive = false;
        filteredActive = false;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.edit_water);
        this.setCancelable(true);

        View reportButton = findViewById(R.id.report);
        final FloatingActionButton cold = (FloatingActionButton) findViewById(R.id.cold);
        final FloatingActionButton filtered = (FloatingActionButton) findViewById(R.id.filtered);

        if (water.isCold()) {
            coldActive = true;
            cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }

        if (water.isFiltered()) {
            filteredActive = true;
            filtered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }


        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = coldActive ? R.color.filterColor : R.color.filterColorSelected;

                coldActive = !coldActive;

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                water.setCold(coldActive);
            }
        });

        filtered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filteredActive ? R.color.filterColor : R.color.filterColorSelected;

                filteredActive = !filteredActive;

                filtered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                water.setFiltered(filteredActive);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReportDialog(context).show();
            }
        });

    }

}
