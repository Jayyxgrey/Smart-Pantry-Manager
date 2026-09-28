package com.example.smartpantrymanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.smartpantrymanager.models.Ingredient;

import java.util.ArrayList;
import java.util.List;
public class DatabaseHelper extends SQLiteOpenHelper {

    public int updateIngredient(int id,
                                String name,
                                double quantity,
                                String unit,
                                String expiryDate) {

        SQLiteDatabase db =
                getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(PANTRY_NAME, name);
        values.put(PANTRY_QUANTITY, quantity);
        values.put(PANTRY_UNIT, unit);
        values.put(PANTRY_EXPIRY, expiryDate);

        return db.update(
                TABLE_PANTRY,
                values,
                PANTRY_ID + " = ?",
                new String[]{
                        String.valueOf(id)
                }
        );
    }
    public int deleteIngredient(int id) {

        SQLiteDatabase db =
                getWritableDatabase();

        return db.delete(
                TABLE_PANTRY,
                PANTRY_ID + " = ?",
                new String[]{
                        String.valueOf(id)
                }
        );
    }
    public List<Ingredient> getAllIngredients() {

        List<Ingredient> ingredientList =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                PANTRY_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_UNIT
                                )
                        );

                String expiry =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_EXPIRY
                                )
                        );

                Ingredient ingredient =
                        new Ingredient(
                                id,
                                name,
                                quantity,
                                unit,
                                expiry
                        );

                ingredientList.add(ingredient);

            } while (cursor.moveToNext());
        }

        cursor.close();

        return ingredientList;
    }
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
        seedRecipes(db);
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
    private long addRecipe(SQLiteDatabase db,
                           String name,
                           String instructions) {

        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, name);
        values.put(RECIPE_INSTRUCTIONS, instructions);

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    private void addRecipeIngredient(SQLiteDatabase db,
                                     long recipeId,
                                     String ingredientName,
                                     double quantity,
                                     String unit) {

        ContentValues values = new ContentValues();

        values.put(RI_RECIPE_ID, recipeId);
        values.put(RI_INGREDIENT_NAME, ingredientName);
        values.put(RI_QUANTITY, quantity);
        values.put(RI_UNIT, unit);

        db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }
    private void seedRecipes(SQLiteDatabase db) {

        long recipeId;

        // 1. Scrambled Eggs
        recipeId = addRecipe(
                db,
                "Scrambled Eggs",
                "Beat the eggs and cook them in a pan."
        );

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");


        // 2. Cheese Omelette
        recipeId = addRecipe(
                db,
                "Cheese Omelette",
                "Beat the eggs, add cheese and cook in a pan."
        );

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");


        // 3. Tomato Omelette
        recipeId = addRecipe(
                db,
                "Tomato Omelette",
                "Beat eggs, add chopped tomato and cook."
        );

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");


        // 4. Cheese Toast
        recipeId = addRecipe(
                db,
                "Cheese Toast",
                "Place cheese on bread and toast."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");


        // 5. Butter Toast
        recipeId = addRecipe(
                db,
                "Butter Toast",
                "Toast the bread and spread with butter."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "butter", 20, "g");


        // 6. Tomato Toast
        recipeId = addRecipe(
                db,
                "Tomato Toast",
                "Toast bread and top with sliced tomato."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");


        // 7. Egg on Toast
        recipeId = addRecipe(
                db,
                "Egg on Toast",
                "Cook the eggs and serve them on toast."
        );

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");


        // 8. Cheese Sandwich
        recipeId = addRecipe(
                db,
                "Cheese Sandwich",
                "Place cheese between two slices of bread."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");


        // 9. Tomato Cheese Sandwich
        recipeId = addRecipe(
                db,
                "Tomato Cheese Sandwich",
                "Add tomato and cheese between bread slices."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");


        // 10. Butter Pasta
        recipeId = addRecipe(
                db,
                "Butter Pasta",
                "Cook pasta and stir through butter."
        );

        addRecipeIngredient(db, recipeId,
                "pasta", 100, "g");

        addRecipeIngredient(db, recipeId,
                "butter", 20, "g");


        // 11. Cheese Pasta
        recipeId = addRecipe(
                db,
                "Cheese Pasta",
                "Cook pasta and mix with grated cheese."
        );

        addRecipeIngredient(db, recipeId,
                "pasta", 100, "g");

        addRecipeIngredient(db, recipeId,
                "cheese", 50, "g");


        // 12. Tomato Pasta
        recipeId = addRecipe(
                db,
                "Tomato Pasta",
                "Cook pasta and combine with chopped tomato."
        );

        addRecipeIngredient(db, recipeId,
                "pasta", 100, "g");

        addRecipeIngredient(db, recipeId,
                "tomato", 2, "item");


        // 13. Egg Fried Rice
        recipeId = addRecipe(
                db,
                "Egg Fried Rice",
                "Cook the egg and combine with cooked rice."
        );

        addRecipeIngredient(db, recipeId,
                "rice", 150, "g");

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");


        // 14. Banana Milkshake
        recipeId = addRecipe(
                db,
                "Banana Milkshake",
                "Blend banana and milk until smooth."
        );

        addRecipeIngredient(db, recipeId,
                "banana", 1, "item");

        addRecipeIngredient(db, recipeId,
                "milk", 250, "ml");


        // 15. Banana Toast
        recipeId = addRecipe(
                db,
                "Banana Toast",
                "Toast bread and top with sliced banana."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "banana", 1, "item");


        // 16. Banana Pancakes
        recipeId = addRecipe(
                db,
                "Banana Pancakes",
                "Mash banana, combine with eggs and cook as pancakes."
        );

        addRecipeIngredient(db, recipeId,
                "banana", 1, "item");

        addRecipeIngredient(db, recipeId,
                "egg", 2, "item");


        // 17. Tomato Cheese Pasta
        recipeId = addRecipe(
                db,
                "Tomato Cheese Pasta",
                "Cook pasta and mix with tomato and cheese."
        );

        addRecipeIngredient(db, recipeId,
                "pasta", 100, "g");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");


        // 18. Egg and Cheese Sandwich
        recipeId = addRecipe(
                db,
                "Egg and Cheese Sandwich",
                "Cook the egg and place it with cheese between bread."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "egg", 1, "item");

        addRecipeIngredient(db, recipeId,
                "cheese", 30, "g");


        // 19. Tomato Egg Sandwich
        recipeId = addRecipe(
                db,
                "Tomato Egg Sandwich",
                "Place cooked egg and tomato between bread slices."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "egg", 1, "item");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");


        // 20. Cheese and Tomato Toast
        recipeId = addRecipe(
                db,
                "Cheese and Tomato Toast",
                "Top bread with cheese and tomato, then toast."
        );

        addRecipeIngredient(db, recipeId,
                "bread", 2, "slice");

        addRecipeIngredient(db, recipeId,
                "cheese", 40, "g");

        addRecipeIngredient(db, recipeId,
                "tomato", 1, "item");
    }
}