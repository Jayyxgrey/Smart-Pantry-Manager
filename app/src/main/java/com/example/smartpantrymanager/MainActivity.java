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

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private PantryAdapter pantryAdapter;
    private List<Ingredient> ingredientList;
    private Button buttonAddIngredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect MainActivity to activity_main.xml
        setContentView(R.layout.activity_main);

        // Connect Java variables to XML views
        recyclerPantry = findViewById(R.id.recyclerPantry);
        buttonAddIngredient = findViewById(R.id.buttonAddIngredient);

        // Set up RecyclerView
        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        ingredientList = new ArrayList<>();

        // Temporary test data
        ingredientList.add(
                new Ingredient(
                        1,
                        "Milk",
                        500,
                        "ml",
                        "2026-10-05"
                )
        );

        ingredientList.add(
                new Ingredient(
                        2,
                        "Eggs",
                        6,
                        "item",
                        "2026-10-02"
                )
        );

        // Connect list to RecyclerView
        pantryAdapter = new PantryAdapter(ingredientList);
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
}