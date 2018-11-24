package com.w3epic.getfit.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.w3epic.getfit.Models.DBEntities.WorkoutLog;
import com.w3epic.getfit.Models.WorkoutDetails;
import com.w3epic.getfit.R;
import com.w3epic.getfit.SQLiteHelperClasss.SQLiteHelper;

public class WorkoutInformationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    WorkoutDetails workoutItemDetails;

    TextView tvTitle;
    WebView wvWorkoutVideo;
    TextView tvCaloriesBurnt;
    TextView tvMet;

    NumberPicker npDuration;
    NumberPicker npRepetation;
    NumberPicker npSet;

    int duration;
    int repetation;
    int set;
    int totalRepetation;
    int totalDuration;
    int setType; // 0=duration / 1=repetation
    final static int SET_TYPE_DURATION = 0;
    final static int SET_TYPE_REPETATION = 1;

    int firstDuration;
    int firstRepetation;
    int firstSet;
    int firstTotalDuration;
    int firstTotalRepetation;

    int firstCaloriesBurntValue;

    SQLiteHelper dbHelper;
    SQLiteDatabase db;

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Loading");
                mProgressDialog.setMessage("Fetching JSON data...");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_information);
        setTitle("Workout information");

        dbHelper = new SQLiteHelper(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();


        tvTitle = findViewById(R.id.tvTitle);
        tvCaloriesBurnt = findViewById(R.id.tvCaloriesBurnt);
        tvMet = findViewById(R.id.tvMet);

        // embedding youtube video start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        wvWorkoutVideo = findViewById(R.id.wvWorkoutVideo);
        String myVideoYoutubeId = "5eSM88TFzAs";

        wvWorkoutVideo.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });

        WebSettings webSettings = wvWorkoutVideo.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        wvWorkoutVideo.loadUrl("https://www.youtube.com/embed/" + myVideoYoutubeId);





        // embedding youtube video end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        npDuration = findViewById(R.id.npDuration);
        npDuration.setMinValue(1);
        npDuration.setMaxValue(300);
        npDuration.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                if (scrollState == SCROLL_STATE_IDLE){
                    duration = numberPicker.getValue();
                    totalDuration = duration * set;
                    setType = SET_TYPE_DURATION;

                    String workoutDetailsCombined = String.valueOf(totalDuration) + "min " + workoutItemDetails.getName();
                    updateWorkoutDetails(workoutDetailsCombined);
                    //Log.d("debug", "workoutDetailsCombined" + workoutDetailsCombined);
                }
            }
        });

        npRepetation = findViewById(R.id.npRepetation);
        npRepetation.setMinValue(1);
        npRepetation.setMaxValue(1000);
        npRepetation.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                if (scrollState == SCROLL_STATE_IDLE){
                    repetation = numberPicker.getValue();
                    totalRepetation = repetation * set;
                    setType = SET_TYPE_REPETATION;

                    String workoutDetailsCombined = totalRepetation + " " + workoutItemDetails.getName();
                    updateWorkoutDetails(workoutDetailsCombined);
                    //Log.d("debug", "workoutDetailsCombined" + workoutDetailsCombined);
                }
            }
        });

        npSet = findViewById(R.id.npSet);
        npSet.setMinValue(1);
        npSet.setMaxValue(10);
        npSet.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                if (scrollState == SCROLL_STATE_IDLE){
                    set = numberPicker.getValue();
                    totalDuration = duration * set;
                    totalRepetation = repetation * set;

                    String workoutDetailsCombined;
                    if (setType == SET_TYPE_DURATION) {
                        workoutDetailsCombined = String.valueOf(totalDuration) + "min " + workoutItemDetails.getName();
                    } else {
                        workoutDetailsCombined = totalRepetation + " " + workoutItemDetails.getName();
                    }

                    updateWorkoutDetails(workoutDetailsCombined);
                    Log.d("debug", "workoutDetailsCombined" + workoutDetailsCombined);
                }
            }
        });

        Toast.makeText(this, getIntent().getStringExtra("tag_id"), Toast.LENGTH_SHORT).show();

        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // setting title
                tvTitle.setText(workoutItemDetails.getName());
                tvCaloriesBurnt.setText(workoutItemDetails.getNfCalories() + " kcal burnt!");
                tvMet.setText("MET: " + workoutItemDetails.getMet());


                // load image from url into imageview using Picasso
                //Picasso.with(FoodInformationActivity.this).load(workoutItemDetails.getImage_full()).fit().into(ivFoodImageFull);

                // change number picker values
                duration = Integer.parseInt(workoutItemDetails.getDurationMin());
                npDuration.setValue(duration);

//                //npRepetation.setValue(Integer.parseInt(String.valueOf((int) Math.round(rep))));
//
                repetation = 1;
                npRepetation.setValue(repetation);
                set = 1;
                npSet.setValue(set);

                setType = SET_TYPE_DURATION;

                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected String doInBackground(String... strings) {
                workoutItemDetails = WorkoutDetails.getWorkoutDetailsByName(getIntent().getStringExtra("workout_name"));
                return null;
            }
        }

        new Multitask().execute();
    }

    private void updateWorkoutDetails(final String workoutDetailsCombined) {

        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                //showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                // setting title
                //tvTitle.setText(workoutItemDetails.getName());
                tvCaloriesBurnt.setText(workoutItemDetails.getNfCalories() + " kcal burnt!");
                tvMet.setText("MET: " + workoutItemDetails.getMet());

                // change number picker values
                //duration = Integer.parseInt(workoutItemDetails.getDurationMin());
                //npDuration.setValue(duration);

//                //npRepetation.setValue(Integer.parseInt(String.valueOf((int) Math.round(rep))));
//                npSet.setValue(1);
//                set = 1;

                //dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected String doInBackground(String... strings) {
                workoutItemDetails = WorkoutDetails.getWorkoutDetailsByName(workoutDetailsCombined);
                return null;
            }
        }

        new Multitask().execute();
    }


//        //String calories = getValueByUnitaryMethod();
//        //String caloriesValue = workoutItemDetails.getNutrientInfo().getCalories().getValue();
//        String caloriesValue = String.valueOf(getValueByUnitaryMethod(firstTotalRepetation, totalRepetation, firstCaloriesBurntValue));
//        workoutItemDetails.setNfCalories(String.valueOf((int) Math.round(Double.parseDouble(caloriesValue))));

    private double getValueByUnitaryMethod(double oldQty, double newQty, double nutrientValue) {
        //Log.d("debug", "oldQty: " + oldQty + " newQty: " + newQty + " nutrientValue:" + nutrientValue);
        //Log.d("debug", "(newQty * nutrientValue) / oldQty =  " + ((newQty * nutrientValue) / oldQty));
        return (newQty * nutrientValue) / oldQty;
    }

    public void btnAddWorkoutToLogOnClickHandler(View view) {
        WorkoutLog workoutLog = new WorkoutLog();
        workoutLog.setUid(currentUser.getUid());
        workoutLog.setWid("0");
        workoutLog.setName(workoutItemDetails.getName());
        workoutLog.setMet(workoutItemDetails.getMet());
        workoutLog.setWorkoutResourceId(workoutItemDetails.getTagId());
        workoutLog.setSet(String.valueOf(set));
        workoutLog.setDurationInMin(String.valueOf(duration));
        workoutLog.setRepetation(String.valueOf(repetation));
        workoutLog.setUnit(String.valueOf("min/rep"));
        workoutLog.setKcalBurnt(workoutItemDetails.getNfCalories());
        workoutLog.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        Log.e("debug", "foodLog: " + workoutLog.toString());

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to db
        //mDatabase.child("foodLog").setValue(currentUser.getUid());
        //mDatabase.child("foodLog").child(currentUser.getUid()).setValue(foodLog.getTimestamp());
        mDatabase.child("workoutLog").child(currentUser.getUid()).child(workoutLog.getTimestamp()).setValue(workoutLog);
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



        // SQLite insert start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // check if email has already registred
        //Log.d("test: ", dbHelper.SQL_CREATE_ENTRIES);
        db = dbHelper.getReadableDatabase();

//        String[] params = new String[]{ contact_no };
//        Cursor c = db.rawQuery("SELECT * FROM contacts WHERE ans = ?", params);

        // Filter results WHERE "title" = 'My Title'
        String selection = dbHelper.COL_TIMESTAMP + " = ? ";
        String[] selectionArgs = {workoutLog.getTimestamp()};


        Cursor cursor = db.query(
                dbHelper.TABLE_WORKOUT_LOG,        // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        //List itemIds = new ArrayList();
        if (cursor.moveToNext()) {
            //Toast.makeText(this, "Email already registred!", Toast.LENGTH_SHORT).show();
            Log.d("debug", "record already exists");
        } else {
            // Gets the data repository in write mode
            db = dbHelper.getWritableDatabase();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(dbHelper.COL_UID, workoutLog.getUid());
            values.put(dbHelper.COL_WID, workoutLog.getWid());
            values.put(dbHelper.COL_NAME, workoutLog.getName());
            values.put(dbHelper.COL_MET, workoutLog.getMet());
            values.put(dbHelper.COL_RESOURCE_ID, workoutLog.getWorkoutResourceId());
            values.put(dbHelper.COL_SET_COUNT, workoutLog.getSet());
            values.put(dbHelper.COL_DURATION_MIN, workoutLog.getDurationInMin());
            values.put(dbHelper.COL_REPETITION, workoutLog.getRepetation());
            values.put(dbHelper.COL_UNIT, workoutLog.getUnit());
            values.put(dbHelper.COL_KCAL_BURNT, workoutLog.getKcalBurnt());
            values.put(dbHelper.COL_TIMESTAMP, workoutLog.getTimestamp());

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(dbHelper.TABLE_WORKOUT_LOG, null, values);

            Toast.makeText(this, "Workout added!", Toast.LENGTH_SHORT).show();
            Log.e("FoodInformationActivity", String.valueOf(newRowId));
        }
        // SQLite insert end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        Intent intent = new Intent(WorkoutInformationActivity.this, WorkoutLogActivity.class);
        //intent.putExtra();
        startActivity(intent);
    }
}

