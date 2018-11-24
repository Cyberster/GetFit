package com.w3epic.getfit.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import com.w3epic.getfit.Adapter.FoodLogAdapter;
import com.w3epic.getfit.Models.DBEntities.FoodLog;
import com.w3epic.getfit.Models.FoodItem;
import com.w3epic.getfit.R;
import com.w3epic.getfit.SQLiteHelperClasss.SQLiteHelper;

import java.util.ArrayList;
import java.util.List;

public class FoodLogActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    SQLiteHelper dbHelper;
    SQLiteDatabase db;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<FoodLog> listItems;

    FoodItem[] foodItems;

    TextView tvLoading;

    int cal_consumed_today = 0;

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Loading");
                mProgressDialog.setMessage("Loading, please wait...");
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
        setContentView(R.layout.activity_food_log);
        setTitle("Food log");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        mRecyclerView = findViewById(R.id.rvFoodSearchResults);
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
            String[] projection = {dbHelper.COL_UID, dbHelper.COL_RESOURCE_ID, dbHelper.COL_NAME,
                dbHelper.COL_SERVING, dbHelper.COL_QUANTITY, dbHelper.COL_UNIT,
                    dbHelper.COL_CALORIES, dbHelper.COL_THUMBNAIL, dbHelper.COL_TIMESTAMP};

            // Filter results WHERE "title" = 'My Title'
            String selection = "date(datetime(" + dbHelper.COL_TIMESTAMP + " , 'unixepoch')) = date('now')";
            //String selection = dbHelper.COL_EMAIL + " = ? AND " + dbHelper.COL_PASS + " = ? ";
            //String[] selectionArgs = {};

            // How you want the results sorted in the resulting Cursor
            String sortOrder = dbHelper.COL_TIMESTAMP + " DESC";

            Cursor cursor = db.query(
                    dbHelper.TABLE_FOOD_LOG,   // The table to query
                    projection,             // The array of columns to return (pass null to get all)
                    selection,              // The columns for the WHERE clause
                    null,          // The values for the WHERE clause
                    null,                   // don't group the rows
                    null,                   // don't filter by row groups
                    sortOrder               // The sort order
            );

            listItems = new ArrayList<FoodLog>();
            while (cursor.moveToNext()) {
                FoodLog foodLog = new FoodLog();
                foodLog.setFoodResourceId(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_RESOURCE_ID)));
                foodLog.setName(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_NAME)));
                foodLog.setThumbnail(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_THUMBNAIL)));
                foodLog.setTimestamp(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_TIMESTAMP)));
                foodLog.setServing(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_SERVING)));
                foodLog.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_QUANTITY)));
                foodLog.setUnit(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_UNIT)));
                foodLog.setCalories(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_CALORIES)));
                foodLog.setUid(cursor.getString(cursor.getColumnIndexOrThrow(dbHelper.COL_UID)));
                Log.e("FoodLogActivity", "foodLog: " + foodLog.toString());

                cal_consumed_today += Integer.parseInt(foodLog.getCalories());

                listItems.add(foodLog);
            }
            cursor.close();

            mAdapter = new FoodLogAdapter(FoodLogActivity.this, listItems);
            mRecyclerView.setAdapter(mAdapter);

            tvLoading.setText("Total calories consumed today is " + cal_consumed_today + " cal");
        } catch (Exception e) {
            Log.d("FoodLogActivity", e.getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
                //tvLoading.setText("Loading...");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

//                listItems = new ArrayList<FoodLog>();
//
//                mAdapter = new FoodLogAdapter(FoodLogActivity.this, listItems);
//                mRecyclerView.setAdapter(mAdapter);

                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
                //tvLoading.setText("");
            }

            @Override
            protected String doInBackground(String... strings) {
                /*int limit = 10;
                //foodItems = FoodItem.jsonFetchFoodItemList(etSearchQuery.getText().toString(), limit);
                //FoodItem foodItem = new FoodItem();

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                // read data from Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                final DatabaseReference mUserReference = mDatabase.child("foodLog").child(currentUser.getUid());
                //.child(String.valueOf(System.currentTimeMillis() / 1000));
                mUserReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        Log.e("Count " ,""+snapshot.getChildrenCount());
                        int i = 0;
                        //foodItems = new FoodItem[(int) snapshot.getChildrenCount()];
                        for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                            FoodLog foodLog = postSnapshot.getValue(FoodLog.class);

//                            FoodItem foodItem = new FoodItem();
//                            foodItem.setResourceId(foodLog.getFoodResourceId());
//                            foodItem.setItemName(foodLog.getName());
//                            foodItem.setNutrientName("Calories");
//                            double totalQty = Double.parseDouble(foodLog.getQuantity()) * Double.parseDouble(foodLog.getServing());
//                            foodItem.setNutrientValue(String.valueOf(totalQty));
//                            foodItem.setNutrientUom("kcal");
//                            FoodItemDetails foodItemDetails = new FoodItemDetails(foodLog.getFoodResourceId());
                            //foodItem.setThumbnail(foodItemDetails.getImage_thumb()); // todo: getImage_thumb returning null

                            //Log.e("msg", "foodItemDetails: " + foodItemDetails.toString());
                            //Log.e("msg", "foodLog.getFoodResourceId(): " + foodLog.getFoodResourceId());

                            listItems.add(foodLog);
                        }
                    }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {}
                });
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                // read data from Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
*/
                return null;
            }
        }

        new Multitask().execute();
    }

    public void btnAddFoodOnClickHandler(View view) {
        startActivity(new Intent(FoodLogActivity.this, SearchFoodActivity.class));
    }
}
