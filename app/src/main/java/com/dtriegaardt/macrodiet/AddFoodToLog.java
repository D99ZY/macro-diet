package com.dtriegaardt.macrodiet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFoodToLog extends AppCompatActivity {

    TextView inputSpeech;
    ArrayList<String> speechArrayList;

    DatabaseHelperClass database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Open daily macros page layout
        setContentView(R.layout.food_log_mic);

        // Add toolbar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Initiate database
        database = new DatabaseHelperClass(this);

        speechArrayList = new ArrayList<>();

        // Set text view to speech input
        inputSpeech = findViewById(R.id.textViewMicSpeech);

        // Start Recording Speech
        // https://towardsdatascience.com/creating-a-voice-recognition-calculator-android-app-869a515bf6d1

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        startActivityForResult(intent, 10);

        // Three buttons functions below

        // Listener for add food button
        Button buttonAddFood = findViewById(R.id.buttonAddFood);
        buttonAddFood.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Run function findFoodItemId  to get food id and food quantity
                String[] foodId = new String[2];
                foodId[0] = findFoodItemID(inputSpeech.getText().toString())[0];
                foodId[1] = findFoodItemID(inputSpeech.getText().toString())[1];

                // Convert food id from String to int
                int id = Integer.parseInt(foodId[0]);
                // Convert food id from String to double
                double quantity = Double.parseDouble(foodId[1]);

                // Get today's date in yyyy-MM-dd format
                Date today = Calendar.getInstance().getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String date = formatter.format(today);

                // Initialize AddDataToTable object
                AddDataToTables addFoodToLog = new AddDataToTables();

                // Add food to food log table in database
                addFoodToLog.addLogEntry(database, "NULL, '" + date + "', 1, " + id + ", " + quantity);

                // Go back to daily log page
                Intent intent = new Intent (AddFoodToLog.this, DailyLog.class);
                startActivity(intent);
            }
        });

        // Listener for try again button
        Button buttonTryAgain = findViewById(R.id.buttonTryAgain);
        buttonTryAgain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Restart page
                Intent intent = new Intent(getIntent());
                finish();
                startActivity(intent);
            }
        });

        // Listener for cancel button
        Button buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // Take user to daily log page
                Intent intent = new Intent (AddFoodToLog.this, DailyLog.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 10:

                    // Get speech array from voice intent
                    speechArrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    inputSpeech.setText(speechArrayList.get(0));

                    break;
            }
        } else {
            Toast.makeText(this, "Failed to recognize speech!", Toast.LENGTH_LONG).show();
        }
    }

    private String[] findFoodItemID(String speechResults){

        // Initialize String array and set default values
        String[] retVal = new String[2];
        retVal[0] = "1";
        retVal[1] = "10";

        // Store each individual word from speechResults in speechArrayList
        ArrayList<String> speechArrayList =
                new ArrayList<String>(Arrays.asList(speechResults.split(" ")));

        // Open the database
        database.dbOpen();

        // Set cursor equal to results of database query
        Cursor cursor = database.getData("SELECT * FROM food_items");


        // Iterate through each element in ArrayList
        for (String word : speechArrayList){

            // If word is all letters but not equal to "one" or "two"
            if (word.matches("[a-zA-Z]+") &&
                    !word.equalsIgnoreCase("one") &&
                    !word.equalsIgnoreCase("two")) {

                // If cursor is at first position, enter do while loop
                if (cursor.moveToFirst()) {

                    // Iterate through each food name in database
                    do {
                        String currentDbFoodName =
                                cursor.getString(cursor.getColumnIndex("food_name"));
                        String currentDbFoodId =
                                cursor.getString(cursor.getColumnIndex("food_id"));

                        // If the word is equal to the food name
                        if (word.equalsIgnoreCase(currentDbFoodName) ||
                                word.equalsIgnoreCase(currentDbFoodName + "s")) {

                            // Set the first element in the array to be
                            // returned to id of the food in the database
                            retVal[0] = currentDbFoodId;
                        }

                    } while (cursor.moveToNext());
                }
            }

            // If word contains digits or if word is "one" or "two"
            else{
                if(word.equalsIgnoreCase("one")){

                    // Set the second element in the array to be returned to 1
                    retVal[1] = "1";
                }
                else if(word.equalsIgnoreCase("two")){

                    // Set the second element in the array to be returned to 2
                    retVal[1] = "2";
                }
                else{

                    // Remove any letters attached to digits
                    String number = word;
                    number = number.replaceAll("[^0-9.]", "");

                    // Set the second element in the array to be returned to the number given
                    retVal[1] = number;
                }
            }
        }

        // Close the cursor and the database
        cursor.close();
        database.dbClose();

        // Return the array
        return retVal;
    }

}
