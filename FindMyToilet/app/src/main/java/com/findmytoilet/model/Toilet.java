package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;
import com.google.android.gms.maps.model.LatLng;

public class Toilet extends Locality {

    private Sex sex;
    private boolean baby;
    private boolean wheel;

    public Toilet(){}

    public Toilet(LatLng location, Sex sex, boolean baby, boolean wheel){
        super(location);

        this.sex = sex;
        this.baby = baby;
        this.wheel = wheel;
    }

    public boolean isBaby() {
        return baby;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public boolean isWheel() {
        return wheel;
    }

    public void setWheel(boolean wheel) {
        this.wheel = wheel;
    }
}
