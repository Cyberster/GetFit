package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class WaterLog {
    private String uid;
    private String timestamp;
    private String glass;

    public WaterLog() {}

    public WaterLog(String uid, String timestamp, String glass) {
        this.uid = uid;
        this.timestamp = timestamp;
        this.glass = glass;
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

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    @Override
    public String toString() {
        return "WaterLog{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", glass='" + glass + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"WaterLog\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + ", \"glass\":\"" + glass + "\""
                + "}}}";
    }*/
}
