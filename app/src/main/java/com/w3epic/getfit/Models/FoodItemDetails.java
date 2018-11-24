package com.w3epic.getfit.Models;

import android.net.Uri;
import android.util.Log;

import com.w3epic.getfit.APIDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by anonymouse on 7/6/18.
 */

public class FoodItemDetails {
    private String resourceId;
    private String name;
    private String imageFull;
    private String imageThumb;
    private String quantity;
    private String unitOfMeasure;
    private NutrientInfo nutrientInfo = new NutrientInfo();

    public FoodItemDetails() {
        resourceId = "0";
        name = "0";
        imageFull = "0";
        imageThumb = "0";
        quantity = "0";
        unitOfMeasure = "0";
        NutrientInfo nutrientInfo = new NutrientInfo();
    }

    public FoodItemDetails(String resourceId, String name, String image_full, String image_thumb, String quantity, String unitOfMeasure, NutrientInfo nutrientInfo) {
        this.resourceId = resourceId;
        this.name = name;
        this.imageFull = image_full;
        this.imageThumb = image_thumb;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.nutrientInfo = nutrientInfo;
    }

    public FoodItemDetails(String foodResourceId) {
        //final String resource_id = foodResourceId;
        Log.d("HomeActivity", "foodResourceId: " + foodResourceId);

        // get resource_id of food
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "https://apibeta.nutritionix.com/v2/item/";
        final String APPID = "appId";
        final String APPKEY = "appKey";

        try {
            Uri buildUri = Uri.parse(BASE_URL + "/" + foodResourceId).buildUpon()
                    .appendQueryParameter(APPID, APIDetails.APP_ID)
                    .appendQueryParameter(APPKEY, APIDetails.APP_KEY)
                    .build();

            URL url = new URL(buildUri.toString());
            Log.d("HomeActivity", buildUri.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            //if ( inputStream == null) return  null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            //if (buffer.length() == 0) return null;
            String jsonStr = buffer.toString();
            Log.d("HomeActivity", jsonStr);

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                // get name
                resourceId = foodResourceId;
                name = jsonObject.getString("name");
                imageFull = jsonObject.getJSONObject("images").getJSONObject("front").getString("full");
                imageThumb = jsonObject.getJSONObject("images").getJSONObject("front").getString("thumb");
                quantity = jsonObject.getJSONObject("label").getJSONObject("serving").getJSONObject("metric").getString("qty");
                unitOfMeasure = jsonObject.getJSONObject("label").getJSONObject("serving").getJSONObject("metric").getString("uom");

                JSONArray nutrients = jsonObject.getJSONObject("label").getJSONArray("nutrients");

                // fetch nutrients
                int index;

                // fetch calories
                Nutrient calories = new Nutrient();
                index = 4;
                calories.setName(nutrients.getJSONObject(index).getString("name"));
                calories.setValue(nutrients.getJSONObject(index).getString("value"));
                calories.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setCalories(calories);

                // fetch protein
                Nutrient protein = new Nutrient();
                index = 0;
                protein.setName(nutrients.getJSONObject(index).getString("name"));
                protein.setValue(nutrients.getJSONObject(index).getString("value"));
                protein.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setProtein(protein);

                // fetch total carbohydrate
                Nutrient totalCarbohydrate = new Nutrient();
                index = 2;
                totalCarbohydrate.setName(nutrients.getJSONObject(index).getString("name"));
                totalCarbohydrate.setValue(nutrients.getJSONObject(index).getString("value"));
                totalCarbohydrate.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setTotalCarbohydrate(totalCarbohydrate);

                // fetch dietary fiber
                Nutrient dietaryFiber = new Nutrient();
                index = 18;
                dietaryFiber.setName(nutrients.getJSONObject(index).getString("name"));
                dietaryFiber.setValue(nutrients.getJSONObject(index).getString("value"));
                dietaryFiber.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setDietaryFiber(dietaryFiber);

                // fetch sugar
                Nutrient sugar = new Nutrient();
                index = 16;
                sugar.setName(nutrients.getJSONObject(index).getString("name"));
                sugar.setValue(nutrients.getJSONObject(index).getString("value"));
                sugar.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setSugar(sugar);

                // fetch total fat
                Nutrient totalFat = new Nutrient();
                index = 1;
                totalFat.setName(nutrients.getJSONObject(index).getString("name"));
                totalFat.setValue(nutrients.getJSONObject(index).getString("value"));
                totalFat.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setTotalFat(totalFat);

                // fetch saturated fat
                Nutrient saturatedFat = new Nutrient();
                index = 35;
                saturatedFat.setName(nutrients.getJSONObject(index).getString("name"));
                saturatedFat.setValue(nutrients.getJSONObject(index).getString("value"));
                saturatedFat.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setSaturatedFat(saturatedFat);

                // fetch polyunsaturated fat
                Nutrient polyunsaturatedFat = new Nutrient();
                index = 37;
                polyunsaturatedFat.setName(nutrients.getJSONObject(index).getString("name"));
                polyunsaturatedFat.setValue(nutrients.getJSONObject(index).getString("value"));
                polyunsaturatedFat.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setPolyunsaturatedFat(polyunsaturatedFat);

                // fetch monounsaturated fat
                Nutrient monounsaturatedFat = new Nutrient();
                index = 36;
                monounsaturatedFat.setName(nutrients.getJSONObject(index).getString("name"));
                monounsaturatedFat.setValue(nutrients.getJSONObject(index).getString("value"));
                monounsaturatedFat.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setMonounsaturatedFat(monounsaturatedFat);

                // fetch trans fat
                Nutrient transFat = new Nutrient();
                index = 34;
                transFat.setName(nutrients.getJSONObject(index).getString("name"));
                transFat.setValue(nutrients.getJSONObject(index).getString("value"));
                transFat.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setTransFat(transFat);

                // fetch cholesterol
                Nutrient cholesterol = new Nutrient();
                index = 33;
                cholesterol.setName(nutrients.getJSONObject(index).getString("name"));
                cholesterol.setValue(nutrients.getJSONObject(index).getString("value"));
                cholesterol.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setCholesterol(cholesterol);

                // fetch sodium
                Nutrient sodium = new Nutrient();
                index = 24;
                sodium.setName(nutrients.getJSONObject(index).getString("name"));
                sodium.setValue(nutrients.getJSONObject(index).getString("value"));
                sodium.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setSodium(sodium);

                // fetch potassium
                Nutrient potassium = new Nutrient();
                index = 23;
                potassium.setName(nutrients.getJSONObject(index).getString("name"));
                potassium.setValue(nutrients.getJSONObject(index).getString("value"));
                potassium.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setPotassium(potassium);

                // fetch calcium
                Nutrient calcium = new Nutrient();
                index = 19;
                calcium.setName(nutrients.getJSONObject(index).getString("name"));
                calcium.setValue(nutrients.getJSONObject(index).getString("value"));
                calcium.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setCalcium(calcium);

                // fetch iron
                Nutrient iron = new Nutrient();
                index = 20;
                iron.setName(nutrients.getJSONObject(index).getString("name"));
                iron.setValue(nutrients.getJSONObject(index).getString("value"));
                iron.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setIron(iron);

                // fetch magnesium
                Nutrient magnesium = new Nutrient();
                index = 21;
                magnesium.setName(nutrients.getJSONObject(index).getString("name"));
                magnesium.setValue(nutrients.getJSONObject(index).getString("value"));
                magnesium.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setMagnesium(magnesium);

                // fetch zinc
                Nutrient zinc = new Nutrient();
                index = 25;
                zinc.setName(nutrients.getJSONObject(index).getString("name"));
                zinc.setValue(nutrients.getJSONObject(index).getString("value"));
                zinc.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setZinc(zinc);

                // fetch selenium
                Nutrient selenium = new Nutrient();
                index = 29;
                selenium.setName(nutrients.getJSONObject(index).getString("name"));
                selenium.setValue(nutrients.getJSONObject(index).getString("value"));
                selenium.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setSelenium(selenium);

                // fetch vitamin-A
                Nutrient vitaminA = new Nutrient();
                index = 30;
                vitaminA.setName(nutrients.getJSONObject(index).getString("name"));
                vitaminA.setValue(nutrients.getJSONObject(index).getString("value"));
                vitaminA.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setVitaminA(vitaminA);

                // fetch vitamin-C
                Nutrient vitaminC = new Nutrient();
                index = 32;
                vitaminC.setName(nutrients.getJSONObject(index).getString("name"));
                vitaminC.setValue(nutrients.getJSONObject(index).getString("value"));
                vitaminC.setUnit(nutrients.getJSONObject(index).getString("unit"));
                nutrientInfo.setVitaminC(vitaminC);
            } catch (JSONException e) {
                Log.d("HomeActivity", e.getMessage());
            }
        } catch (IOException e){
            e.printStackTrace();
            //return  null;
        } catch (Exception e) {
            //Log.d("HomeActivity", e.getMessage());
            e.printStackTrace();
        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage_full() {
        return imageFull;
    }

    public void setImage_full(String image_full) {
        this.imageFull = image_full;
    }

    public String getImage_thumb() {
        return imageThumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.imageThumb = image_thumb;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public NutrientInfo getNutrientInfo() {
        return nutrientInfo;
    }

    public void setNutrientInfo(NutrientInfo nutrientInfo) {
        this.nutrientInfo = nutrientInfo;
    }

    @Override
    public String toString() {
        return "FoodItemDetails{" +
                "resourceId='" + resourceId + '\'' +
                ", name='" + name + '\'' +
                ", image_full='" + imageFull + '\'' +
                ", image_thumb='" + imageThumb + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitOfMeasure='" + unitOfMeasure + '\'' +
                ", nutrientInfo=" + nutrientInfo.toString() +
                '}';
    }
}

