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

public class FoodItem {
    private String resourceId;
    private String itemName;
    private String thumbnail;
    private String nutrientName; // calories/carb/protein/fat
    private String nutrientValue;
    private String nutrientUom; // unit of measure - kcal/g

    public FoodItem() {}

    public FoodItem(String resourceId, String itemName, String thumbnail, String nutrientName, String nutrientValue, String nutrientUom) {
        this.resourceId = resourceId;
        this.itemName = itemName;
        this.thumbnail = thumbnail;
        this.nutrientName = nutrientName;
        this.nutrientValue = nutrientValue;
        this.nutrientUom = nutrientUom;
    }

    public FoodItem(final String foodItemName) {
        // get resource_id of food
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "https://apibeta.nutritionix.com/v2/search";
        final String Q = "q";
        final String LIMIT = "limit";
        final String OFFSET = "offset";
        final String SEARCH_TYPE = "search_type";
        final String APPID = "appId";
        final String APPKEY = "appKey";
        final String SEARCH_NUTRIENT = "search_nutrient";
        final String SEARCHTYPE = "searchType";

        try {
            Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(Q, foodItemName)
                    .appendQueryParameter(LIMIT, "1")
                    .appendQueryParameter(OFFSET, "0")
                    .appendQueryParameter(SEARCH_TYPE, "grocery")
                    .appendQueryParameter(APPID, APIDetails.APP_ID)
                    .appendQueryParameter(APPKEY, APIDetails.APP_KEY)
                    .appendQueryParameter(SEARCH_NUTRIENT, "calories")
                    .appendQueryParameter(SEARCHTYPE, "recipe")
                    .build();

            URL url = new URL(buildUri.toString());
            Log.d("FoodItem", buildUri.toString());
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            //if ( inputStream == null) return  null;
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) !=null){
                buffer.append(line + "\n");
            }

            //if (buffer.length() == 0)return null;
            String jsonStr = buffer.toString();
            Log.d("HomeActivity", jsonStr);

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray elements = jsonObject.getJSONArray("results");
                JSONObject resource = elements.getJSONObject(0);

                resourceId = resource.getString("resource_id");
                itemName = resource.getString("item_name");
                thumbnail = resource.getString("thumbnail");
                nutrientName = resource.getString("nutrient_name");
                nutrientValue = resource.getString("nutrient_value");
                nutrientUom = resource.getString("nutrient_uom");

                Log.d("HomeActivity", "food_item: " + this.toString());
            } catch (JSONException e) {
                Log.d("HomeActivity", e.getMessage());
            }
        }catch(IOException e){
            e.printStackTrace();
            //return  null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getNutrientValue() {
        return nutrientValue;
    }

    public void setNutrientValue(String nutrientValue) {
        this.nutrientValue = nutrientValue;
    }

    public String getNutrientUom() {
        return nutrientUom;
    }

    public void setNutrientUom(String nutrientUom) {
        this.nutrientUom = nutrientUom;
    }

    @Override
    public String toString() {
        return "FoodItem{" +
                "resourceId='" + resourceId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", nutrientName='" + nutrientName + '\'' +
                ", nutrientValue='" + nutrientValue + '\'' +
                ", nutrientUom='" + nutrientUom + '\'' +
                '}';
    }

    // fetch food item list by name
    //@SuppressLint("StaticFieldLeak")
    public static FoodItem[] jsonFetchFoodItemList(final String foodItemName, final int limit) {
        final FoodItem[] foodItems = new FoodItem[limit];

        /*class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {}

            @Override
            protected String doInBackground(String... strings) {*/
                // get resource_id of food
                HttpsURLConnection urlConnection = null;
                BufferedReader reader = null;

                final String BASE_URL = "https://apibeta.nutritionix.com/v2/search";
                final String Q = "q";
                final String LIMIT = "limit";
                final String OFFSET = "offset";
                final String SEARCH_TYPE = "search_type";
                final String APPID = "appId";
                final String APPKEY = "appKey";
                final String SEARCH_NUTRIENT = "search_nutrient";
                final String SEARCHTYPE = "searchType";

                try {
                    Uri buildUri = Uri.parse(BASE_URL).buildUpon()
                            .appendQueryParameter(Q, foodItemName)
                            .appendQueryParameter(LIMIT, String.valueOf(limit))
                            .appendQueryParameter(OFFSET, "0")
                            .appendQueryParameter(SEARCH_TYPE, "grocery")
                            .appendQueryParameter(APPID, APIDetails.APP_ID)
                            .appendQueryParameter(APPKEY, APIDetails.APP_KEY)
                            .appendQueryParameter(SEARCH_NUTRIENT, "calories")
                            .appendQueryParameter(SEARCHTYPE, "recipe")
                            .build();

                    URL url = new URL(buildUri.toString());
                    Log.d("FoodItem", buildUri.toString());
                    urlConnection = (HttpsURLConnection) url.openConnection();

                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();

                    if (inputStream == null)
                        return null;
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) return null;
                    String jsonStr = buffer.toString();
                    Log.d("HomeActivity", jsonStr);

                    // iterating through results
                    for (int i = 0; i < limit; i++) {
                        try {
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            JSONArray elements = jsonObject.getJSONArray("results");
                            JSONObject resource = elements.getJSONObject(i);

                            FoodItem foodItem = new FoodItem();
                            foodItem.setResourceId(resource.getString("resource_id"));
                            foodItem.setItemName(resource.getString("item_name"));
                            foodItem.setThumbnail(resource.getString("thumbnail"));
                            foodItem.setNutrientName(resource.getString("nutrient_name"));
                            foodItem.setNutrientValue(resource.getString("nutrient_value"));
                            foodItem.setNutrientUom(resource.getString("nutrient_uom"));

                            foodItems[i] = foodItem;

                            //Log.d("HomeActivity", "food_item: " + foodItem.toString());
                        } catch (JSONException e) {
                            Log.d("HomeActivity", e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

               /* return null;
            }
        }

        new Multitask().execute();*/

        return foodItems;
    }
}
