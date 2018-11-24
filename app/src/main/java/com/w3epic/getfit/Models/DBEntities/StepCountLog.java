package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class StepCountLog {
    private String uid;
    private String timestamp;
    private String stepCount;

    public StepCountLog() {}

    public StepCountLog(String uid, String timestamp, String stepCount) {
        this.uid = uid;
        this.timestamp = timestamp;
        this.stepCount = stepCount;
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

    public String getStepCount() {
        return stepCount;
    }

    public void setStepCount(String stepCount) {
        this.stepCount = stepCount;
    }

    @Override
    public String toString() {
        return "StepCountLog{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", stepCount='" + stepCount + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"StepCountLog\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + ", \"stepCount\":\"" + stepCount + "\""
                + "}}}";
    }*/
}
