package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class FoodLog {
    private String uid;
    private String foodResourceId; // resource_id
    private String name; // resource_id
    private String serving; // kcal
    private String quantity;
    private String unit;
    private String calories;
    private String thumbnail;
    private String timestamp; // in seconds 1531170918; use System.currentTimeMillis() / 1000

    public FoodLog() {}

    public FoodLog(String uid, String foodResourceId, String name, String serving, String quantity, String unit, String calories, String thumbnail, String timestamp) {
        this.uid = uid;
        this.foodResourceId = foodResourceId;
        this.name = name;
        this.serving = serving;
        this.quantity = quantity;
        this.unit = unit;
        this.calories = calories;
        this.thumbnail = thumbnail;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFoodResourceId() {
        return foodResourceId;
    }

    public void setFoodResourceId(String foodResourceId) {
        this.foodResourceId = foodResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "FoodLog{" +
                "uid='" + uid + '\'' +
                ", foodResourceId='" + foodResourceId + '\'' +
                ", name='" + name + '\'' +
                ", serving='" + serving + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unit='" + unit + '\'' +
                ", calories='" + calories + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    //@Override
    public String toJsonString() {
        return "{\"FoodLog\":{\"" +
                uid + "\":{"
                + ", \"foodResourceId\":\"" + foodResourceId + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"serving\":\"" + serving + "\""
                + ", \"quantity\":\"" + quantity + "\""
                + ", \"unit\":\"" + unit + "\""
                + ", \"calories\":\"" + calories + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + "}}}";
    }
}
