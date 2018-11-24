package com.w3epic.getfit.Models;

/**
 * Created by anonymouse on 7/7/18.
 */
public class NutrientInfo {
    private Nutrient calories;            // index: 4
    private Nutrient protein;             // index: 0
    private Nutrient totalCarbohydrate;   // index: 2
    private Nutrient dietaryFiber;        // index: 18
    private Nutrient sugar;               // index: 16
    private Nutrient totalFat;            // index: 1
    private Nutrient saturatedFat;        // index: 35
    private Nutrient polyunsaturatedFat;  // index: 37
    private Nutrient monounsaturatedFat;  // index: 36
    private Nutrient transFat;            // index: 34
    private Nutrient cholesterol;         // index: 33
    private Nutrient sodium;              // index: 24
    private Nutrient potassium;           // index: 23
    private Nutrient calcium;             // index: 19
    private Nutrient iron;                // index: 20
    private Nutrient magnesium;           // index: 21
    private Nutrient zinc;                // index: 25
    private Nutrient selenium;            // index: 29
    private Nutrient vitaminA;            // index: 30
    private Nutrient vitaminC;            // index: 32

    public NutrientInfo() {}

    public NutrientInfo(Nutrient calories, Nutrient protein, Nutrient totalCarbohydrate, Nutrient dietaryFiber, Nutrient sugar, Nutrient totalFat, Nutrient saturatedFat, Nutrient polyunsaturatedFat, Nutrient monounsaturatedFat, Nutrient trnsFat, Nutrient cholesterol, Nutrient sodium, Nutrient potassium, Nutrient calcium, Nutrient iron, Nutrient magnesium, Nutrient zinc, Nutrient selenium, Nutrient vitaminA, Nutrient vitaminC) {
        this.calories = calories;
        this.protein = protein;
        this.totalCarbohydrate = totalCarbohydrate;
        this.dietaryFiber = dietaryFiber;
        this.sugar = sugar;
        this.totalFat = totalFat;
        this.saturatedFat = saturatedFat;
        this.polyunsaturatedFat = polyunsaturatedFat;
        this.monounsaturatedFat = monounsaturatedFat;
        this.transFat = transFat;
        this.cholesterol = cholesterol;
        this.sodium = sodium;
        this.potassium = potassium;
        this.calcium = calcium;
        this.iron = iron;
        this.magnesium = magnesium;
        this.zinc = zinc;
        this.selenium = selenium;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
    }

    public Nutrient getCalories() {
        return calories;
    }

    public void setCalories(Nutrient calories) {
        this.calories = calories;
    }

    public Nutrient getProtein() {
        return protein;
    }

    public void setProtein(Nutrient protein) {
        this.protein = protein;
    }

    public Nutrient getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(Nutrient totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public Nutrient getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(Nutrient dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public Nutrient getSugar() {
        return sugar;
    }

    public void setSugar(Nutrient sugar) {
        this.sugar = sugar;
    }

    public Nutrient getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(Nutrient totalFat) {
        this.totalFat = totalFat;
    }

    public Nutrient getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Nutrient saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Nutrient getPolyunsaturatedFat() {
        return polyunsaturatedFat;
    }

    public void setPolyunsaturatedFat(Nutrient polyunsaturatedFat) {
        this.polyunsaturatedFat = polyunsaturatedFat;
    }

    public Nutrient getMonounsaturatedFat() {
        return monounsaturatedFat;
    }

    public void setMonounsaturatedFat(Nutrient monounsaturatedFat) {
        this.monounsaturatedFat = monounsaturatedFat;
    }

    public Nutrient getTransFat() {
        return transFat;
    }

    public void setTransFat(Nutrient transFat) {
        this.transFat = transFat;
    }

    public Nutrient getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Nutrient cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Nutrient getSodium() {
        return sodium;
    }

    public void setSodium(Nutrient sodium) {
        this.sodium = sodium;
    }

    public Nutrient getPotassium() {
        return potassium;
    }

    public void setPotassium(Nutrient potassium) {
        this.potassium = potassium;
    }

    public Nutrient getCalcium() {
        return calcium;
    }

    public void setCalcium(Nutrient calcium) {
        this.calcium = calcium;
    }

    public Nutrient getIron() {
        return iron;
    }

    public void setIron(Nutrient iron) {
        this.iron = iron;
    }

    public Nutrient getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(Nutrient magnesium) {
        this.magnesium = magnesium;
    }

    public Nutrient getZinc() {
        return zinc;
    }

    public void setZinc(Nutrient zinc) {
        this.zinc = zinc;
    }

    public Nutrient getSelenium() {
        return selenium;
    }

    public void setSelenium(Nutrient selenium) {
        this.selenium = selenium;
    }

    public Nutrient getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(Nutrient vitaminA) {
        this.vitaminA = vitaminA;
    }

    public Nutrient getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(Nutrient vitaminC) {
        this.vitaminC = vitaminC;
    }

    @Override
    public String toString() {
        return "NutrientInfo{" +
                "calories=" + calories +
                ", protein=" + protein +
                ", totalCarbohydrate=" + totalCarbohydrate +
                ", dietaryFiber=" + dietaryFiber +
                ", sugar=" + sugar +
                ", totalFat=" + totalFat +
                ", saturatedFat=" + saturatedFat +
                ", polyunsaturatedFat=" + polyunsaturatedFat +
                ", monounsaturatedFat=" + monounsaturatedFat +
                ", transFat=" + transFat +
                ", cholesterol=" + cholesterol +
                ", sodium=" + sodium +
                ", potassium=" + potassium +
                ", calcium=" + calcium +
                ", iron=" + iron +
                ", magnesium=" + magnesium +
                ", zinc=" + zinc +
                ", selenium=" + selenium +
                ", vitaminA=" + vitaminA +
                ", vitaminC=" + vitaminC +
                '}';
    }
}
