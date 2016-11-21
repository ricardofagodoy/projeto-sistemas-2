package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Water;
import com.findmytoilet.network.LocalityHttp;
import com.findmytoilet.persistence.SharedPreferencesPersistence;
import com.google.android.gms.maps.model.Marker;

public class WaterEditDialog extends Dialog{

    private static final String TAG = WaterEditDialog.class.getName();

    private Context context;
    private Water water;
    private boolean coldActive;
    private boolean filteredActive;

    public WaterEditDialog(final Context context, Locality locality) {
        super(context);
        this.water = (Water) locality;
        coldActive = false;
        filteredActive = false;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.edit_water);
        this.setCancelable(true);

        View reportButton = findViewById(R.id.report);
        View editButton = findViewById(R.id.edit);
        final ImageView like = (ImageView) findViewById(R.id.waterLike);
        final ImageView dislike = (ImageView) findViewById(R.id.waterDislike);

        final FloatingActionButton cold = (FloatingActionButton) findViewById(R.id.cold);
        final FloatingActionButton filtered = (FloatingActionButton) findViewById(R.id.filtered);

        ((TextView) findViewById(R.id.address)).setText(water.getStreetName());


        //Get the rating preference for this water
        Integer i = SharedPreferencesPersistence.getPreferenceRating().get(water.getId());

        if ((i != null) && (i == 1)) {
            like.setTag("pressed");
            like.setImageResource(R.drawable.likepressed);
        }

        if ((i != null) && (i == -1)) {
            dislike.setTag("pressed");
            dislike.setImageResource(R.drawable.dislikepressed);
        }


        if (water.isCold()) {
            coldActive = true;
            cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }

        if (water.isFiltered()) {
            filteredActive = true;
            filtered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }


        cold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = coldActive ? R.color.filterColor : R.color.filterColorSelected;

                coldActive = !coldActive;

                cold.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                water.setCold(coldActive);
                updateLocality();
            }
        });

        filtered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = filteredActive ? R.color.filterColor : R.color.filterColorSelected;

                filteredActive = !filteredActive;

                filtered.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                water.setFiltered(filteredActive);
                updateLocality();
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReportDialog(context, water.getId()).show();
            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!like.getTag().equals("pressed")) {
                    like.setImageResource(R.drawable.likepressed);
                    dislike.setImageResource(R.drawable.dislike);

                    if (dislike.getTag().equals("pressed")) {
                        LocalityHttp.getInstance().rateLocality(water.getId(), 2);
                        water.setRating(water.getRating() + 2);
                    }
                    else {
                        LocalityHttp.getInstance().rateLocality(water.getId(), 1);
                        water.setRating(water.getRating() + 1);
                    }

                    like.setTag("pressed");
                    dislike.setTag("");

                    SharedPreferencesPersistence.setPreferenceRating(water.getId(), 1);
                }
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dislike.getTag().equals("pressed")) {
                    dislike.setImageResource(R.drawable.dislikepressed);
                    like.setImageResource(R.drawable.like);


                    if (like.getTag().equals("pressed")) {
                        LocalityHttp.getInstance().rateLocality(water.getId(), -2);
                        water.setRating(water.getRating() - 2);
                    }
                    else {
                        LocalityHttp.getInstance().rateLocality(water.getId(), -1);
                        water.setRating(water.getRating() - 1);
                    }

                    dislike.setTag("pressed");
                    like.setTag("");

                    SharedPreferencesPersistence.setPreferenceRating(water.getId(), -1);
                }
            }
        });


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cold.setClickable(!cold.isClickable());
                filtered.setClickable(!filtered.isClickable());
            }
        });

        editButton.callOnClick();

    }

    private void updateLocality(){
        LocalityHttp.getInstance().updateLocality(water);
    }

}
