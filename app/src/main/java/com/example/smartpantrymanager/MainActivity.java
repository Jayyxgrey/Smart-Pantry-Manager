package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapters.PantryAdapter;
import com.example.smartpantrymanager.models.Ingredient;

import java.util.ArrayList;
import java.util.List;


import com.example.smartpantrymanager.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private PantryAdapter pantryAdapter;
    private List<Ingredient> ingredientList;
    private Button buttonAddIngredient;
    private Button buttonSuggestedRecipes;
    private DatabaseHelper databaseHelper;
    private Button buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect MainActivity to activity_main.xml
        setContentView(R.layout.activity_main);

        buttonSuggestedRecipes =
                findViewById(
                        R.id.buttonSuggestedRecipes
                );

        buttonSuggestedRecipes.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            MainActivity.this,
                            SuggestedRecipesActivity.class
                    );

            startActivity(intent);
        });

        // Connect Java variables to XML views
        recyclerPantry = findViewById(R.id.recyclerPantry);
        buttonAddIngredient = findViewById(R.id.buttonAddIngredient);

        buttonSettings = findViewById(R.id.buttonSettings);

        buttonSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    SettingsActivity.class
            );

            startActivity(intent);
        });

        databaseHelper = new DatabaseHelper(this);
        // Set up RecyclerView
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );
        ingredientList = new ArrayList<>();
        ingredientList.addAll(databaseHelper.getAllIngredients());



        // Connect list to RecyclerView
        pantryAdapter = new PantryAdapter(
                ingredientList,
                new PantryAdapter.OnIngredientActionListener() {

                    @Override
                    public void onEdit(Ingredient ingredient) {

                        Intent intent = new Intent(
                                MainActivity.this,
                                AddEditIngredientActivity.class
                        );

                        intent.putExtra(
                                "ingredient_id",
                                ingredient.getId()
                        );

                        intent.putExtra(
                                "ingredient_name",
                                ingredient.getName()
                        );

                        intent.putExtra(
                                "ingredient_quantity",
                                ingredient.getQuantity()
                        );

                        intent.putExtra(
                                "ingredient_unit",
                                ingredient.getUnit()
                        );

                        intent.putExtra(
                                "ingredient_expiry",
                                ingredient.getExpiryDate()
                        );

                        startActivity(intent);
                    }

                    @Override
                    public void onDelete(Ingredient ingredient) {

                        databaseHelper.deleteIngredient(
                                ingredient.getId()
                        );

                        ingredientList.clear();

                        ingredientList.addAll(
                                databaseHelper.getAllIngredients()
                        );

                        pantryAdapter.notifyDataSetChanged();
                    }
                }
        );
        recyclerPantry.setAdapter(pantryAdapter);

        // Open Add Ingredient screen
        buttonAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        if (databaseHelper != null &&
                ingredientList != null &&
                pantryAdapter != null) {

            ingredientList.clear();

            ingredientList.addAll(
                    databaseHelper.getAllIngredients()
            );

            pantryAdapter.notifyDataSetChanged();
        }
    }
}