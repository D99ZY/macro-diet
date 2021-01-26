package com.dtriegaardt.macrodiet;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// https://www.javatpoint.com/android-sqlite-tutorial

public class DatabaseHelperClass {

    // Attributes
    private static final String dbName = "MacroDietDB";
    private static final int dbVersion = 113;

    private final Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DatabaseHelperClass(Context ctx){
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context) {
            super(context, dbName, null, dbVersion);
        }

        // onCreate method runs when app is installed
        @Override
        public void onCreate(SQLiteDatabase db){
            // https://www.sqlitetutorial.net/sqlite-java/create-table/
            try{
                // Create database tables

                // Create food item table
                db.execSQL("CREATE TABLE IF NOT EXISTS food_items " +
                        "(food_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "food_name VARCHAR, " +
                        "food_notes VARCHAR, " +
                        "food_serving_units VARCHAR, " +
                        "food_serving_amount DOUBLE, " +
                        "food_calories DOUBLE, " +
                        "food_fats DOUBLE, " +
                        "food_carbs DOUBLE, " +
                        "food_protein DOUBLE);");

                // Create daily food log table
                db.execSQL("CREATE TABLE IF NOT EXISTS food_log " +
                        "(log_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "log_date DATE, " +
                        "log_meal_number INTEGER, " +
                        "log_food_id INTEGER, " +
                        "log_food_serving_size DOUBLE);");

                // Create user details table
                db.execSQL("CREATE TABLE IF NOT EXISTS user_details " +
                        "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "user_name VARCHAR, " +
                        "user_daily_calorie_intake DOUBLE, " +
                        "user_daily_fat_intake DOUBLE, " +
                        "user_daily_carb_intake DOUBLE, " +
                        "user_daily_protein_intake DOUBLE);");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }

        // onUpgrade method is run when app is reopened and version number is different
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            // Drop Tables
            db.execSQL("DROP TABLE IF EXISTS food_items");
            db.execSQL("DROP TABLE IF EXISTS food_log");
            db.execSQL("DROP TABLE IF EXISTS user_details");
            onCreate(db);

            // Logcat Info log
            String TAG = "READ_ME";
            Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
        }
    }
    // Open the database
    public DatabaseHelperClass dbOpen() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Close the database
    public void dbClose(){
        dbHelper.close();
    }

    // Insert function to add data to database
    public void insert(String table, String fields, String values) {
        db.execSQL("INSERT INTO " + table + " (" + fields + ")" + " VALUES (" + values + ")");
    }

    // Getter for SQLiteDatabase
    public SQLiteDatabase getDb(){
        return db;
    }

    public Cursor getData(String query){
        return  db.rawQuery(query, null);
    }

}
