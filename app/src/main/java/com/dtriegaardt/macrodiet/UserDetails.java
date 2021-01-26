package com.dtriegaardt.macrodiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class UserDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Open user details page layout
        setContentView(R.layout.user_details);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Listener for user details submit button
        Button buttonSubmitDetails = findViewById(R.id.buttonSubmitUserDetails);
        buttonSubmitDetails.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                submitUserDetails();
            }
        });
    }

    public void submitUserDetails(){

        EditText editTextName = findViewById(R.id.plainTextEnterName);
        String name = editTextName.getText().toString();

        EditText editTextCalories = findViewById(R.id.plainTextEnterCalories);
        String calories = editTextCalories.getText().toString();

        EditText editTextFat = findViewById(R.id.plainTextEnterFat);
        String fat = editTextFat.getText().toString();

        EditText editTextCarbs = findViewById(R.id.plainTextEnterCarbs);
        String carbs = editTextCarbs.getText().toString();

        EditText editTextProtein = findViewById(R.id.plainTextEnterProtein);
        String protein = editTextProtein.getText().toString();

        // If any input fields are empty, alert user to enter all fields
        if (name.isEmpty() || calories.isEmpty() || fat.isEmpty() ||
                carbs.isEmpty() || protein.isEmpty()) {
            Toast.makeText(this, "Enter all fields", Toast.LENGTH_LONG).show();
        }
        else{
            // Initialize database connection
            DatabaseHelperClass database = new DatabaseHelperClass(this);

            // Add user to database
            AddDataToTables users = new AddDataToTables();
            String values = "NULL, '" + name + "', " + calories + ", " +
                    fat + ", " + carbs + ", " + protein;
            users.addUser(database, values);

            // Take user to daily log page
            Intent intent = new Intent (UserDetails.this, DailyLog.class);
            startActivity(intent);
        }
    }
}


