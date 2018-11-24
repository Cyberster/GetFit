/*
*   Developed by Arpan Das, Ankan Biswas, Gopa Samanta, Avick Biswas
*   Government College of Engineering and Textile Technology, Serampore
* */

package com.w3epic.getfit.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.w3epic.getfit.Models.FoodItem;
import com.w3epic.getfit.Models.FoodItemDetails;
import com.w3epic.getfit.Models.WorkoutDetails;
import com.w3epic.getfit.R;
import com.w3epic.getfit.Services.StepCountService;
import com.w3epic.getfit.Services.SyncService;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;

    TextView tvText;
    FoodItem foodItem;
    FoodItem[] foodItems;
    FoodItemDetails foodItemDetails;
    WorkoutDetails workoutDetails;

    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setTitle("Loading");
                mProgressDialog.setMessage("Loading please wait...");
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
        setContentView(R.layout.activity_main);
        //new Multitask().execute();

        // intit DB variables
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        //tvText = findViewById(R.id.tvText);

        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected String doInBackground(String... strings) {
                //foodItem = new FoodItem("Milk");

                //foodItems = FoodItem.jsonFetchFoodItemList("Butter", 5);

                //workoutItemDetails = new FoodItemDetails(foodItems[0].getResourceId());

                //workoutDetails = new WorkoutDetails("100 pushup", "male", "59", "177", "27");

                //Log.d("HomeActivity", workoutDetails.toString());
                return null;
            }
        }

        new Multitask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser != null) {
            //Toast.makeText(this, "Logged in as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            ifNotLoggedInThenRedirectTo(this, LoginActivity.class );
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }

        // start step count service
        startService(new Intent(this, StepCountService.class));
        startService(new Intent(this, SyncService.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentUser != null) {
            //Toast.makeText(this, "Logged in as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
            ifNotLoggedInThenRedirectTo(this, LoginActivity.class );
        } else {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
        } else if (id == R.id.action_logout) {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            Toast.makeText(this, "Signed out successfully!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_health_tools) {
            startActivity(new Intent(this, HealthToolsActivity.class));
        } else if (id == R.id.action_contact_us) {
            startActivity(new Intent(this, ContactUsActivity.class));
        } else if (id == R.id.action_about_us) {
            startActivity(new Intent(this, AboutUsActivity.class));
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    public void btnFetchOnClickHandler(View view) {
        /*class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected String doInBackground(String... strings) {
                //tvText.setText(foodItem.toString());
                Log.d("HomeActivity", foodItem.toString());

                //try {
                workoutItemDetails = new FoodItemDetails(foodItems[0].getResourceId());
                //tvText.setText(workoutItemDetails.toString());
                //tvText.setText(foodItems[0].getResourceId());
                Log.d("HomeActivity", foodItems[0].toString());
                /*} catch (Exception e) {
                    Log.d("HomeActivity", e.getMessage());
                }* /

                //tvText.setText(workoutDetails.toString());
                Log.d("HomeActivity", workoutDetails.toString());
                return null;
            }
        }

        new Multitask().execute();

        tvText.setText(workoutItemDetails==null?"":workoutItemDetails.toString());*/
    }

    private void ifNotLoggedInThenRedirectTo(Context context, Class targetClass) {
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) { // not logged in
            startActivity(new Intent(context, targetClass));
            Toast.makeText(this, "Please log in...", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(this, "Logged in as: " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        }
        //updateUI(currentUser);
    }

    public void btnFoodLogOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, FoodLogActivity.class));
    }

    public void btnWorkoutOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, WorkoutLogActivity.class));
    }

    public void btnWaterLogOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, WaterLogActivity.class));
    }

    public void btnWeightLogOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, WeightLogActivity.class));
    }

    public void btnBodyFatPercentageLogOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, BodyFatPercentageLogActivity.class));
    }

    public void btnContactUsOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
    }

    public void btnAboutUsOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
    }

//    public void btnPedometerOnClickHandler(View view) {
//        //https://stackoverflow.com/questions/36583719/intent-to-open-a-specific-tab-of-tabbed-activity
//        int page = 1;
//        Intent intent = new Intent(HomeActivity.this, HealthToolsActivity.class);
//        intent.putExtra("tab_index", page);// One is your argument
//        startActivity(intent);
//    }

    public void btnStepCountLogOnClickHandler(View view) {
        startActivity(new Intent(HomeActivity.this, StepCountLogActivity.class));
    }
}
