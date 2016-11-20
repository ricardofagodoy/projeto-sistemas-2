package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.model.Water;
import com.findmytoilet.network.LocalityHttp;

public class WaterConfirmationDialog extends Dialog {

    private static final String TAG = WaterConfirmationDialog.class.getName();

    private Context context;

    private Boolean coldActive;
    private Boolean filteredActive;
    private Boolean likeActive;

    public WaterConfirmationDialog(final Context context) {
        super(context);
        this.context = context;
        coldActive = false;
        filteredActive = false;
        likeActive = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_water_confirmation);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        final FloatingActionButton cold = (FloatingActionButton) findViewById(R.id.cold);
        final FloatingActionButton filtered = (FloatingActionButton) findViewById(R.id.filtered);

        View confirm = findViewById(R.id.confirm);

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = coldActive ? R.color.filterColor : R.color.filterColorSelected;

                coldActive = !coldActive;

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        filtered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filteredActive ? R.color.filterColor : R.color.filterColorSelected;

                filteredActive = !filteredActive;

                filtered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapController mapController = MapController.getInstance();

                LocalityHttp.getInstance().createLocality(
                        new Water(mapController.getPinPosition(), coldActive, filteredActive, 0));

                for (Dialog d : LocationTypeDialog.dialogs)
                    d.dismiss();

                LocationTypeDialog.dialogs.clear();
                mapController.clearPin();
            }
        });
    }
}
