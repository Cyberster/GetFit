package com.w3epic.getfit.Activities;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.w3epic.getfit.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StepCountLogActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    int step_today = 0;
    int step_goal = 0;

    public static final String PREF_FILE_KEY = "stepCountLog_";
    public static final String WORD = "step_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_count_log);
        setTitle("Step count log");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + date, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        step_goal = Integer.parseInt(getString(R.string.pref_step_goal_default));

        Log.d("debug", "WORD" + WORD);
        if (sharedPreferences.contains(WORD)) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            step_today = Integer.parseInt(sharedPreferences.getString(WORD, ""));
            //etMeaning.setText(sharedPreferences.getString(MEANING, ""));
        }

        if (defaultSharedPreferences.contains(getString(R.string.pref_step_goal_key))) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            step_goal = Integer.parseInt(defaultSharedPreferences.getString(getString(R.string.pref_step_goal_key), ""));
            //etMeaning.setText(sharedPreferences.getString(MEANING, ""));
        }

        Log.d("debug", String.valueOf(step_goal));
        Log.d("debug", String.valueOf(step_today));

        String text1 = step_today  + " out of " + step_goal + " steps today";
        String text2 = "You need to walk " + (step_goal-step_today)  + " more steps!";

        TextView tvStepCountLog1 = findViewById(R.id.tvStepCountLog1);
        TextView tvStepCountLog2 = findViewById(R.id.tvStepCountLog2);

        tvStepCountLog1.setText(text1);
        tvStepCountLog2.setText(text2);

    }
}
