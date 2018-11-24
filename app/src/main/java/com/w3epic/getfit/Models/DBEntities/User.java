package com.w3epic.getfit.Models.DBEntities;

/**
 * Created by anonymouse on 7/5/18.
 */

public class User {

    //private String username;
    //private String password;
    private String phone;
    private String name;
    private String city;
    private String age;
    private String height_cm;
    private String weight_kg;

    // constructors
    public User() {
        this.phone      = "";
        this.name       = "";
        this.city       = "";
        this.age        = "27";
        this.height_cm  = "177";
        this.weight_kg  = "59";
    }

    public User(String phone, String name, String city, String age, String height_cm, String weight_kg) {
        this.phone      = phone;
        this.name       = name;
        this.city       = city;
        this.age        = age;
        this.height_cm  = height_cm;
        this.weight_kg  = weight_kg;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight_cm() {
        return height_cm;
    }

    public void setHeight_cm(String height_cm) {
        this.height_cm = height_cm;
    }

    public String getWeight_kg() {
        return weight_kg;
    }

    public void setWeight_kg(String weight_kg) {
        this.weight_kg = weight_kg;
    }

    @Override
    public String toString() {
        return "User{" +
                "phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", age='" + age + '\'' +
                ", height_cm='" + height_cm + '\'' +
                ", weight_kg='" + weight_kg + '\'' +
                '}';
    }

    /*@Override
    public String toString() {
        return "{\"User\":{\"" +
                phone + "\":{"
                + "\"phone\":\"" + phone + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"city\":\"" + city + "\""
                + ", \"age\":\"" + age + "\""
                + ", \"height_cm\":\"" + height_cm + "\""
                + ", \"weight_kg\":\"" + weight_kg + "\""
                + "}}}";
    }*/
}