package com.w3epic.getfit.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.w3epic.getfit.R;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightLogActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    TextView tvWeightLogState;
    TextView tvTargetWeight;
    EditText etCurrentWeight;

    double weight_today = 0;
    double weight_goal = 0;

    public static final String PREF_FILE_KEY = "weightlog_";
    public static final String WORD = "weight";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_log);
        setTitle("Weight log");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String data = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + data, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        tvWeightLogState = findViewById(R.id.tvWeightLogState);
        tvTargetWeight = findViewById(R.id.tvTargetWeight);
        etCurrentWeight = findViewById(R.id.etCurrentWeight);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateWeightLogGUI();
    }

    public void btnDelWeightOnClickHandler(View view) {
        String weight = etCurrentWeight.getText().toString();
        Double weight_double = Double.parseDouble(weight);
        weight_double -= 0.25D;
        etCurrentWeight.setText(String.valueOf(weight_double));
    }

    public void btnAddWeightOnClickHandler(View view) {
        String weight = etCurrentWeight.getText().toString();
        Double weight_double = Double.parseDouble(weight);
        weight_double += 0.25D;
        etCurrentWeight.setText(String.valueOf(weight_double));
    }

    public void btnAddWeightToLogOnClickHandler(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WORD, etCurrentWeight.getText().toString());
        editor.commit(); // or use apply()
        updateWeightLogGUI();
    }

    private void updateWeightLogGUI() {
        String word = getString(R.string.pref_weight_goal_key);
        String DEFAULT_WEIGHT_GOAL = defaultSharedPreferences.getString(word, "0");

        File f = new File("/data/data/" + getPackageName() +  "/shared_prefs/" + getPackageName() + "_preferences.xml");
        if (!f.exists()) DEFAULT_WEIGHT_GOAL = getString(R.string.pref_weight_goal_default);


        if (sharedPreferences.contains(WORD)) {
            weight_today = Double.parseDouble(sharedPreferences.getString(WORD, DEFAULT_WEIGHT_GOAL));
        } else {
            weight_today = Double.parseDouble(DEFAULT_WEIGHT_GOAL);
        }

        if (defaultSharedPreferences.contains(WORD)) {
            weight_goal = Double.parseDouble(defaultSharedPreferences.getString(WORD, DEFAULT_WEIGHT_GOAL));
        } else {
            weight_goal = Double.parseDouble(DEFAULT_WEIGHT_GOAL);
        }

        tvWeightLogState.setText(String.valueOf(weight_today + " kg"));
        etCurrentWeight.setText(String.valueOf(weight_today));
        tvTargetWeight.setText(String.valueOf("Target weight: " + weight_goal + " kg"));
    }
}
