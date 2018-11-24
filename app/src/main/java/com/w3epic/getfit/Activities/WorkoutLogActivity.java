package com.w3epic.getfit.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.w3epic.getfit.Adapter.WorkoutLogAdapter;
import com.w3epic.getfit.Models.DBEntities.WorkoutLog;
import com.w3epic.getfit.R;
import com.w3epic.getfit.SQLiteHelperClasss.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class WorkoutLogActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<WorkoutLog> listItems;

    SQLiteHelper dbHelper;
    SQLiteDatabase db;

    double kcal_burnt_today = 0;
    TextView tvLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_log);
        setTitle("Workout log");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        mRecyclerView = findViewById(R.id.rvWorkoutSearchResults);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvLoading = findViewById(R.id.tvLoading);

        // fetch data food item data from sqlite
        try {
            dbHelper = new SQLiteHelper(this);

            // Gets the data repository in write mode
            db = dbHelper.getReadableDatabase();

            // Define a projection that specifies which columns from the database
            // you will actually use after this query.
//            String[] projection = {dbHelper.COL_UID, dbHelper.COL_WID, dbHelper.COL_NAME, dbHelper.COL_MET,
//                    dbHelper.COL_RESOURCE_ID, dbHelper.COL_SET_COUNT, dbHelper.COL_DURATION_MIN,
//                    dbHelper.COL_REPETITION, dbHelper.COL_UNIT, dbHelper.COL_KCAL_BURNT, dbHelper.COL_TIMESTAMP};

//            SELECT * FROM workout_log
//            WHERE date(datetime(timestamp , 'unixepoch')) = date('now');

            // Filter results WHERE "title" = 'My Title'
            String selection = "date(datetime(" + dbHelper.COL_TIMESTAMP + " , 'unixepoch')) = date('now')";
            //String[] selectionArgs = {};

            // How you want the results sorted in the resulting Cursor
            String sortOrder = dbHelper.COL_TIMESTAMP + " DESC";

            Cursor cursor = db.query(
                    dbHelper.TABLE_WORKOUT_LOG,     // The table to query
                    null,                     // The array of columns to return (pass null to get all)
                    selection,                   // The columns for the WHERE clause
                    null,               // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder                       // The sort order
            );

            Log.e("WorkoutLogActivity", db.toString());

            listItems = new ArrayList<WorkoutLog>();
            while (cursor.moveToNext()) {
                WorkoutLog workoutLog = new WorkoutLog();
                workoutLog.setUid(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_UID)));
                workoutLog.setWid(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_WID)));
                workoutLog.setName(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_NAME)));
                workoutLog.setMet(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_RESOURCE_ID)));
                workoutLog.setWorkoutResourceId(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_RESOURCE_ID)));
                workoutLog.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_TIMESTAMP)));
                workoutLog.setSet(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_SET_COUNT)));
                workoutLog.setDurationInMin(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_DURATION_MIN)));
                workoutLog.setRepetation(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_REPETITION)));
                workoutLog.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_UNIT)));
                workoutLog.setKcalBurnt(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_KCAL_BURNT)));
                workoutLog.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_TIMESTAMP)));
                Log.e("WorkoutLogActivity", "workoutLog: " + workoutLog.getName());

                kcal_burnt_today += Double.parseDouble(workoutLog.getKcalBurnt());

                listItems.add(workoutLog);
            }
            cursor.close();

            mAdapter = new WorkoutLogAdapter(WorkoutLogActivity.this, listItems);
            mRecyclerView.setAdapter(mAdapter);

            tvLoading.setText("Total calories burnt today is " + kcal_burnt_today + " kcal");
        } catch (Exception e) {
            Log.d("FoodLogActivity", e.getMessage());
        }

//        class Multitask extends AsyncTask<String, String, String> {
//            @Override
//            protected void onPreExecute() {
//                //showDialog(DIALOG_DOWNLOAD_PROGRESS);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//
//                listItems = new ArrayList<WorkoutDetails>();
//
//                mAdapter = new WorkoutItemAdapter(WorkoutLogActivity.this, listItems);
//                mRecyclerView.setAdapter(mAdapter);
//
//                //dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
//            }
//
//            @Override
//            protected String doInBackground(String... strings) {
//                try {
//                    int limit = 10;
//                    //foodItems = FoodItem.jsonFetchFoodItemList(etSearchQuery.getText().toString(), limit);
//                    //FoodItem foodItem = new FoodItem();
//
//                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                    // read data from Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                    final DatabaseReference mUserReference = mDatabase.child("workoutLog").child(currentUser.getUid());
//                    //.child(String.valueOf(System.currentTimeMillis() / 1000));
//
//                    mUserReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot snapshot) {
//                            Log.e("Count ", "" + snapshot.getChildrenCount());
//                            int i = 0;
//                            //foodItems = new FoodItem[(int) snapshot.getChildrenCount()];
//                            for (DataSnapshot postSnapshot : snapshot.getChildren()) {
//                                WorkoutLog workoutLog = postSnapshot.getValue(WorkoutLog.class);
//
//                                WorkoutDetails workoutDetails = new WorkoutDetails();
//                                workoutDetails.setTagId(workoutLog.getWorkoutResourceId());
//                                workoutDetails.setName(workoutLog.getName());
//                                workoutDetails.setDurationMin(workoutLog.getDurationInMin());
//                                workoutDetails.setNfCalories(workoutLog.getKcalBurnt());
//                                workoutDetails.setMet(workoutLog.getMet());
//                                workoutDetails.setPhotoFull("");
//                                workoutDetails.setPhotoThumb("");
//
//                                listItems.add(workoutDetails);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                            Log.e("The read failed: ", databaseError.getMessage());
//                        }
//                    });
//                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                    // read data from Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//        }
//
//        new Multitask().execute();
    }

    public void btnAddWorkoutOnClickHandler(View view) {
        startActivity(new Intent(WorkoutLogActivity.this, SearchWorkoutActivity.class));
    }
}
