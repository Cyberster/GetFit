package com.w3epic.getfit.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.w3epic.getfit.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BodyFatPercentageLogActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    TextView tvBodyFatPercentageLogState;
    TextView tvTargetWeight;
    EditText etCurrentBodyFatPercentage;

    double bodyFatPercentage_today = 0;
    double bodyFatPercentage_goal = 0;

    public static final String PREF_FILE_KEY = "bodyFatPercentagelog_";
    public static final String WORD = "bodyFatPercentage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_fat_percentage_log);
        setTitle("Body fat percentage log");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String data = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + data, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        etCurrentBodyFatPercentage = findViewById(R.id.etCurrentBodyFatPercentage);
        tvBodyFatPercentageLogState = findViewById(R.id.tvBodyFatPercentageLogState);
        tvTargetWeight = findViewById(R.id.tvTargetWeight);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateBodyFatPercentageLogGUI();
    }

    public void btnDelBodyFatPercentageOnClickHandler(View view) {
        String weight = etCurrentBodyFatPercentage.getText().toString();
        Double weight_double = Double.parseDouble(weight);
        weight_double -= 0.25D;
        etCurrentBodyFatPercentage.setText(String.valueOf(weight_double));
    }

    public void btnAddBodyFatPercentageOnClickHandler(View view) {
        String weight = etCurrentBodyFatPercentage.getText().toString();
        Double weight_double = Double.parseDouble(weight);
        weight_double += 0.25D;
        etCurrentBodyFatPercentage.setText(String.valueOf(weight_double));
    }

    public void btnAddBodyFatPercentageToLogOnClickHandler(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WORD, etCurrentBodyFatPercentage.getText().toString());
        editor.commit(); // or use apply()
        updateBodyFatPercentageLogGUI();
    }

    public void btnCalculateBodyFatPercentageOnClickHandler(View view) {
        //        //https://stackoverflow.com/questions/36583719/intent-to-open-a-specific-tab-of-tabbed-activity
        int page = 3;
        Intent intent = new Intent(BodyFatPercentageLogActivity.this, HealthToolsActivity.class);
        intent.putExtra("tab_index", page);// One is your argument
        startActivity(intent);
    }

    private void updateBodyFatPercentageLogGUI() {
        String word = getString(R.string.pref_body_fat_percentage_goal_key);
        String DEFAULT_BODY_FAT = defaultSharedPreferences.getString(word, "0");

        File f = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + getPackageName() + "_preferences.xml");
        if (!f.exists()) DEFAULT_BODY_FAT = getString(R.string.pref_body_fat_percentage_goal_default);

        if (sharedPreferences.contains(WORD)) {
            bodyFatPercentage_today = Double.parseDouble(sharedPreferences.getString(WORD, DEFAULT_BODY_FAT));
        } else {
            bodyFatPercentage_today = Double.parseDouble(DEFAULT_BODY_FAT);
        }

        if (defaultSharedPreferences.contains(WORD)) {
            bodyFatPercentage_goal = Double.parseDouble(defaultSharedPreferences.getString(WORD, DEFAULT_BODY_FAT));
        } else {
            bodyFatPercentage_goal = Double.parseDouble(DEFAULT_BODY_FAT);
        }

        tvBodyFatPercentageLogState.setText(String.valueOf(bodyFatPercentage_today + " %"));
        etCurrentBodyFatPercentage.setText(String.valueOf(bodyFatPercentage_today));
        tvTargetWeight.setText(String.valueOf("Target body fat: " + bodyFatPercentage_goal + " %"));
    }
}
