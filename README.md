# Smart Pantry Manager

## Overview

Smart Pantry Manager is an Android application developed using Java in Android Studio.

The application helps users keep track of the ingredients available in their pantry and suggests recipes that can be prepared using those ingredients.

Users can add, view, edit and delete pantry ingredients. Each ingredient contains information such as its name, quantity, unit of measurement and expiry date.

The application also contains a collection of predefined recipes. The recipe-matching system compares the ingredients required by each recipe with the ingredients currently available in the user's pantry.

---

## Features

The Smart Pantry Manager includes the following features:

- Add pantry ingredients
- View pantry ingredients
- Edit existing ingredients
- Delete ingredients
- Store ingredient quantities
- Store measurement units
- Store expiry dates
- Input validation
- Persistent SQLite database storage
- Suggested recipes
- Recipe quantity matching
- Basic unit conversion
- Recipe details
- Settings screen
- RecyclerView for displaying pantry items and recipes

---

## Technologies Used

The application was developed using:

- Android Studio
- Java
- XML
- SQLite
- SQLiteOpenHelper
- RecyclerView
- Git
- GitHub

---

## Database

Smart Pantry Manager uses SQLite for local data persistence.

The database is managed through a `DatabaseHelper` class that extends `SQLiteOpenHelper`.

The application contains three main database tables:

### Pantry

The `pantry` table stores the user's available ingredients.

It contains:

- ID
- Ingredient name
- Quantity
- Unit
- Expiry date

### Recipes

The `recipes` table stores the recipes available in the application.

It contains:

- Recipe ID
- Recipe name
- Instructions

### Recipe Ingredients

The `recipe_ingredients` table stores the ingredients required for each recipe.

It contains:

- ID
- Recipe ID
- Ingredient name
- Required quantity
- Unit

The Recipe ID connects each required ingredient to its corresponding recipe.

---

## Recipe Matching

The application uses a recipe-matching system to determine which recipes can be prepared using the ingredients currently available in the pantry.

A recipe is suggested only when all of its required ingredients are available in sufficient quantities.

For example, if a recipe requires:

- 2 eggs
- 40 g cheese

and the user's pantry contains:

- 6 eggs
- 200 g cheese

the recipe can be suggested.

However, if the user only has 1 egg, the recipe will not qualify because the required quantity is not available.

The application also supports basic unit conversion, including:

- Kilograms to grams
- Litres to millilitres

Basic singular and plural ingredient-name matching is also included.

---

## Application Screens

The application contains the following main screens:

### My Pantry

The main screen displays all ingredients currently stored in the user's pantry.

From this screen, the user can:

- Add an ingredient
- Edit an ingredient
- Delete an ingredient
- Open Suggested Recipes
- Open Settings

### Add/Edit Ingredient

This screen allows the user to enter:

- Ingredient name
- Quantity
- Unit
- Expiry date

Input validation prevents invalid or incomplete information from being saved.

### Suggested Recipes

This screen displays recipes that can be prepared using the ingredients currently available in the user's pantry.

### Recipe Detail

The Recipe Detail screen displays the selected recipe's name and preparation instructions.

### Settings

The Settings screen provides a dedicated area for application settings and future configuration options.

---

## Project Structure

The main project structure includes:

    com.example.smartpantrymanager
    |
    |-- adapters
    |   |-- PantryAdapter.java
    |   |-- RecipeAdapter.java
    |
    |-- database
    |   |-- DatabaseHelper.java
    |
    |-- models
    |   |-- Ingredient.java
    |   |-- Recipe.java
    |   |-- RecipeIngredient.java
    |
    |-- utils
    |   |-- RecipeMatcher.java
    |
    |-- MainActivity.java
    |-- AddEditIngredientActivity.java
    |-- SuggestedRecipesActivity.java
    |-- RecipeDetailActivity.java
    |-- SettingsActivity.java

---

## Installation and Setup

To run the Smart Pantry Manager application:

1. Clone or download this repository.
2. Open Android Studio.
3. Select **Open**.
4. Select the Smart Pantry Manager project folder.
5. Wait for Gradle to finish syncing.
6. Ensure the required Android SDK is installed.
7. Start an Android emulator or connect a compatible Android device.
8. Click **Run** in Android Studio.
9. Select the emulator or connected device.
10. The Smart Pantry Manager application should launch.

---

## Input Validation

The application performs validation before saving pantry ingredients.

Examples include:

- Ingredient name cannot be empty.
- Quantity cannot be empty.
- Quantity must contain a valid number.
- Quantity must be greater than zero.
- Unit cannot be empty.
- Expiry date cannot be empty.

This prevents invalid pantry information from being stored in the database.

---

## CRUD Operations

The application implements CRUD operations for pantry ingredients.

**Create:**  
Users can add new ingredients to their pantry.

**Read:**  
Stored ingredients are retrieved from SQLite and displayed using a RecyclerView.

**Update:**  
Users can edit existing ingredient information.

**Delete:**  
Users can remove ingredients from their pantry.

---

## Git Version Control

Git was used throughout the development of the application.

The project was developed incrementally using meaningful commits, including:

1. Initial Smart Pantry Manager project
2. Add ingredient and recipe models
3. Implement SQLite database schema
4. Add pantry list and RecyclerView adapter
5. Implement add ingredient functionality
6. Implement pantry edit and delete
7. Add seeded recipe collection
8. Implement strict recipe matching
9. Add recipe screens and settings
10. Complete testing and documentation

This commit history demonstrates the incremental development of the application.

---

## Future Improvements

Possible future improvements include:

- Expiry-date notifications
- Date picker for selecting expiry dates
- Recipe images
- Additional recipes
- Recipe categories
- Preparation times
- Improved Material Design interface
- Search and filtering
- More advanced unit conversions
- Sorting ingredients by expiry date
- Cloud backup and synchronisation

---

## Author

Smart Pantry Manager

Mobile Application Development Project
