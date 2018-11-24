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
import com.w3epic.getfit.Models.DBEntities.FoodLog;
import com.w3epic.getfit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulodichone on 3/22/17.
 */

public class FoodLogAdapter extends RecyclerView.Adapter<FoodLogAdapter.ViewHolder> {

    private Context context;
    private List<FoodLog> foodLogs;

    public FoodLogAdapter(Context context, List<FoodLog> foodLogs) {
        this.context = context;
        this.foodLogs = foodLogs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_log_item, parent, false);

        return new ViewHolder(v, context, (ArrayList<FoodLog>) foodLogs);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final FoodLog foodLog = foodLogs.get(position);
        holder.tvFoodTitle.setText(foodLog.getName());
        StringBuilder desc = new StringBuilder();

        desc.append(foodLog.getCalories());
        desc.append(" kcal");

        holder.tvFoodDescription.setText(desc.toString());
        //Log.d("test", foodItem.toString());

        try {
            // load image from url into imageview using Picasso
            Picasso.with(context).load(foodLog.getThumbnail()).fit().into(holder.ivSearchThumb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodInformationActivity.class);
                intent.putExtra("resource_id", foodLog.getFoodResourceId());
                intent.putExtra("mode", "add"); // mode = add/edit
                context.startActivity(intent);
                //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FoodInformationActivity.class);
                intent.putExtra("resource_id", foodLog.getFoodResourceId());
                intent.putExtra("mode", "edit"); // mode = add/edit
                context.startActivity(intent);
                //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (foodLogs == null) return 0;
        return foodLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvFoodTitle;
        public TextView tvFoodDescription;
        public ImageView ivSearchThumb;
        public Button btnAddFood;
        public Button btnRemoveFood;

        public ViewHolder(View view, Context ctx, ArrayList<FoodLog> items) {
            super(view);
            foodLogs = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            tvFoodTitle         = view.findViewById(R.id.tvFoodTitle);
            tvFoodDescription   = view.findViewById(R.id.tvFoodDescription);
            ivSearchThumb       = view.findViewById(R.id.ivSearchThumb);
            btnAddFood          = view.findViewById(R.id.btnAddFood);
            btnRemoveFood       = view.findViewById(R.id.btnRemoveFood);
        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            final FoodLog item = foodLogs.get(position);
            //Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
