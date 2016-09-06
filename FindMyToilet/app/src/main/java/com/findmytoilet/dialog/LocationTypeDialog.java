package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

import java.util.ArrayList;
import java.util.List;

public class LocationTypeDialog extends Dialog {

    private Context context;
    public static List<Dialog> dialogs = new ArrayList<Dialog>();

    public LocationTypeDialog(final Context context)
    {
        super(context);
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_location_type);
        this.setCancelable(true);

        dialogs.add(this);

        View toiletType = findViewById(R.id.toiletType);
        View waterType = findViewById(R.id.waterType);

        toiletType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ToiletSexDialog(context).show();
            }
        });

        waterType.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new WaterConfirmationDialog(context).show();
            }
        });

    }


}
