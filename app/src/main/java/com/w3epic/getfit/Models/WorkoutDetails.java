package com.w3epic.getfit.Models;

import android.util.Log;

import com.w3epic.getfit.APIDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by anonymouse on 7/6/18.
 */

public class WorkoutDetails {
    private String tagId;
    private String name;
    private String durationMin;
    private String nfCalories;
    private String met;
    private String photoFull;
    private String photoThumb;

    public WorkoutDetails() {}

    public WorkoutDetails(String query, String gender, String weight_kg, String height_cm, String age) {
        // get resource_id of food
        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;

        final String BASE_URL = "https://trackapi.nutritionix.com/v2/natural/exercise/";
        final String QUERY = "query";
        final String GENDER = "gender";
        final String WEIGHT_KG = "weight_kg";
        final String HEIGHT_CM = "height_cm";
        final String AGE = "age";
        final String APPID = "x-app-id";
        final String APPKEY = "x-app-key";

        try {
            JSONObject jsonRequestData = new JSONObject();
            try {
                jsonRequestData.put(QUERY, query);
                jsonRequestData.put(GENDER, gender);
                jsonRequestData.put(WEIGHT_KG, weight_kg);
                jsonRequestData.put(HEIGHT_CM, height_cm);
                jsonRequestData.put(AGE, age);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("HomeActivity", "json request: " + jsonRequestData.toString());

            String urlParameters  = QUERY + "=" + query + "&" +
                                    GENDER + "=" + gender + "&" +
                                    WEIGHT_KG + "=" + weight_kg + "&" +
                                    HEIGHT_CM + "=" + height_cm + "&" +
                                    AGE + "=" + age;
            byte[] postData = urlParameters.getBytes( "UTF-8" );
            URL url = new URL( BASE_URL );

            urlConnection = (HttpsURLConnection) url.openConnection();
//                    OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
//                    wr.write(jsonObject.toString());


            //urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty(APPID, APIDetails.APP_ID);
            urlConnection.setRequestProperty(APPKEY, APIDetails.APP_KEY);
            //Log.d("HomeActivity", urlConnection.toString());

            // For POST only - START
            urlConnection.setDoOutput(true);
            OutputStream os = urlConnection.getOutputStream();
            os.write(jsonRequestData.toString().getBytes("UTF-8"));
            os.flush();
            os.close();
            // For POST only - END

            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            //Log.d("HomeActivity", "Line1: " + bufferedReader.readLine());

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            //if ( inputStream == null) return  null;

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) !=null){
                buffer.append(line + "\n");
            }

            //if (buffer.length() == 0) return null;
            String jsonStr = buffer.toString();
            //Log.d("HomeActivity", "json response: " + jsonStr);

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONObject exercises = jsonObject.getJSONArray("exercises").getJSONObject(0);
                tagId = exercises.getString("tag_id");
                name = exercises.getString("name");
                durationMin = exercises.getString("duration_min");
                nfCalories = exercises.getString("nf_calories");
                met = exercises.getString("met");
                photoFull = exercises.getJSONObject("photo").getString("highres");
                photoThumb = exercises.getJSONObject("photo").getString("thumb");

                //Log.d("HomeActivity", "WorkoutDetails: " + this.toString());
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

    public static WorkoutDetails getWorkoutDetailsByName(String workoutName) {
        String query        = workoutName;
        String gender       = "male"; // todo: need to make gender, weight, height, age dynamic
        String weight_kg    = "59";
        String height_cm    = "177";
        String age          = "27";

        return new WorkoutDetails(query, gender, weight_kg, height_cm, age);
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(String durationMin) {
        this.durationMin = durationMin;
    }

    public String getNfCalories() {
        return nfCalories;
    }

    public void setNfCalories(String nfCalories) {
        this.nfCalories = nfCalories;
    }

    public String getMet() {
        return met;
    }

    public void setMet(String met) {
        this.met = met;
    }

    public String getPhotoFull() {
        return photoFull;
    }

    public void setPhotoFull(String photoFull) {
        this.photoFull = photoFull;
    }

    public String getPhotoThumb() {
        return photoThumb;
    }

    public void setPhotoThumb(String photoThumb) {
        this.photoThumb = photoThumb;
    }

    @Override
    public String toString() {
        return "WorkoutDetails{" +
                "tagId='" + tagId + '\'' +
                ", name='" + name + '\'' +
                ", durationMin='" + durationMin + '\'' +
                ", nfCalories='" + nfCalories + '\'' +
                ", met='" + met + '\'' +
                ", photoFull='" + photoFull + '\'' +
                ", photoThumb='" + photoThumb + '\'' +
                '}';
    }
}
