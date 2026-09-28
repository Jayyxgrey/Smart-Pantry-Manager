package com.example.smartpantrymanager.utils;

import com.example.smartpantrymanager.models.Ingredient;
import com.example.smartpantrymanager.models.RecipeIngredient;

import java.util.List;

public class RecipeMatcher {

    public static boolean canMakeRecipe(
            List<Ingredient> pantry,
            List<RecipeIngredient> requirements) {

        for (RecipeIngredient requirement : requirements) {

            boolean found = false;

            for (Ingredient ingredient : pantry) {

                if (namesMatch(
                        ingredient.getName(),
                        requirement.getIngredientName())) {

                    double pantryAmount =
                            convertToBase(
                                    ingredient.getQuantity(),
                                    ingredient.getUnit()
                            );

                    double requiredAmount =
                            convertToBase(
                                    requirement.getQuantity(),
                                    requirement.getUnit()
                            );

                    if (unitsCompatible(
                            ingredient.getUnit(),
                            requirement.getUnit())
                            && pantryAmount >= requiredAmount) {

                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    private static boolean namesMatch(
            String pantryName,
            String recipeName) {

        String first = normalizeName(pantryName);
        String second = normalizeName(recipeName);

        return first.equals(second);
    }

    private static String normalizeName(String name) {

        String normalized =
                name.trim().toLowerCase();

        // Basic plural handling
        if (normalized.endsWith("oes")) {
            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 2
                    );
        } else if (normalized.endsWith("s")
                && normalized.length() > 1) {

            normalized =
                    normalized.substring(
                            0,
                            normalized.length() - 1
                    );
        }

        return normalized;
    }

    private static boolean unitsCompatible(
            String unit1,
            String unit2) {

        return getUnitCategory(unit1)
                .equals(getUnitCategory(unit2));
    }

    private static String getUnitCategory(String unit) {

        String normalized =
                unit.trim().toLowerCase();

        switch (normalized) {

            case "g":
            case "kg":
                return "weight";

            case "ml":
            case "l":
                return "volume";

            case "item":
            case "items":
                return "item";

            case "slice":
            case "slices":
                return "slice";

            default:
                return normalized;
        }
    }

    private static double convertToBase(
            double quantity,
            String unit) {

        String normalized =
                unit.trim().toLowerCase();

        switch (normalized) {

            case "kg":
                return quantity * 1000;

            case "l":
                return quantity * 1000;

            default:
                return quantity;
        }
    }
}