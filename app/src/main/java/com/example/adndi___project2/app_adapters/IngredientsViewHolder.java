package com.example.adndi___project2.app_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.adndi___project2.R;


/**
 * Classe de ViewHolder que irá definir as view da RecyclerView do Fragment de ingredients
 */
public class IngredientsViewHolder extends RecyclerView.ViewHolder {

    final TextView mIngredientNameTextView;
    final TextView mIngredientQuantityTextView;
    final TextView mIngredientMeasureTextView;

    IngredientsViewHolder(View view) {
        super(view);
        mIngredientNameTextView = view.findViewById(R.id.tv_ingredient_name);
        mIngredientQuantityTextView = view.findViewById(R.id.tv_ingredient_quantity);
        mIngredientMeasureTextView = view.findViewById(R.id.tv_ingredient_measure);
    }

}
