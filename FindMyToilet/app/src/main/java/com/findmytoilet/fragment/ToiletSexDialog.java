package com.findmytoilet.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

public class ToiletSexDialog extends Dialog {

    private Context context;

    public ToiletSexDialog(final Context context)
    {
        super(context);
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.fragment_sex);
        this.setCancelable(true);

        LocationTypeDialog.dialogs.add(this);

        View unisex = findViewById(R.id.unisex);
        View man = findViewById(R.id.man);
        View woman = findViewById(R.id.woman);

        unisex.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ToiletConfirmation(context).show();
            }
        });

        man.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ToiletConfirmation(context).show();
            }
        });

        woman.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new ToiletConfirmation(context).show();
            }
        });

    }


}
