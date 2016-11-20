package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;
import com.google.android.gms.maps.model.LatLng;

public class Toilet extends Locality {

    private Sex sex;
    private Boolean baby;
    private Boolean wheel;
    private Boolean paid;

    public Toilet(){}

    public Toilet(LatLng location, Sex sex, Boolean baby, Boolean wheel, Boolean paid, Integer rating){
        super(location);
        this.paid = paid;
        this.sex = sex;
        this.baby = baby;
        this.wheel = wheel;
        this.setRating(rating);
    }

    public Boolean isBaby() {
        return baby;
    }

    public void setBaby(Boolean baby) {
        this.baby = baby;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean isWheel() {
        return wheel;
    }

    public void setWheel(Boolean wheel) {
        this.wheel = wheel;
    }

    public String toString(){
        return "id: " + this.getId() + " Location: " + this.getLocation() + " Sex: " + this.getSex() + " Baby:" + this.isBaby() + " Paid: " + this.isPaid();
    }

}
