package com.findmytoilet.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

public class WaterConfirmation extends Dialog {

    private Context context;

    public WaterConfirmation(final Context context)
    {
        super(context);
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_water_confirmation);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        View cold = findViewById(R.id.cold);
        View hot = findViewById(R.id.hot);
        View confirm = findViewById(R.id.confirm);

        cold.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        hot.setOnClickListener(new View.OnClickListener()
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
