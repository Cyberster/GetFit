package com.w3epic.getfit.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.w3epic.getfit.Adapter.FoodItemAdapter;
import com.w3epic.getfit.Models.FoodItem;
import com.w3epic.getfit.R;

import java.util.ArrayList;
import java.util.List;

public class SearchFoodActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<FoodItem> listItems;

    FoodItem[] foodItems;
    EditText etSearchQuery;
    ImageView ivSearchThumb;

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
        setContentView(R.layout.activity_search_food);
        setTitle("Search food");

        etSearchQuery = findViewById(R.id.etSearchQuery);
        ivSearchThumb = findViewById(R.id.ivSearchThumb);

        mRecyclerView = findViewById(R.id.rvFoodSearchResults);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void btnSearchOnClickHandler(View view) {
        class Multitask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                showDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                mAdapter = new FoodItemAdapter(SearchFoodActivity.this, listItems);
                mRecyclerView.setAdapter(mAdapter);
                dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
            }

            @Override
            protected String doInBackground(String... strings) {
                try {
                    int limit = 10;
                    foodItems = FoodItem.jsonFetchFoodItemList(etSearchQuery.getText().toString(), limit);
                    listItems = new ArrayList<FoodItem>();

                    for (int i = 0; i < limit; i++) {
                        listItems.add(foodItems[i]);
                        //Log.d("debug", foodItems[i].toString());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }
        }

        new Multitask().execute();
    }
}