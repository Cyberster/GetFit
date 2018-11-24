package com.w3epic.getfit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.w3epic.getfit.Activities.FoodInformationActivity;
import com.w3epic.getfit.Models.FoodItem;
import com.w3epic.getfit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulodichone on 3/22/17.
 */

public class FoodItemAdapter extends RecyclerView.Adapter<FoodItemAdapter.ViewHolder> {

    private Context context;
    private List<FoodItem> foodItems;

    public FoodItemAdapter(Context context, List<FoodItem> foodItems) {
        this.context = context;
        this.foodItems = foodItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item, parent, false);

        return new ViewHolder(v, context, (ArrayList<FoodItem>) foodItems);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            final FoodItem foodItem = foodItems.get(position);
            holder.tvFoodTitle.setText(foodItem.getItemName());
            StringBuilder desc = new StringBuilder();
            desc.append(foodItem.getNutrientName()).append(": ");
            if (!foodItem.getNutrientValue().equals("null")) {
                desc.append(foodItem.getNutrientValue());
                desc.append(foodItem.getNutrientUom());
            } else {
                desc.append("unknown");
            }

            holder.tvFoodDescription.setText(desc.toString());
            //Log.d("test", foodItem.toString());

            try {
                // load image from url into imageview using Picasso
                Picasso.with(context).load(foodItem.getThumbnail()).fit().into(holder.ivSearchThumb);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.btnAddFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, FoodInformationActivity.class);
                    intent.putExtra("resource_id", foodItem.getResourceId());
                    intent.putExtra("mode", "add"); // mode = add/edit
                    context.startActivity(intent);
                    //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (foodItems == null) return 0;
        return foodItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvFoodTitle;
        public TextView tvFoodDescription;
        public ImageView ivSearchThumb;
        public Button btnAddFood;

        public ViewHolder(View view, Context ctx, ArrayList<FoodItem> items) {
            super(view);
            foodItems = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            tvFoodTitle         = view.findViewById(R.id.tvFoodTitle);
            tvFoodDescription   = view.findViewById(R.id.tvFoodDescription);
            ivSearchThumb       = view.findViewById(R.id.ivSearchThumb);
            btnAddFood          = view.findViewById(R.id.btnAddFood);
        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            final FoodItem item = foodItems.get(position);
            //Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getItemName(), Toast.LENGTH_LONG).show();
        }
    }
}
