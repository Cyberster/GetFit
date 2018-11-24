package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/6/18.
 */

public class Workout {
    private String id;
    private String name;
    private String unitType; // rep/min
    private String videoLink;

    public Workout() {}

    public Workout(String id, String name, String unitType, String videoLink) {
        this.id = id;
        this.name = name;
        this.unitType = unitType;
        this.videoLink = videoLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitType='" + unitType + '\'' +
                ", videoLink='" + videoLink + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"Workout\":{\"" +
                id + "\":{"
                + "\"id\":\"" + id + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"unitType\":\"" + unitType + "\""
                + ", \"videoLink\":\"" + videoLink + "\""
                + "}}}";
    }*/
}
