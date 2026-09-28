package com.example.smartpantrymanager;

import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerPantry = findViewById(R.id.recyclerPantry);

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

        pantryAdapter = new PantryAdapter(ingredientList);

        recyclerPantry.setAdapter(pantryAdapter);
    }
}