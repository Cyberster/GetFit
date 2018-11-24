package com.w3epic.getfit.Models;

/**
 * Created by anonymouse on 7/7/18.
 */
public class Nutrient {
    private String name;    // protein
    private String value;   // 3.5
    private String unit;    // g/ml

    public Nutrient() {
        name = "";
        value = "";
        unit = "";
    }

    public Nutrient(String name, String value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value.equals("null") ? "0" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
