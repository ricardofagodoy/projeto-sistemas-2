package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.findmytoilet.R;

public class ReportDialog extends Dialog {

    public ReportDialog(final Context context)
    {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.report);
        this.setCancelable(true);
    }
}
