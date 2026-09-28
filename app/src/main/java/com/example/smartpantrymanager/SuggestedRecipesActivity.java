package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.adapters.RecipeAdapter;
import com.example.smartpantrymanager.database.DatabaseHelper;
import com.example.smartpantrymanager.models.Ingredient;
import com.example.smartpantrymanager.models.Recipe;
import com.example.smartpantrymanager.models.RecipeIngredient;
import com.example.smartpantrymanager.utils.RecipeMatcher;

import java.util.ArrayList;
import java.util.List;

public class SuggestedRecipesActivity
        extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_suggested_recipes
        );

        RecyclerView recyclerRecipes =
                findViewById(R.id.recyclerRecipes);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        DatabaseHelper databaseHelper =
                new DatabaseHelper(this);

        List<Ingredient> pantry =
                databaseHelper.getAllIngredients();

        List<Recipe> allRecipes =
                databaseHelper.getAllRecipes();

        List<Recipe> matchingRecipes =
                new ArrayList<>();

        for (Recipe recipe : allRecipes) {

            List<RecipeIngredient> requirements =
                    databaseHelper.getRecipeIngredients(
                            recipe.getId()
                    );

            if (RecipeMatcher.canMakeRecipe(
                    pantry,
                    requirements)) {

                matchingRecipes.add(recipe);
            }
        }

        RecipeAdapter adapter =
                new RecipeAdapter(
                        matchingRecipes,
                        recipe -> {

                            Intent intent =
                                    new Intent(
                                            this,
                                            RecipeDetailActivity.class
                                    );

                            intent.putExtra(
                                    "recipe_id",
                                    recipe.getId()
                            );

                            intent.putExtra(
                                    "recipe_name",
                                    recipe.getName()
                            );

                            intent.putExtra(
                                    "recipe_instructions",
                                    recipe.getInstructions()
                            );

                            startActivity(intent);
                        }
                );

        recyclerRecipes.setAdapter(adapter);
    }
}