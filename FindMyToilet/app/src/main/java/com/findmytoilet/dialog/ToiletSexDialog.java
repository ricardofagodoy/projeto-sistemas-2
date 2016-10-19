package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.findmytoilet.R;
import com.findmytoilet.enums.Sex;

public class ToiletSexDialog extends Dialog {

    private static final String TAG = ToiletSexDialog.class.getName();

    private Context context;

    public ToiletSexDialog(final Context context) {
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
        View male = findViewById(R.id.male);
        View female = findViewById(R.id.female);

        unisex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToiletConfirmationDialog(context, Sex.UNISEX).show();
            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToiletConfirmationDialog(context, Sex.MALE).show();
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ToiletConfirmationDialog(context, Sex.FEMALE).show();
            }
        });

    }


}
