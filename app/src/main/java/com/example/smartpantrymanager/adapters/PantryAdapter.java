package com.example.smartpantrymanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.models.Ingredient;

import java.util.List;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<Ingredient> ingredientList;

    public PantryAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PantryViewHolder holder,
            int position) {

        Ingredient ingredient = ingredientList.get(position);

        holder.textName.setText(ingredient.getName());

        String quantity =
                ingredient.getQuantity() + " " + ingredient.getUnit();

        holder.textQuantity.setText(quantity);

        String expiry =
                "Expires: " + ingredient.getExpiryDate();

        holder.textExpiry.setText(expiry);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public static class PantryViewHolder
            extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textQuantity;
        TextView textExpiry;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            textName =
                    itemView.findViewById(R.id.textIngredientName);

            textQuantity =
                    itemView.findViewById(R.id.textIngredientQuantity);

            textExpiry =
                    itemView.findViewById(R.id.textIngredientExpiry);
        }
    }
}