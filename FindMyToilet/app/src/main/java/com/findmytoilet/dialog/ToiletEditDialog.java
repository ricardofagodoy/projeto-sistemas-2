package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;

public class ToiletEditDialog extends Dialog {

    private static final String TAG = ToiletEditDialog.class.getName();

    private Context context;

    public ToiletEditDialog(final Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.edit_toilet);
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
