package com.w3epic.getfit.Services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Handler;
import android.os.IBinder;

import com.w3epic.getfit.StepDetector;
import com.w3epic.getfit.StepListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StepCountService extends Service implements SensorEventListener, StepListener {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    int step_today = 0;
    int step_goal = 0;

    public static final String PREF_FILE_KEY = "stepCountLog_";
    public static final String WORD = "steps";

    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;

    public StepCountService() {}

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String date = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + date, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        numSteps = 0;
        sensorManager.registerListener(StepCountService.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run() {
//                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
//                handler.postDelayed(runnable, 10000);
//            }
//        };
//
//        handler.postDelayed(runnable, 15000);
    }

    private void updateStepCountPrefData() {
        String word = "step_count";

        int step_taday = 0;

        // tead today's data
        if (sharedPreferences.contains(word)) {
            // https://developer.android.com/reference/android/content/SharedPreferences#getString(java.lang.String,%20java.lang.String)
            step_taday = Integer.parseInt(sharedPreferences.getString(word, ""));
        }

        step_taday++;

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(word, String.valueOf(step_taday));
        editor.commit(); // or use apply()
    }

    @Override
    public void onDestroy() {
        /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
        //handler.removeCallbacks(runnable);
        //Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();

        sensorManager.unregisterListener(StepCountService.this);
    }

    @Override
    public void onStart(Intent intent, int startid) {
        //Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        //Log.e("debug", String.valueOf(numSteps));
        updateStepCountPrefData();
        // add numSteps to shared pref
    }
}
