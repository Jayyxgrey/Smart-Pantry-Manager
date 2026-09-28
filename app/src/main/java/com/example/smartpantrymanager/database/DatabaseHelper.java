package com.example.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

public class DatabaseHelper extends SQLiteOpenHelper {

    public long addIngredient(String name,
                              double quantity,
                              String unit,
                              String expiryDate) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PANTRY_NAME, name);
        values.put(PANTRY_QUANTITY, quantity);
        values.put(PANTRY_UNIT, unit);
        values.put(PANTRY_EXPIRY, expiryDate);

        return db.insert(
                TABLE_PANTRY,
                null,
                values
        );
    }
    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // Pantry table
    public static final String TABLE_PANTRY = "pantry";
    public static final String PANTRY_ID = "id";
    public static final String PANTRY_NAME = "name";
    public static final String PANTRY_QUANTITY = "quantity";
    public static final String PANTRY_UNIT = "unit";
    public static final String PANTRY_EXPIRY = "expiry_date";

    // Recipes table
    public static final String TABLE_RECIPES = "recipes";
    public static final String RECIPE_ID = "id";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_INSTRUCTIONS = "instructions";

    // Recipe ingredients table
    public static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    public static final String RI_ID = "id";
    public static final String RI_RECIPE_ID = "recipe_id";
    public static final String RI_INGREDIENT_NAME = "ingredient_name";
    public static final String RI_QUANTITY = "quantity";
    public static final String RI_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createPantryTable =
                "CREATE TABLE " + TABLE_PANTRY + " (" +
                        PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        PANTRY_NAME + " TEXT NOT NULL, " +
                        PANTRY_QUANTITY + " REAL NOT NULL, " +
                        PANTRY_UNIT + " TEXT NOT NULL, " +
                        PANTRY_EXPIRY + " TEXT)";

        String createRecipesTable =
                "CREATE TABLE " + TABLE_RECIPES + " (" +
                        RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RECIPE_NAME + " TEXT NOT NULL, " +
                        RECIPE_INSTRUCTIONS + " TEXT NOT NULL)";

        String createRecipeIngredientsTable =
                "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                        RI_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        RI_RECIPE_ID + " INTEGER NOT NULL, " +
                        RI_INGREDIENT_NAME + " TEXT NOT NULL, " +
                        RI_QUANTITY + " REAL NOT NULL, " +
                        RI_UNIT + " TEXT NOT NULL, " +
                        "FOREIGN KEY(" + RI_RECIPE_ID + ") REFERENCES " +
                        TABLE_RECIPES + "(" + RECIPE_ID + "))";

        db.execSQL(createPantryTable);
        db.execSQL(createRecipesTable);
        db.execSQL(createRecipeIngredientsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,
                          int oldVersion,
                          int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_RECIPE_INGREDIENTS);

        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_RECIPES);

        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE_PANTRY);

        onCreate(db);
    }
}