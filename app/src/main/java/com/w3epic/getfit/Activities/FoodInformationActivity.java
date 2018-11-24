package com.w3epic.getfit.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.w3epic.getfit.Adapter.NutrientAdapter;
import com.w3epic.getfit.Models.DBEntities.FoodLog;
import com.w3epic.getfit.Models.FoodItemDetails;
import com.w3epic.getfit.Models.Nutrient;
import com.w3epic.getfit.R;
import com.w3epic.getfit.SQLiteHelperClasss.SQLiteHelper;
import com.w3epic.getfit.Services.SyncService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodInformationActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences defaultSharedPreferences;

    public static final String PREF_FILE_KEY = "foodLog_";
    public static final String WORD = "food_item";

    SQLiteHelper dbHelper;
    SQLiteDatabase db;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<Nutrient> listItems;

    FoodItemDetails foodItemDetails;

    TextView tvTitle;
    ImageView ivFoodImageFull;

    NumberPicker npServing;
    NumberPicker npQuantity;
    NumberPicker npUnit;

//    private static final int REQUEST_TYPE_FIRST_LOAD = 0;
//    private static final int REQUEST_TYPE_REFRESH = 1;

    double serving;
    double quantity;
    double unit;
    double totalQuantity;

    double firstServing;
    double firstQuantity;
    double firstUnit;
    double firstTotalQuantity;
    //List<Nutrient> firstListItems; // todo: deep copy from listItems to firstListItems not working
    double firstCaloriesValue;
    double firstProteinValue;
    double firstTotalCarbohydrateValue;
    double firstDietaryFiberValue;
    double firstSugarValue;
    double firstTotalFatValue;
    double firstSaturatedFatValue;
    double firstPolyunsaturatedFatValue;
    double firstMonounsaturatedFatValue;
    double firstTransFatValue;
    double firstCholesterolValue;
    double firstSodiumValue;
    double firstPotassiumValue;
    double firstCalciumValue;
    double firstIronValue;
    double firstMagnesiumValue;
    double firstZincValue;
    double firstSeleniumValue;
    double firstVitaminAValue;
    double firstVitaminCValue;
    //int[] firstNutrientValues = new int[20];



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
        setContentView(R.layout.activity_food_information);
        setTitle("Food information");

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String data = df.format(new Date());
        sharedPreferences = getSharedPreferences(PREF_FILE_KEY + data, Context.MODE_PRIVATE);
        defaultSharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);

        dbHelper = new SQLiteHelper(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        currentUser = mAuth.getCurrentUser();

        mRecyclerView = findViewById(R.id.rvFoodInfo);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tvTitle = findViewById(R.id.tvTitle);
        ivFoodImageFull = findViewById(R.id.ivFoodImageFull);

        npServing = findViewById(R.id.npDuration);
        npServing.setMinValue(1);
        npServing.setMaxValue(100);
        npServing.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                if (scrollState == SCROLL_STATE_IDLE){
                    serving = numberPicker.getValue();
                    totalQuantity = serving * quantity;

                    updateListItem();
                    Log.d("debug", String.valueOf(totalQuantity));
                }
            }
        });

        npQuantity = findViewById(R.id.npRepetation);
        /*ArrayList<String> quantityValues = new ArrayList<>();
        float sum = 0F;
        for (int i = 0; i < 10; i++) {
            quantityValues.add(String.valueOf(sum));
            sum += 0.1F;
        }
        for (int i = 1; i <= 1000; i++) {
            quantityValues.add(String.valueOf(i));
        }

        for (int i = 0; i <= 1010; i++) {
            Log.d("debug", (String) quantityValues.toArray()[i]);
        }*/
        npQuantity.setMinValue(0);
        npQuantity.setMaxValue(1010);
        //npQuantity.setDisplayedValues(quantityValues.toArray(new String[0]));
        npQuantity.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                quantity = numberPicker.getValue();
                totalQuantity = serving * quantity;

                updateListItem();
                Log.d("debug", String.valueOf(totalQuantity) + "serving:" + serving + "quantity:" + quantity);
            }
        });

        npUnit = findViewById(R.id.npSet);
        npUnit.setMinValue(0);
        npUnit.setMaxValue(1);
        npUnit.setDisplayedValues(new String[] {"g", "ml"});
        npUnit.setOnScrollListener(new NumberPicker.OnScrollListener() {
            private int scrollState = 0;
            @Override
            public void onScrollStateChange(NumberPicker numberPicker, int i) {
                unit = numberPicker.getValue();
                Log.d("debug", String.valueOf(numberPicker.getValue()));
            }
        });



        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    // setting title
                    tvTitle.setText(foodItemDetails.getName());

                    // load image from url into imageview using Picasso
                    Picasso.with(FoodInformationActivity.this).load(foodItemDetails.getImage_full()).fit().into(ivFoodImageFull);

                    // change number picker values
                    npServing.setValue(1);
                    serving = 1;

                    Log.d("debug", foodItemDetails.getQuantity()); // 0.8g
                    double qty = Double.parseDouble(foodItemDetails.getQuantity());
                    quantity = (double) Math.round(qty);
                    npQuantity.setValue(Integer.parseInt(String.valueOf((int) Math.round(qty))));

                    npUnit.setValue(1);
                    unit = 1;
                    npUnit.setValue(foodItemDetails.getUnitOfMeasure().equals("g") ? 0 : 1);

                    // adding list of nutrients into recyclerview
                    mAdapter = new NutrientAdapter(FoodInformationActivity.this, listItems);
                    mRecyclerView.setAdapter(mAdapter);

                    dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

                    firstServing = serving;
                    firstQuantity = quantity;
                    firstUnit = unit;
                    firstTotalQuantity = quantity;
                    firstCaloriesValue = Double.parseDouble(listItems.get(0).getValue());
                    firstProteinValue = Double.parseDouble(listItems.get(1).getValue());
                    firstTotalCarbohydrateValue = Double.parseDouble(listItems.get(2).getValue());
                    firstDietaryFiberValue = Double.parseDouble(listItems.get(3).getValue());
                    firstSugarValue = Double.parseDouble(listItems.get(4).getValue());
                    firstTotalFatValue = Double.parseDouble(listItems.get(5).getValue());
                    firstSaturatedFatValue = Double.parseDouble(listItems.get(6).getValue());
                    firstPolyunsaturatedFatValue = Double.parseDouble(listItems.get(7).getValue());
                    firstMonounsaturatedFatValue = Double.parseDouble(listItems.get(8).getValue());
                    firstTransFatValue = Double.parseDouble(listItems.get(9).getValue());
                    firstCholesterolValue = Double.parseDouble(listItems.get(10).getValue());
                    firstSodiumValue = Double.parseDouble(listItems.get(11).getValue());
                    firstPotassiumValue = Double.parseDouble(listItems.get(12).getValue());
                    firstCalciumValue = Double.parseDouble(listItems.get(13).getValue());
                    firstIronValue = Double.parseDouble(listItems.get(14).getValue());
                    firstMagnesiumValue = Double.parseDouble(listItems.get(15).getValue());
                    firstZincValue = Double.parseDouble(listItems.get(16).getValue());
                    firstSeleniumValue = Double.parseDouble(listItems.get(17).getValue());
                    firstVitaminAValue = Double.parseDouble(listItems.get(18).getValue());
                    firstVitaminCValue = Double.parseDouble(listItems.get(19).getValue());
                    //firstListItems = new ArrayList<Nutrient>(listItems);
                /*try {
                    Log.d("debug", String.valueOf(listItems.get(2).getValue()));
                } catch (Exception e) {
                    Log.d("debug", e.getMessage());
                }*/
                    //firstListItems = new ArrayList<Nutrient>();
                /*for (Nutrient item : listItems) {
                    firstListItems.add(item);
                }*/
                /*for (int i=0; i<20; i++) {
                    firstNutrientValues[i] = Integer.parseInt(listItems.get(i).getValue());
                }*/

                    //firstListItems = new ArrayList<>(listItems);
                    //Collections.copy(firstListItems, listItems);
                    //Log.d("debug", "firstTotalQuantity: " + String.valueOf(firstTotalQuantity));

                    //Toast.makeText(FoodInformationActivity.this, getIntent().getStringExtra("resource_id"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... strings) {
                foodItemDetails = new FoodItemDetails(getIntent().getStringExtra("resource_id"));

                //String calories = getValueByUnitaryMethod();
                //workoutItemDetails.getNutrientInfo().getCalories().setValue(calories);

                listItems = new ArrayList<Nutrient>();
                listItems.add(foodItemDetails.getNutrientInfo().getCalories());
                listItems.add(foodItemDetails.getNutrientInfo().getProtein());
                listItems.add(foodItemDetails.getNutrientInfo().getTotalCarbohydrate());
                listItems.add(foodItemDetails.getNutrientInfo().getDietaryFiber());
                listItems.add(foodItemDetails.getNutrientInfo().getSugar());
                listItems.add(foodItemDetails.getNutrientInfo().getTotalFat());
                listItems.add(foodItemDetails.getNutrientInfo().getSaturatedFat());
                listItems.add(foodItemDetails.getNutrientInfo().getPolyunsaturatedFat());
                listItems.add(foodItemDetails.getNutrientInfo().getMonounsaturatedFat());
                listItems.add(foodItemDetails.getNutrientInfo().getTransFat());
                listItems.add(foodItemDetails.getNutrientInfo().getCholesterol());
                listItems.add(foodItemDetails.getNutrientInfo().getSodium());
                listItems.add(foodItemDetails.getNutrientInfo().getPotassium());
                listItems.add(foodItemDetails.getNutrientInfo().getCalcium());
                listItems.add(foodItemDetails.getNutrientInfo().getIron());
                listItems.add(foodItemDetails.getNutrientInfo().getMagnesium());
                listItems.add(foodItemDetails.getNutrientInfo().getZinc());
                listItems.add(foodItemDetails.getNutrientInfo().getSelenium());
                listItems.add(foodItemDetails.getNutrientInfo().getVitaminA());
                listItems.add(foodItemDetails.getNutrientInfo().getVitaminC());

                return null;
            }
        }

        new Multitask().execute();
    }

    private void updateListItem() {
        //String calories = getValueByUnitaryMethod();
        //String caloriesValue = workoutItemDetails.getNutrientInfo().getCalories().getValue();
        String caloriesValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstCaloriesValue));
        foodItemDetails.getNutrientInfo().getCalories().setValue(String.valueOf((int) Math.round(Double.parseDouble(caloriesValue))));
        String proteinValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstProteinValue));
        foodItemDetails.getNutrientInfo().getProtein().setValue(String.valueOf((int) Math.round(Double.parseDouble(proteinValue))));
        String totalCarbohydrateValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstTotalCarbohydrateValue));
        foodItemDetails.getNutrientInfo().getTotalCarbohydrate().setValue(String.valueOf((int) Math.round(Double.parseDouble(totalCarbohydrateValue))));
        String dietaryFiberValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstDietaryFiberValue));
        foodItemDetails.getNutrientInfo().getDietaryFiber().setValue(String.valueOf((int) Math.round(Double.parseDouble(dietaryFiberValue))));
        String sugarValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstSugarValue));
        foodItemDetails.getNutrientInfo().getSugar().setValue(String.valueOf((int) Math.round(Double.parseDouble(sugarValue))));
        String totalFatValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstTotalFatValue));
        foodItemDetails.getNutrientInfo().getTotalFat().setValue(String.valueOf((int) Math.round(Double.parseDouble(totalFatValue))));
        String saturatedFatValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstSaturatedFatValue));
        foodItemDetails.getNutrientInfo().getSaturatedFat().setValue(String.valueOf((int) Math.round(Double.parseDouble(saturatedFatValue))));
        String polyunsaturatedFatValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstPolyunsaturatedFatValue));
        foodItemDetails.getNutrientInfo().getPolyunsaturatedFat().setValue(String.valueOf((int) Math.round(Double.parseDouble(polyunsaturatedFatValue))));
        String monounsaturatedFatValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstMonounsaturatedFatValue));
        foodItemDetails.getNutrientInfo().getMonounsaturatedFat().setValue(String.valueOf((int) Math.round(Double.parseDouble(monounsaturatedFatValue))));
        String transFatValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstTransFatValue));
        foodItemDetails.getNutrientInfo().getTransFat().setValue(String.valueOf((int) Math.round(Double.parseDouble(transFatValue))));
        String cholesterolValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstCholesterolValue));
        foodItemDetails.getNutrientInfo().getCholesterol().setValue(String.valueOf((int) Math.round(Double.parseDouble(cholesterolValue))));
        String sodiumValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstSodiumValue));
        foodItemDetails.getNutrientInfo().getSodium().setValue(String.valueOf((int) Math.round(Double.parseDouble(sodiumValue))));
        String potassiumValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstPotassiumValue));
        foodItemDetails.getNutrientInfo().getPotassium().setValue(String.valueOf((int) Math.round(Double.parseDouble(potassiumValue))));
        String calciumValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstCalciumValue));
        foodItemDetails.getNutrientInfo().getCalcium().setValue(String.valueOf((int) Math.round(Double.parseDouble(calciumValue))));
        String ironValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstIronValue));
        foodItemDetails.getNutrientInfo().getIron().setValue(String.valueOf((int) Math.round(Double.parseDouble(ironValue))));
        String magnesiumValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstMagnesiumValue));
        foodItemDetails.getNutrientInfo().getMagnesium().setValue(String.valueOf((int) Math.round(Double.parseDouble(magnesiumValue))));
        String zincValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstZincValue));
        foodItemDetails.getNutrientInfo().getZinc().setValue(String.valueOf((int) Math.round(Double.parseDouble(zincValue))));
        String seleniumValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstSeleniumValue));
        foodItemDetails.getNutrientInfo().getSelenium().setValue(String.valueOf((int) Math.round(Double.parseDouble(seleniumValue))));
        String vitaminAValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstVitaminAValue));
        foodItemDetails.getNutrientInfo().getVitaminA().setValue(String.valueOf((int) Math.round(Double.parseDouble(vitaminAValue))));
        String vitaminCValue = String.valueOf(getValueByUnitaryMethod(firstTotalQuantity, totalQuantity, firstVitaminCValue));
        foodItemDetails.getNutrientInfo().getVitaminC().setValue(String.valueOf((int) Math.round(Double.parseDouble(vitaminCValue))));

        listItems = new ArrayList<Nutrient>();
        listItems.add(foodItemDetails.getNutrientInfo().getCalories());
        listItems.add(foodItemDetails.getNutrientInfo().getProtein());
        listItems.add(foodItemDetails.getNutrientInfo().getTotalCarbohydrate());
        listItems.add(foodItemDetails.getNutrientInfo().getDietaryFiber());
        listItems.add(foodItemDetails.getNutrientInfo().getSugar());
        listItems.add(foodItemDetails.getNutrientInfo().getTotalFat());
        listItems.add(foodItemDetails.getNutrientInfo().getSaturatedFat());
        listItems.add(foodItemDetails.getNutrientInfo().getPolyunsaturatedFat());
        listItems.add(foodItemDetails.getNutrientInfo().getMonounsaturatedFat());
        listItems.add(foodItemDetails.getNutrientInfo().getTransFat());
        listItems.add(foodItemDetails.getNutrientInfo().getCholesterol());
        listItems.add(foodItemDetails.getNutrientInfo().getSodium());
        listItems.add(foodItemDetails.getNutrientInfo().getPotassium());
        listItems.add(foodItemDetails.getNutrientInfo().getCalcium());
        listItems.add(foodItemDetails.getNutrientInfo().getIron());
        listItems.add(foodItemDetails.getNutrientInfo().getMagnesium());
        listItems.add(foodItemDetails.getNutrientInfo().getZinc());
        listItems.add(foodItemDetails.getNutrientInfo().getSelenium());
        listItems.add(foodItemDetails.getNutrientInfo().getVitaminA());
        listItems.add(foodItemDetails.getNutrientInfo().getVitaminC());

        // adding list of nutrients into recyclerview
        mAdapter = new NutrientAdapter(FoodInformationActivity.this, listItems);
        mRecyclerView.setAdapter(mAdapter);
    }

    private double getValueByUnitaryMethod(double oldQty, double newQty, double nutrientValue) {
        //Log.d("debug", "oldQty: " + oldQty + " newQty: " + newQty + " nutrientValue:" + nutrientValue);
        //Log.d("debug", "(newQty * nutrientValue) / oldQty =  " + ((newQty * nutrientValue) / oldQty));
        return (newQty * nutrientValue) / oldQty;
    }

    public void btnAddFoodToLogOnClickHandler(View view) {
        FoodLog foodLog = new FoodLog();
        foodLog.setUid(currentUser.getUid());
        foodLog.setFoodResourceId(foodItemDetails.getResourceId());
        foodLog.setName(foodItemDetails.getName());
        foodLog.setServing(String.valueOf(serving));
        foodLog.setQuantity(String.valueOf(quantity));
        foodLog.setUnit(foodItemDetails.getUnitOfMeasure());
        foodLog.setCalories(foodItemDetails.getNutrientInfo().getCalories().getValue());
        foodLog.setThumbnail(foodItemDetails.getImage_thumb());
        foodLog.setTimestamp(String.valueOf(System.currentTimeMillis() / 1000));
        Log.d("debug", "foodLog: " + foodLog.toString());

        // write
//        try {
            // file path: /data/data/com.w3epic.getfit/logs/foodLog/12-06-2018.json
            String mJsonResponse = foodLog.toJsonString();

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String date = df.format(new Date());

            //String filename = "/data/data/" + getApplicationContext().getPackageName() + "/logs/foodLog/" + date + ".json";
            String filename = "/data/data/" + getApplicationContext().getPackageName() + "/foodLog_" + date + ".xml";
            Log.e("debug", filename);




        // SQLite insert start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // check if email has already registred
        db = dbHelper.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = dbHelper.COL_TIMESTAMP + " = ? ";
        String[] selectionArgs = {foodLog.getTimestamp()};

        Cursor cursor = db.query(
                dbHelper.TABLE_FOOD_LOG,   // The table to query
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
            values.put(dbHelper.COL_UID, foodLog.getUid());
            values.put(dbHelper.COL_RESOURCE_ID, foodLog.getFoodResourceId());
            values.put(dbHelper.COL_NAME, foodLog.getName());
            values.put(dbHelper.COL_SERVING, foodLog.getServing());
            values.put(dbHelper.COL_QUANTITY, foodLog.getQuantity());
            values.put(dbHelper.COL_UNIT, foodLog.getUnit());
            values.put(dbHelper.COL_CALORIES, foodLog.getCalories());
            values.put(dbHelper.COL_THUMBNAIL, foodLog.getThumbnail());
            values.put(dbHelper.COL_TIMESTAMP, foodLog.getTimestamp());

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(dbHelper.TABLE_FOOD_LOG, null, values);

            Toast.makeText(this, "Food added!", Toast.LENGTH_SHORT).show();
            Log.e("FoodInformationActivity", "newRowId");
        }
        // SQLite insert end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


        Intent syncIntent = new Intent(this, SyncService.class);
        syncIntent.putExtra("className", getClass().getSimpleName());
        startService(syncIntent);

        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to Firebase database start @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to db
        //mDatabase.child("foodLog").setValue(currentUser.getUid());
        //mDatabase.child("foodLog").child(currentUser.getUid()).setValue(foodLog.getTimestamp());
        mDatabase.child("foodLog").child(currentUser.getUid()).child(foodLog.getTimestamp()).setValue(foodLog);
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // write data to Firebase database end @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        Intent foodLogIntent = new Intent(FoodInformationActivity.this, FoodLogActivity.class);
        //intent.putExtra();
        startActivity(foodLogIntent);
    }
}
