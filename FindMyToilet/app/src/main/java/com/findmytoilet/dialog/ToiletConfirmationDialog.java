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

public class ToiletConfirmationDialog extends Dialog {

    private Context context;

    private boolean cleanActive;
    private boolean paidActive;
    private boolean wheelActive;

    public ToiletConfirmationDialog(final Context context)
    {
        super(context);
        this.context = context;
        cleanActive = false;
        paidActive = false;
        wheelActive = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_toilet_confirmation);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        final FloatingActionButton clean = (FloatingActionButton) findViewById(R.id.clean);
        final FloatingActionButton paid = (FloatingActionButton) findViewById(R.id.paid);
        final FloatingActionButton wheel = (FloatingActionButton) findViewById(R.id.wheel);

        final View confirm = findViewById(R.id.confirm);

        clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = cleanActive ? R.color.filterColor : R.color.filterColorSelected;

                cleanActive = !cleanActive;

                clean.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        paid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = paidActive ? R.color.filterColor : R.color.filterColorSelected;

                paidActive = !paidActive;

                paid.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        wheel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int color = wheelActive ? R.color.filterColor : R.color.filterColorSelected;

                wheelActive = !wheelActive;

                wheel.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
            }
        });

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (Dialog d : LocationTypeDialog.dialogs)
                    d.dismiss();

                LocationTypeDialog.dialogs.clear();
            }
        });

    }






}
