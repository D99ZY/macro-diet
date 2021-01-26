package com.dtriegaardt.macrodiet;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Stetho is used to view database
        // https://facebook.github.io/stetho/
        Stetho.initializeWithDefaults(this);
        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        // Initialize database
        DatabaseHelperClass database = new DatabaseHelperClass(this);

        // Check how many rows are in the database
        database.dbOpen();
        long rowsOfFoodItems = DatabaseUtils.queryNumEntries(database.getDb(), "food_items");
        long rowsOfUsers = DatabaseUtils.queryNumEntries(database.getDb(), "user_details");
        database.dbClose();

        // If there is no data in the food_items table, populate the database
        // from addAllFoodItems method in AddDataToTables class
        if (rowsOfFoodItems < 1){
            AddDataToTables foodList = new AddDataToTables();
            foodList.addAllFoodItems(database);


            // Testing food log info

            Date today = Calendar.getInstance().getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = formatter.format(today);

            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 1, 100");

            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 7, 100");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 10, 2");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 5, 250");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 14, 40");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 15, 60");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 19, 200");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 17, 80");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 3, 120");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 12, 2");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 16, 12");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 6, 150");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 20, 30");
            foodList.addLogEntry(database, "NULL, '" + date + "', 1, 18, 500");

        }

        // Check if user has entered details, if not then go to user_details page layout
        if (rowsOfUsers < 1){
            Intent intent = new Intent (MainActivity.this, UserDetails.class);
            startActivity(intent);
        }
        // If user details are saved in database, then go to daily_macros page layout
        else{
            Intent intent = new Intent (MainActivity.this, DailyLog.class);
            startActivity(intent);
        }

    }
}
