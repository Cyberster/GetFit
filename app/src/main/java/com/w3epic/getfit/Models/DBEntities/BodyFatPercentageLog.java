package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class BodyFatPercentageLog {
    private String uid;
    private String timestamp;
    private String bodyFatPercentage;

    public BodyFatPercentageLog() {}

    public BodyFatPercentageLog(String uid, String timestamp, String bodyFatPercentage) {
        this.uid = uid;
        this.timestamp = timestamp;
        this.bodyFatPercentage = bodyFatPercentage;
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

    public String getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(String bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    @Override
    public String toString() {
        return "BodyFatPercentageLog{" +
                "uid='" + uid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", bodyFatPercentage='" + bodyFatPercentage + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"BodyFatPercentageLog\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + ", \"bodyFatPercentage\":\"" + bodyFatPercentage + "\""
                + "}}}";
    }*/

    /*
    {
//  "BodyFatPercentageLog": {
//    "kIIaix5UnbcWrmcUiMmB1RwkRpe2": {
//      "timestamp": "2018-07-09 18:51:00.529",
//      "bodyFatPercentage": "10"
//    }
//  }
//}
    public String test() {
        // "{\"BodyFatPercentageLog\" : {\"" + uid + "\": {" + "\"timestamp\": \"" + timestamp + "\"," + "\"bodyFatPercentage\": \"" + bodyFatPercentage + "\"}}}";
        return
                "{\"BodyFatPercentageLog\":{\"" +
                uid + "\": {" + "\"timestamp\": \"" + timestamp + "\"," + "\"bodyFatPercentage\": \"" + bodyFatPercentage + "\"}}}";}
    }*/
}
