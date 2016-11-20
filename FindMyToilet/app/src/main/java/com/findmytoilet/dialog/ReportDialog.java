package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.findmytoilet.R;
import com.findmytoilet.network.LocalityHttp;

public class ReportDialog extends Dialog {

    private static final String TAG = ReportDialog.class.getName();
    private String localityID;


    public ReportDialog(final Context context, String id) {
        super(context);
        this.localityID = id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.report);
        this.setCancelable(true);

        View reportButton = findViewById(R.id.report);
        final EditText reason = (EditText) findViewById(R.id.reason);


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalityHttp.getInstance().reportLocality(localityID ,reason.getText().toString());
                ReportDialog.this.dismiss();
            }
        });
    }
}
