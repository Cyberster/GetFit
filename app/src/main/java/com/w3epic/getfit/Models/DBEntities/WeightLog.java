package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class WeightLog {
    private String uid;
    private String timestamp;
    private String weightInKg;

    public WeightLog() {}

    public WeightLog(String uid, String timestamp, String weightInKg) {
        this.uid = uid;
        this.timestamp = timestamp;
        this.weightInKg = weightInKg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(String weightInKg) {
        this.weightInKg = weightInKg;
    }

    @Override
    public String toString() {
        return "WeightLog{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", weightInKg='" + weightInKg + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"WeightLog\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + ", \"weightInKg\":\"" + weightInKg + "\""
                + "}}}";
    }*/
}
