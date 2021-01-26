package com.dtriegaardt.macrodiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // https://www.youtube.com/watch?v=VQKq9RHMS_0

    private Context context;
    private ArrayList food_name, food_serving_units, food_serving_amount, food_calories, food_fats, food_carbs, food_protein, food_notes;

    // Constructor
    RecyclerAdapter(Context context, ArrayList food_name, ArrayList food_serving_units, ArrayList food_serving_amount,
                    ArrayList food_calories, ArrayList food_fats, ArrayList food_carbs, ArrayList food_protein, ArrayList food_notes){
        this.context = context;
        this.food_name = food_name;
        this.food_serving_units = food_serving_units;
        this.food_serving_amount = food_serving_amount;
        this.food_calories = food_calories;
        this.food_fats = food_fats;
        this.food_carbs = food_carbs;
        this.food_protein = food_protein;
        this.food_notes = food_notes;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_row, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.textViewFoodItemName.setText(String.valueOf(food_name.get(position)));
        holder.textViewFoodItemServingUnit.setText(String.valueOf(food_serving_units.get(position)));
        holder.textViewFoodItemServingSize.setText(String.valueOf(food_serving_amount.get(position)));
        holder.textViewFoodItemCalories.setText(String.valueOf(food_calories.get(position)));
        holder.textViewFoodItemFatValue.setText(String.valueOf(food_fats.get(position)));
        holder.textViewFoodItemCarbsValue.setText(String.valueOf(food_carbs.get(position)));
        holder.textViewFoodItemProteinValue.setText(String.valueOf(food_protein.get(position)));
        holder.textViewFoodItemNotes.setText(String.valueOf(food_notes.get(position)));
    }

    @Override
    public int getItemCount() {
        return food_name.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView textViewFoodItemName, textViewFoodItemServingUnit, textViewFoodItemServingSize,
                textViewFoodItemCalories, textViewFoodItemFatValue, textViewFoodItemCarbsValue,
                textViewFoodItemProteinValue, textViewFoodItemNotes;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewFoodItemName = itemView.findViewById(R.id.textViewFoodItemName);
            textViewFoodItemServingUnit = itemView.findViewById(R.id.textViewFoodItemServingUnit);
            textViewFoodItemServingSize = itemView.findViewById(R.id.textViewFoodItemServingSize);
            textViewFoodItemCalories = itemView.findViewById(R.id.textViewFoodItemCalories);
            textViewFoodItemFatValue = itemView.findViewById(R.id.textViewFoodItemFatValue);
            textViewFoodItemCarbsValue = itemView.findViewById(R.id.textViewFoodItemCarbsValue);
            textViewFoodItemProteinValue = itemView.findViewById(R.id.textViewFoodItemProteinValue);
            textViewFoodItemNotes = itemView.findViewById(R.id.textViewFoodItemNotes);
        }
    }
}
