package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;

public class Filter {

    private Sex sex;
    private Boolean cold;
    private Boolean baby;
    private Boolean wheel;
    private Boolean toiletLike;
    private Boolean waterLike;
    private boolean waterFiltered;

    public Filter(Sex sex, Boolean baby, Boolean wheel, Boolean toiletLike, Boolean cold, Boolean waterLike, Boolean waterFiltered) {
        this.cold = cold;
        this.sex = sex;
        this.baby = baby;
        this.wheel = wheel;
        this.toiletLike = toiletLike;
        this.waterLike = waterLike;
        this.waterFiltered = waterFiltered;
    }

    public Filter() {
        this(Sex.UNISEX, false, false, false, false, false, false);
    }

    public Boolean isWheel() {
        return wheel;
    }

    public void toggleWheel() {
        this.wheel = !this.wheel;
    }

    public Boolean isCold() {
        return cold;
    }

    public void toggleCold() {
        this.cold = !this.cold;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean isBaby() {
        return baby;
    }

    public void toggleBaby() {
        this.baby = !this.baby;
    }


    public Boolean isToiletLike() {
        return toiletLike;
    }

    public void toggleToiletLike() {
        this.toiletLike = !this.toiletLike;
    }


    public Boolean isWaterLike() {
        return waterLike;
    }

    public void toggleWaterLike() {
        this.waterLike = !this.waterLike;
    }

    public Boolean isWaterFiltered() {
        return waterFiltered;
    }

    public void toggleWaterFiltered() {
        this.waterFiltered = !this.waterFiltered;
    }

    @Override
    public String toString() {
        return "Sex: " + this.getSex().name() + " | " + "Cold: " + this.isCold() +
                " | " + "Baby: " + this.isBaby() + " | " + "Wheel: " + this.isWheel() +
                " | " + "ToiletLike: " + this.isToiletLike() + " | " + "WaterLike: " + this.isWaterLike()+ " | " + "WaterFiltered: " + this.isWaterFiltered() ;
    }
}
