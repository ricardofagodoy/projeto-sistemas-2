package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

public class ToiletConfirmationDialog extends Dialog {

    private Context context;

    public ToiletConfirmationDialog(final Context context)
    {
        super(context);
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_toilet_confirmation);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        View clean = findViewById(R.id.clean);
        View paid = findViewById(R.id.paid);
        View wheel = findViewById(R.id.wheel);
        View confirm = findViewById(R.id.confirm);

        clean.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        paid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        wheel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

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
