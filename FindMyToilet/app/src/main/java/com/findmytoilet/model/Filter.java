package com.findmytoilet.model;

import com.findmytoilet.enums.Sex;

public class Filter {

    private Boolean cold;
    private Sex sex;
    private Boolean baby;
    private Boolean paid;

    public Filter(Boolean cold, Sex sex, Boolean baby, Boolean paid) {
        this.cold = cold;
        this.sex = sex;
        this.baby = baby;
        this.paid = paid;
    }

    public Filter() {
        this(false, Sex.UNISEX, false, false);
    }

    public Boolean getCold() {
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

    public Boolean getBaby() {
        return baby;
    }

    public void toggleBaby() {
        this.baby = !this.baby;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void togglePaid() {
        this.paid = !this.paid;
    }

    @Override
    public String toString() {
        return "Sex: " + this.getSex().name() + " | " + "Cold: " + this.getCold() +
                " | " + "Baby: " + this.getBaby() + " | " + "Paid: " + this.getPaid();
    }
}
