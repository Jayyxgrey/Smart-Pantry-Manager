package com.example.smartpantrymanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.models.Ingredient;
import android.widget.Button;

import java.util.List;

public class PantryAdapter
        extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<Ingredient> ingredientList;

    private final OnIngredientActionListener listener;

    public interface OnIngredientActionListener {

        void onEdit(Ingredient ingredient);

        void onDelete(Ingredient ingredient);
    }

    public PantryAdapter(
            List<Ingredient> ingredientList,
            OnIngredientActionListener listener) {

        this.ingredientList = ingredientList;
        this.listener = listener;
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

        holder.buttonEdit.setOnClickListener(
                v -> listener.onEdit(ingredient)
        );

        holder.buttonDelete.setOnClickListener(
                v -> listener.onDelete(ingredient)
        );
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
        Button buttonEdit;
        Button buttonDelete;


        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            textName =
                    itemView.findViewById(R.id.textIngredientName);

            textQuantity =
                    itemView.findViewById(R.id.textIngredientQuantity);

            textExpiry =
                    itemView.findViewById(R.id.textIngredientExpiry);
            buttonEdit =
                    itemView.findViewById(R.id.buttonEdit);

            buttonDelete =
                    itemView.findViewById(R.id.buttonDelete);
        }
    }
}