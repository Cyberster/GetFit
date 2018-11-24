package com.w3epic.getfit.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.w3epic.getfit.Models.Nutrient;
import com.w3epic.getfit.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by anonymouse on 7/7/18.
 */

public class NutrientAdapter extends RecyclerView.Adapter<NutrientAdapter.ViewHolder> {

    private Context context;
    private List<Nutrient> nutrientItems;

    public NutrientAdapter(Context context, List<Nutrient> nutrientItems) {
        this.context = context;
        this.nutrientItems = nutrientItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nutrient, parent, false);

        return new ViewHolder(v, context, (ArrayList<Nutrient>) nutrientItems);
    }

    @Override
    public void onBindViewHolder(NutrientAdapter.ViewHolder holder, final int position) {
        Nutrient nutrientItem = nutrientItems.get(position);
        holder.tvNutrientName.setText(nutrientItem.getName());
        holder.tvNutrientValue.setText(nutrientItem.getValue().equals("null") ? "-" : nutrientItem.getValue());
        holder.tvNutrientUom.setText(nutrientItem.getValue().equals("null") ? "-" : nutrientItem.getUnit());
    }

    @Override
    public int getItemCount() {
        return nutrientItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvNutrientName;
        public TextView tvNutrientValue;
        public TextView tvNutrientUom;

        public ViewHolder(View view, Context ctx, ArrayList<Nutrient> items) {
            super(view);
            nutrientItems = items;
            //get the Activity Context
            context = ctx;

            view.setOnClickListener(this);

            tvNutrientName = view.findViewById(R.id.tvNutrientName);
            tvNutrientValue = view.findViewById(R.id.tvNutrientValue);
            tvNutrientUom = view.findViewById(R.id.tvNutrientUom);
        }

        @Override
        public void onClick(View v) {
            //Get int position
            int position = getAdapterPosition();
            final Nutrient item = nutrientItems.get(position);
            //Intent intent = new Intent(context, MyActivity.class);
            Toast.makeText(context, item.getName(), Toast.LENGTH_LONG).show();
        }
    }
}
