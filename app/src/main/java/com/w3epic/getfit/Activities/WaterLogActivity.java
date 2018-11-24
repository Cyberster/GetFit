package com.w3epic.getfit.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.w3epic.getfit.Models.FoodItem;
import com.w3epic.getfit.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WaterLogActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    FoodItem[] foodItems;
    TextView tvWaterLogState1;
    TextView tvWaterLogState2;

    int glass_today = 0;
    int glass_goal = 0;

    public static final String PREF_FILE_KEY = "waterlog_";
    public static final String WORD = "glass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_log);
        setTitle("Water log");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + date, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        tvWaterLogState1 = findViewById(R.id.tvWaterLogState1);
        tvWaterLogState2 = findViewById(R.id.tvWaterLogState2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWaterLogGUI();
    }

    public void btnAddFoodOnClickHandler(View view) {
        startActivity(new Intent(WaterLogActivity.this, SearchFoodActivity.class));
    }

    public void btnAddWaterOnClickHandler(View view) {
        if (sharedPreferences.contains(WORD)) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            glass_today = Integer.parseInt(sharedPreferences.getString(WORD, "0"));

            if (glass_today < 8) glass_today++;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(WORD, String.valueOf(glass_today));
            editor.commit(); // or use apply()

            glass_today = Integer.parseInt(sharedPreferences.getString(WORD, "0"));
        }
        updateWaterLogGUI();
    }

    public void btnDelWaterOnClickHandler(View view) {
        if (sharedPreferences.contains(WORD)) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            glass_today = Integer.parseInt(sharedPreferences.getString(WORD, "0"));

            if (glass_today > 0) glass_today--;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(WORD, String.valueOf(glass_today));
            editor.commit(); // or use apply()

            glass_today = Integer.parseInt(sharedPreferences.getString(WORD, "0"));
        }
        updateWaterLogGUI();
    }

    private void updateWaterLogGUI() {
        String word = getString(R.string.pref_water_goal_key);
        String DEFAULT_WATER_GOAL = defaultSharedPreferences.getString(word, "0");

        String text1;
        String text2;

        File f = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + getPackageName() + "_preferences.xml");
        if (!f.exists()) DEFAULT_WATER_GOAL = getString(R.string.pref_water_goal_default);

        if (sharedPreferences.contains(WORD)) {
            glass_today = Integer.parseInt(sharedPreferences.getString(WORD, "0"));
        }

        if (defaultSharedPreferences.contains(word)) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            glass_goal = Integer.parseInt(defaultSharedPreferences.getString(word, "0"));

            text1 = glass_today  + " out of " + glass_goal + " glass today";
            text2 = (glass_goal-glass_today)  + " glass left!";
        } else {
            glass_goal = Integer.parseInt(DEFAULT_WATER_GOAL);

            text1 = glass_today  + " out of " + glass_goal + " glass today";
            text2 = (glass_goal-glass_today)  + " glass left!";
        }

        tvWaterLogState1.setText(text1);
        tvWaterLogState2.setText(text2);
    }
}