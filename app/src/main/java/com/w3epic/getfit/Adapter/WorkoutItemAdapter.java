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
import com.w3epic.getfit.Models.WorkoutDetails;
import com.w3epic.getfit.R;
import com.w3epic.getfit.Activities.WorkoutInformationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulodichone on 3/22/17.
 */

public class WorkoutItemAdapter extends RecyclerView.Adapter<WorkoutItemAdapter.ViewHolder> {

    private Context context;
    private List<WorkoutDetails> workoutItems;

    public WorkoutItemAdapter(Context context, List<WorkoutDetails> workoutItems) {
        this.context = context;
        this.workoutItems = workoutItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_item, parent, false);

        return new ViewHolder(v, context, (ArrayList<WorkoutDetails>) workoutItems);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WorkoutDetails workoutItem = workoutItems.get(position);
        holder.tvWorkoutTitle.setText(workoutItem.getName());
        StringBuilder desc = new StringBuilder();
        desc.append(workoutItem.getNfCalories()).append("kcal");

        /*if (!foodItem.getNutrientValue().equals("null")) {
            desc.append(foodItem.getNutrientValue());
            desc.append(foodItem.getNutrientUom());
        } else {
            desc.append("unknown");
        }*/

        holder.tvWorkoutDescription.setText(desc.toString());
        //Log.d("test", foodItem.toString());

        try {
            // load image from url into imageview using Picasso
            Picasso.with(context).load(workoutItem.getPhotoThumb()).fit().into(holder.ivSearchThumb);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("tag_id", workoutItem.getTagId());
                intent.putExtra("workout_name", workoutItem.getName());
                context.startActivity(intent);
                //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return workoutItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvWorkoutTitle;
        public TextView tvWorkoutDescription;
        public ImageView ivSearchThumb;
        public Button btnAddWorkout;

        public ViewHolder(View view, Context ctx, ArrayList<WorkoutDetails> items) {
            super(view);
            workoutItems = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            tvWorkoutTitle         = view.findViewById(R.id.tvWorkoutTitle);
            tvWorkoutDescription   = view.findViewById(R.id.tvWorkoutDescription);
            ivSearchThumb          = view.findViewById(R.id.ivSearchThumb);
            btnAddWorkout          = view.findViewById(R.id.btnAddWorkout);
        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            final WorkoutDetails item = workoutItems.get(position);
            //Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
