package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;

public class Filter {

    private Sex sex;
    private Boolean cold;
    private Boolean baby;
    private Boolean wheel;
    private Boolean toiletLike;
    private Boolean waterLike;

    public Filter(Boolean cold, Sex sex, Boolean baby, Boolean wheel, Boolean toiletLike, Boolean waterLike) {
        this.cold = cold;
        this.sex = sex;
        this.baby = baby;
        this.wheel = wheel;
        this.toiletLike = toiletLike;
        this.waterLike = waterLike;
    }

    public Filter() {
        this(false, Sex.UNISEX, false, false, false, false);
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

    @Override
    public String toString() {
        return "Sex: " + this.getSex().name() + " | " + "Cold: " + this.isCold() +
                " | " + "Baby: " + this.isBaby() + " | " + "Wheel: " + this.isWheel() +
                " | " + "ToiletLike: " + this.isToiletLike() + " | " + "WaterLike: " + this.isWaterLike();
    }
}
