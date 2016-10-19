package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

public class WaterEditDialog extends Dialog {

    private static final String TAG = WaterEditDialog.class.getName();

    private Context context;

    public WaterEditDialog(final Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.edit_water);
        this.setCancelable(true);

        View reportButton = findViewById(R.id.report);

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReportDialog(context).show();
            }
        });
    }
}
