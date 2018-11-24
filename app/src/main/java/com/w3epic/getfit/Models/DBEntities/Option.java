package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class Option {
    private String uid;
    private String unit; // metric / imperial
    private String weightCmGoal;
    private String waterGoal;
    private String stepGoal;
    private String fatPercentageGoal;

    // constructors
    public Option() {}

    public Option(String uid, String unit, String weightCmGoal, String waterGoal, String stepGoal, String fatPercentageGoal) {
        this.uid = uid;
        this.unit = unit;
        this.weightCmGoal = weightCmGoal;
        this.waterGoal = waterGoal;
        this.stepGoal = stepGoal;
        this.fatPercentageGoal = fatPercentageGoal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeightCmGoal() {
        return weightCmGoal;
    }

    public void setWeightCmGoal(String weightCmGoal) {
        this.weightCmGoal = weightCmGoal;
    }

    public String getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(String waterGoal) {
        this.waterGoal = waterGoal;
    }

    public String getStepGoal() {
        return stepGoal;
    }

    public void setStepGoal(String stepGoal) {
        this.stepGoal = stepGoal;
    }

    public String getFatPercentageGoal() {
        return fatPercentageGoal;
    }

    public void setFatPercentageGoal(String fatPercentageGoal) {
        this.fatPercentageGoal = fatPercentageGoal;
    }

    @Override
    public String toString() {
        return "Option{" +
                "uid='" + uid + '\'' +
                ", unit='" + unit + '\'' +
                ", weightCmGoal='" + weightCmGoal + '\'' +
                ", waterGoal='" + waterGoal + '\'' +
                ", stepGoal='" + stepGoal + '\'' +
                ", fatPercentageGoal='" + fatPercentageGoal + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"Option\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"unit\":\"" + unit + "\""
                + ", \"weightCmGoal\":\"" + weightCmGoal + "\""
                + ", \"waterGoal\":\"" + waterGoal + "\""
                + ", \"stepGoal\":\"" + stepGoal + "\""
                + ", \"fatPercentageGoal\":\"" + fatPercentageGoal + "\""
                + "}}}";
    }*/
}
