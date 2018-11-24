package com.w3epic.getfit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.w3epic.getfit.Models.DBEntities.WorkoutLog;
import com.w3epic.getfit.R;
import com.w3epic.getfit.Activities.WorkoutInformationActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by paulodichone on 3/22/17.
 */

public class WorkoutLogAdapter extends RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder> {

    private Context context;
    private List<WorkoutLog> workoutLogs;

    public WorkoutLogAdapter(Context context, List<WorkoutLog> workoutLogs) {
        this.context = context;
        this.workoutLogs = workoutLogs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.workout_log_item, parent, false);

        return new ViewHolder(v, context, (ArrayList<WorkoutLog>) workoutLogs);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final WorkoutLog workoutLog = workoutLogs.get(position);
        String title = Character.toUpperCase(workoutLog.getName().charAt(0)) + workoutLog.getName().substring(1);
        holder.tvWorkoutTitle.setText(title);

        int rep = Integer.parseInt(workoutLog.getRepetation());
        int set = Integer.parseInt(workoutLog.getSet());

        StringBuilder desc1 = new StringBuilder();
        desc1.append(workoutLog.getRepetation() + " x ");
        desc1.append(workoutLog.getSet() + " = ");
        desc1.append((rep * set) + " repetitions");
        holder.tvWorkoutDesc1.setText(desc1.toString());

        StringBuilder desc2 = new StringBuilder();
        desc2.append("Duration: " + workoutLog.getDurationInMin() + " min, ");
        desc2.append(workoutLog.getKcalBurnt());
        desc2.append(" kcal");
        holder.tvWorkoutDesc2.setText(desc2.toString());

        //Log.d("test", foodItem.toString());

        // load image from url into imageview using Picasso
        //Picasso.with(context).load(workoutLog.getThumbnail()).fit().into(holder.ivSearchThumb);

        holder.btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("resource_id", workoutLog.getWorkoutResourceId());
                intent.putExtra("mode", "add"); // mode = add/edit
                context.startActivity(intent);
                //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WorkoutInformationActivity.class);
                intent.putExtra("resource_id", workoutLog.getWorkoutResourceId());
                intent.putExtra("mode", "edit"); // mode = add/edit
                context.startActivity(intent);
                //Toast.makeText(context, "position "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (workoutLogs == null) return 0;
        return workoutLogs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvWorkoutTitle;
        public TextView tvWorkoutDesc1;
        public TextView tvWorkoutDesc2;
        //public ImageView ivSearchThumb;
        public Button btnAddWorkout;
        public Button btnRemoveWorkout;

        public ViewHolder(View view, Context ctx, ArrayList<WorkoutLog> items) {
            super(view);
            workoutLogs = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            tvWorkoutTitle   = view.findViewById(R.id.tvWorkoutTitle);
            tvWorkoutDesc1   = view.findViewById(R.id.tvWorkoutDesc1);
            tvWorkoutDesc2   = view.findViewById(R.id.tvWorkoutDesc2);
            //ivSearchThumb    = view.findViewById(R.id.ivSearchThumb);
            btnAddWorkout    = view.findViewById(R.id.btnAddWorkout);
            btnRemoveWorkout = view.findViewById(R.id.btnRemoveFood);
        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            final WorkoutLog item = workoutLogs.get(position);
            //Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
