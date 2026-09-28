package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.database.DatabaseHelper;

public class AddEditIngredientActivity
        extends AppCompatActivity {

    private EditText editIngredientName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiryDate;

    private Button buttonSaveIngredient;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_add_edit_ingredient
        );

        editIngredientName =
                findViewById(R.id.editIngredientName);

        editQuantity =
                findViewById(R.id.editQuantity);

        editUnit =
                findViewById(R.id.editUnit);

        editExpiryDate =
                findViewById(R.id.editExpiryDate);

        buttonSaveIngredient =
                findViewById(R.id.buttonSaveIngredient);

        databaseHelper =
                new DatabaseHelper(this);

        buttonSaveIngredient.setOnClickListener(
                v -> saveIngredient()
        );
    }

    private void saveIngredient() {

        String name =
                editIngredientName.getText()
                        .toString().trim();

        String quantityText =
                editQuantity.getText()
                        .toString().trim();

        String unit =
                editUnit.getText()
                        .toString().trim();

        String expiryDate =
                editExpiryDate.getText()
                        .toString().trim();

        // Validate name
        if (name.isEmpty()) {

            editIngredientName.setError(
                    "Ingredient name is required"
            );

            editIngredientName.requestFocus();
            return;
        }

        // Validate quantity
        if (quantityText.isEmpty()) {

            editQuantity.setError(
                    "Quantity is required"
            );

            editQuantity.requestFocus();
            return;
        }

        double quantity;

        try {

            quantity =
                    Double.parseDouble(quantityText);

        } catch (NumberFormatException e) {

            editQuantity.setError(
                    "Enter a valid quantity"
            );

            return;
        }

        if (quantity <= 0) {

            editQuantity.setError(
                    "Quantity must be greater than 0"
            );

            return;
        }

        // Validate unit
        if (unit.isEmpty()) {

            editUnit.setError(
                    "Unit is required"
            );

            editUnit.requestFocus();
            return;
        }

        // Validate expiry
        if (expiryDate.isEmpty()) {

            editExpiryDate.setError(
                    "Expiry date is required"
            );

            editExpiryDate.requestFocus();
            return;
        }

        long result =
                databaseHelper.addIngredient(
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

        if (result != -1) {

            Toast.makeText(
                    this,
                    "Ingredient added",
                    Toast.LENGTH_SHORT
            ).show();

            finish();

        } else {

            Toast.makeText(
                    this,
                    "Failed to add ingredient",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}