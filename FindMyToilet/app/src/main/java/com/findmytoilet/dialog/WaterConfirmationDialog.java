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

public class WaterConfirmationDialog extends Dialog {

    private static final String TAG = WaterConfirmationDialog.class.getName();

    private Context context;

    private boolean coldActive;

    public WaterConfirmationDialog(final Context context) {
        super(context);
        this.context = context;
        coldActive = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_water_confirmation);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        final FloatingActionButton cold = (FloatingActionButton) findViewById(R.id.cold);
        final FloatingActionButton hot = (FloatingActionButton) findViewById(R.id.hot);

        View confirm = findViewById(R.id.confirm);

        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coldActive = true;
                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                hot.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
            }
        });

        hot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coldActive = false;

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
                hot.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Dialog d : LocationTypeDialog.dialogs)
                    d.dismiss();

                LocationTypeDialog.dialogs.clear();
            }
        });

        hot.callOnClick();
    }
}
