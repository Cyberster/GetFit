package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class WorkoutLog {
    private String uid;
    private String wid;
    private String name;
    private String met;
    private String workoutResourceId; // resource_id
    private String durationInMin;
    private String set;
    private String repetation;
    private String unit;
    private String timestamp;
    private String kcalBurnt;

    public WorkoutLog() {}

    public WorkoutLog(String uid, String wid, String name, String met, String workoutResourceId, String durationInMin, String set, String repetation, String unit, String timestamp, String kcalBurnt) {
        this.uid = uid;
        this.wid = wid;
        this.name = name;
        this.met = met;
        this.workoutResourceId = workoutResourceId;
        this.durationInMin = durationInMin;
        this.set = set;
        this.repetation = repetation;
        this.unit = unit;
        this.timestamp = timestamp;
        this.kcalBurnt = kcalBurnt;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMet() {
        return met;
    }

    public void setMet(String met) {
        this.met = met;
    }

    public String getWorkoutResourceId() {
        return workoutResourceId;
    }

    public void setWorkoutResourceId(String workoutResourceId) {
        this.workoutResourceId = workoutResourceId;
    }

    public String getDurationInMin() {
        return durationInMin;
    }

    public void setDurationInMin(String durationInMin) {
        this.durationInMin = durationInMin;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getRepetation() {
        return repetation;
    }

    public void setRepetation(String repetation) {
        this.repetation = repetation;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getKcalBurnt() {
        return kcalBurnt;
    }

    public void setKcalBurnt(String kcalBurnt) {
        this.kcalBurnt = kcalBurnt;
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "uid='" + uid + '\'' +
                ", wid='" + wid + '\'' +
                ", name='" + name + '\'' +
                ", met='" + met + '\'' +
                ", workoutResourceId='" + workoutResourceId + '\'' +
                ", durationInMin='" + durationInMin + '\'' +
                ", set='" + set + '\'' +
                ", repetation='" + repetation + '\'' +
                ", unit='" + unit + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", kcalBurnt='" + kcalBurnt + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"WorkoutLog\":{\"" +
                uid + "\":{"
                + "\"uid\":\"" + uid + "\""
                + ", \"wid\":\"" + wid + "\""
                + ", \"workoutResourceId\":\"" + workoutResourceId + "\""
                + ", \"durationInMin\":\"" + durationInMin + "\""
                + ", \"set\":\"" + set + "\""
                + ", \"repetation\":\"" + repetation + "\""
                + ", \"timestamp\":\"" + timestamp + "\""
                + "}}}";
    }*/
}
