package com.dtriegaardt.macrodiet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DailyLog extends AppCompatActivity {

    RecyclerView recyclerView;

    DatabaseHelperClass database;
    ArrayList<String> food_id, food_name, food_serving_units, food_serving_amount, food_calories, food_fats, food_carbs, food_protein, food_notes;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Open daily macros page layout
        setContentView(R.layout.daily_macros);

        // Add toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Run setDate function to show data in layout
        TextView dateView = findViewById(R.id.textViewCurrentDate);
        setDate(dateView);

        // Run setName function to show name in layout
        TextView nameView = findViewById(R.id.textViewName);
        setName(nameView);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        // Initialize database
        database = new DatabaseHelperClass(this);

        // Initialize arraylists
        food_id = new ArrayList<>();
        food_name = new ArrayList<>();
        food_serving_units = new ArrayList<>();
        food_serving_amount = new ArrayList<>();
        food_calories = new ArrayList<>();
        food_fats = new ArrayList<>();
        food_carbs = new ArrayList<>();
        food_protein = new ArrayList<>();
        food_notes = new ArrayList<>();

        // Get data and store in arraylists
        populateArrayLists();

        // Initialize RecyclerAdapter with ArrayLists
        recyclerAdapter = new RecyclerAdapter(this, food_name, food_serving_units,
                food_serving_amount, food_calories, food_fats, food_carbs, food_protein, food_notes);

        // Set adapter and layout manager
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Listener for mic button
        ImageButton buttonMic = findViewById(R.id.micListenButton);
        buttonMic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Take user to add food to log page
                Intent intent = new Intent (DailyLog.this, AddFoodToLog.class);
                startActivity(intent);
            }
        });
    }

    // Function to retrieve today's date and display it in day-month-year format
    public void setDate (TextView view){

        // https://stackoverflow.com/questions/40310773/android-studio-textview-show-date
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String date = formatter.format(today);
        view.setText(date);
    }

    // Function to retrieve user's name from database
    public void setName (TextView view){

        // https://stackoverflow.com/questions/8939187/how-to-read-data-from-sqlite-database-and-show-it-on-a-list-view

        // Initiate database
        DatabaseHelperClass database = new DatabaseHelperClass(this);

        // Open database
        database.dbOpen();

        // Set cursor equal to results of database rawQuery
        Cursor c = database.getData("SELECT user_name FROM user_details");

        // If cursor is not null, set the view's text to the user's name
        if (c != null ) {

            // Move cursor to first position
            c.moveToFirst();

            // Set name to user's name
            String name = c.getString(c.getColumnIndex("user_name"));
            view.setText(name);

            // Close the cursor
            c.close();
        }
        // Close database
        database.dbClose();
    }

    public void populateArrayLists(){

        database.dbOpen();

        String query = "SELECT * FROM food_log";
        Cursor cursor = database.getData(query);


        if (cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else{
            cursor.moveToFirst();

            while (cursor.moveToNext()){

                String findFood = "SELECT * FROM food_items WHERE food_id = " +
                        cursor.getString(cursor.getColumnIndex("log_food_id"));

                Cursor c = database.getData(findFood);
                c.moveToFirst();

                // Add food id to list
                food_id.add(cursor.getString(cursor.getColumnIndex("log_food_id")));

                // Add food name to list
                String name = c.getString(c.getColumnIndex("food_name"));
                food_name.add(name);

                // Add food serving units to list
                String units = c.getString(c.getColumnIndex("food_serving_units"));
                food_serving_units.add(units);

                // Add food serving amount to list
                String logServingSize = cursor.getString(
                        cursor.getColumnIndex("log_food_serving_size"));
                food_serving_amount.add(logServingSize);

                // Add food calories to list
                String foodItemServingSize = c.getString(
                        c.getColumnIndex("food_serving_amount"));

                Double multiplier = Double.parseDouble(logServingSize) /
                        Double.parseDouble(foodItemServingSize);

                String caloriesInFoodItemServingSize = c.getString(
                        c.getColumnIndex("food_calories"));
                Double foodItemCalories = Double.parseDouble(caloriesInFoodItemServingSize);

                Double logCalories = foodItemCalories * multiplier;

                food_calories.add(String.valueOf(Math.round(logCalories)));

                // Add food fats to list
                String fatsInFoodItemServingSize = c.getString(
                        c.getColumnIndex("food_fats"));
                Double foodItemFats = Double.parseDouble(fatsInFoodItemServingSize);

                Double logFats = foodItemFats * multiplier;

                DecimalFormat df = new DecimalFormat("#.#");

                food_fats.add(String.valueOf(df.format(logFats)));

                // Add food carbs to list
                String carbsInFoodItemServingSize = c.getString(
                        c.getColumnIndex("food_carbs"));
                Double foodItemCarbs = Double.parseDouble(carbsInFoodItemServingSize);

                Double logCarbs = foodItemCarbs * multiplier;

                food_carbs.add(String.valueOf(df.format(logCarbs)));

                // Add food protein to list
                String proteinInFoodItemServingSize = c.getString(
                        c.getColumnIndex("food_protein"));
                Double foodItemProtein = Double.parseDouble(proteinInFoodItemServingSize);

                Double logProtein = foodItemProtein * multiplier;

                food_protein.add(String.valueOf(df.format(logProtein)));

                // Add food notes to list
                String foodNotes = c.getString(c.getColumnIndex("food_notes"));
                if (foodNotes != null){
                    food_notes.add(foodNotes);
                }
                else{
                    food_notes.add("");
                }

                // Close Cursor c
                c.close();
            }
        }
        // Close Cursor cursor
        cursor.close();

        // Close database
        database.dbClose();
    }
}


