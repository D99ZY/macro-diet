package com.dtriegaardt.macrodiet;

public class AddDataToTables {

    // Add all food items to database
    public void addAllFoodItems(DatabaseHelperClass database){

        // Open database
        database.dbOpen();

        // List of fields
        String foodFields = "food_id, food_name, food_notes, food_serving_units, food_serving_amount, food_calories, food_fats, food_carbs, food_protein";

        // Insert statements below

        // Fruit and Vegetables
        database.insert("food_items", foodFields, "NULL, 'Kiwi', NULL, 'Serving', 1, 42, 0.4, 10, 0.8");
        database.insert("food_items", foodFields, "NULL, 'Pineapple', NULL, 'g', 100, 50, 0.1, 13, 0.5");
        database.insert("food_items", foodFields, "NULL, 'Strawberries', NULL, 'g', 100, 32, 0.3, 7.7, 0.7");
        database.insert("food_items", foodFields, "NULL, 'Pineapple Juice', 'With Bits', 'ml', 100, 42, 0, 10, 0.5");
        database.insert("food_items", foodFields, "NULL, 'Guava Juice', NULL, 'ml', 100, 23, 0, 5.3, 0");
        database.insert("food_items", foodFields, "NULL, 'Sweet Potato', NULL, 'g', 100, 87, 0.3, 21.3, 1.2");

        // Meat and Fish
        database.insert("food_items", foodFields, "NULL, 'Tuna in Brine', 'Drained', 'g', 100, 113, 0.5, 0, 27");
        database.insert("food_items", foodFields, "NULL, 'Ribeye Steak', NULL, 'g', 100, 155, 7, 0, 22.6");
        database.insert("food_items", foodFields, "NULL, 'Salmon Fillet', 'Skinless', 'g', 100, 209, 12, 0, 25.3");
        database.insert("food_items", foodFields, "NULL, 'Sausage', NULL, 'Serving', 1, 190, 15, 4.2, 9.5");
        database.insert("food_items", foodFields, "NULL, 'Smoked Salmon', NULL, 'g', 100, 185, 9.9, 1.5, 22.3");

        // Grains and Cereals
        database.insert("food_items", foodFields, "NULL, 'Bread', 'White', 'Serving', 1, 108, 1, 20.1, 3.7");
        database.insert("food_items", foodFields, "NULL, 'Spaghetti', 'Cooked Weight', 'g', 100, 160, 0.7, 32.5, 5.1");
        database.insert("food_items", foodFields, "NULL, 'Oats', 'Rolled', 'g', 100, 374, 8, 60, 11");
        database.insert("food_items", foodFields, "NULL, 'Special K', NULL, 'g', 100, 375, 1.5, 79, 9");
        database.insert("food_items", foodFields, "NULL, 'Cracker', 'Jacobs', 'Serving', 1, 34, 1.3, 4.5, 0.7");

        // Dairy
        database.insert("food_items", foodFields, "NULL, 'Cheddar', 'Medium', 'g', 100, 416, 34.9, 0.1, 25.4");
        database.insert("food_items", foodFields, "NULL, 'Milk', 'Full Fat', 'ml', 100, 66, 3.7, 4.7, 3.5");
        database.insert("food_items", foodFields, "NULL, 'Greek Yogurt', NULL, 'g', 100, 120, 9.2, 5.3, 4.1");
        database.insert("food_items", foodFields, "NULL, 'Butter', 'Unsalted', 'g', 100, 706, 78, 0.6, 0.5");


        // Close database
        database.dbClose();
    }

    // Add user details to database
    public void addUser(DatabaseHelperClass database, String values){

        // Open database
        database.dbOpen();

        // List of fields
        String userFields = "user_id, user_name, user_daily_calorie_intake, " +
                "user_daily_fat_intake, user_daily_carb_intake, user_daily_protein_intake";

        // Insert statement
        database.insert("user_details", userFields, values);

        // Close database
        database.dbClose();
    }

    // Add food log details to database
    public void addLogEntry(DatabaseHelperClass database, String values){

        // Open database
        database.dbOpen();

        // List of fields
        String userFields = "log_id, log_date, log_meal_number, log_food_id, log_food_serving_size";

        // Insert statement
        database.insert("food_log", userFields, values);

        // Close database
        database.dbClose();
    }
}

