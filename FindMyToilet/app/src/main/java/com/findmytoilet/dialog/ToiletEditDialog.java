package com.findmytoilet.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.findmytoilet.R;
import com.findmytoilet.controller.MapController;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.model.Locality;
import com.findmytoilet.model.Toilet;
import com.findmytoilet.network.LocalityHttp;
import com.google.android.gms.maps.model.Marker;

public class ToiletEditDialog extends Dialog {

    private static final String TAG = ToiletEditDialog.class.getName();

    private Context context;
    private Toilet toilet;
    private boolean babyActive;
    private boolean wheelActive;
    private boolean paidActive;


    public ToiletEditDialog(final Context context, Locality locality) {
        super(context);
        this.toilet =  (Toilet) locality;
        babyActive = false;
        wheelActive = false;
        paidActive = false;
        this.context = context;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.edit_toilet);
        this.setCancelable(true);

        View reportButton = findViewById(R.id.report);
        View editButton = findViewById(R.id.edit);
        final ImageView like = (ImageView) findViewById(R.id.toiletLike);
        final ImageView dislike = (ImageView) findViewById(R.id.toiletDislike);


        final FloatingActionButton unisex = (FloatingActionButton) findViewById(R.id.unisex);
        final FloatingActionButton male = (FloatingActionButton) findViewById(R.id.male);
        final FloatingActionButton female = (FloatingActionButton) findViewById(R.id.female);

        final FloatingActionButton baby = (FloatingActionButton) findViewById(R.id.baby);
        final FloatingActionButton wheel = (FloatingActionButton) findViewById(R.id.wheel);
        final FloatingActionButton paid = (FloatingActionButton) findViewById(R.id.paid);


        ((TextView) findViewById(R.id.address)).setText(toilet.getStreetName());

        switch (toilet.getSex()){
            case UNISEX:
                unisex.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                break;
            case MALE:
                male.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                break;
            case FEMALE:
                female.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                break;
        }



        if (toilet.isBaby()) {
            babyActive = true;
            baby.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }

        if (toilet.isPaid()) {
            paidActive = true;
            paid.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }

        if (toilet.isWheel()) {
            wheelActive = true;
            wheel.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
        }

        unisex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toilet.setSex(Sex.UNISEX);
                updateLocality();

                unisex.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                female.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
                male.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));

            }
        });

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toilet.setSex(Sex.MALE);
                updateLocality();

                male.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                female.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
                unisex.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toilet.setSex(Sex.FEMALE);
                updateLocality();

                female.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColorSelected)));
                male.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
                unisex.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.filterColor)));
            }
        });

        baby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = babyActive ? R.color.filterColor : R.color.filterColorSelected;

                babyActive = !babyActive;

                baby.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                toilet.setBaby(babyActive);
                updateLocality();
            }
        });

        wheel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = wheelActive ? R.color.filterColor : R.color.filterColorSelected;

                wheelActive = !wheelActive;

                wheel.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                toilet.setWheel(wheelActive);
                updateLocality();
            }
        });

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = paidActive ? R.color.filterColor : R.color.filterColorSelected;

                paidActive = !paidActive;

                paid.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, color)));
                toilet.setPaid(paidActive);
                updateLocality();
            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!like.getTag().equals("pressed")) {
                    like.setImageResource(R.drawable.likepressed);
                    dislike.setImageResource(R.drawable.dislike);

                    if (dislike.getTag().equals("pressed"))
                        LocalityHttp.getInstance().rateLocality(toilet.getId(), 2);
                    else
                        LocalityHttp.getInstance().rateLocality(toilet.getId(), 1);

                    like.setTag("pressed");
                    dislike.setTag("");

                }
            }
        });

        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dislike.getTag().equals("pressed")) {
                    dislike.setImageResource(R.drawable.dislikepressed);
                    like.setImageResource(R.drawable.like);

                    if (like.getTag().equals("pressed"))
                        LocalityHttp.getInstance().rateLocality(toilet.getId(), -2);
                    else
                        LocalityHttp.getInstance().rateLocality(toilet.getId(), -1);

                    dislike.setTag("pressed");
                    like.setTag("");

                }
            }
        });


        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReportDialog(context, toilet.getId()).show();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unisex.setClickable(!unisex.isClickable());
                male.setClickable(!male.isClickable());
                female.setClickable(!female.isClickable());
                wheel.setClickable(!wheel.isClickable());
                paid.setClickable(!paid.isClickable());
                baby.setClickable(!baby.isClickable());
            }
        });

        editButton.callOnClick();
    }

    private void updateLocality(){
        LocalityHttp.getInstance().updateLocality(toilet);
    }






}
