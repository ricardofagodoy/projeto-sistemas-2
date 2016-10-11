package com.findmytoilet.model;

import com.findmytoilet.R;
import com.findmytoilet.enums.Sex;
import com.findmytoilet.util.BitmapUtils;
import com.google.android.gms.maps.model.LatLng;

public class Toilet extends Locality{

    private Sex sex;
    private boolean paid;
    private boolean babyChange;
    private boolean wheel;

    public Toilet(){
    }

    public Toilet(LatLng location, Sex sex, boolean paid, boolean babyChange, boolean wheel){
        super(location);

        this.sex = sex;
        this.paid = paid;
        this.babyChange = babyChange;
        this.wheel = wheel;
    }

    public boolean isChildChange() {
        return babyChange;
    }

    public void setChildChange(boolean childChange) {
        this.babyChange = childChange;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isWheel() {
        return wheel;
    }

    public void setWheel(boolean wheel) {
        this.wheel = wheel;
    }
}
