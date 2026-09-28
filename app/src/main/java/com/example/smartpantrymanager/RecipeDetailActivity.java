package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail);

        TextView textName =
                findViewById(R.id.textDetailRecipeName);

        TextView textInstructions =
                findViewById(R.id.textRecipeInstructions);

        String name =
                getIntent().getStringExtra("recipe_name");

        String instructions =
                getIntent().getStringExtra("recipe_instructions");

        textName.setText(name);
        textInstructions.setText(instructions);
    }
}