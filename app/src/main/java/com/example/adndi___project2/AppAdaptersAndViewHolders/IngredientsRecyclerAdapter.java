package com.example.adndi___project2.AppAdaptersAndViewHolders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adndi___project2.DataBase.RecipeIngredients;
import com.example.adndi___project2.R;

import java.util.List;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsViewHolder> {


    private List<RecipeIngredients> mIngredientsList;

    /**
     * Construtor da classe
     */
    public IngredientsRecyclerAdapter() {
    }

    /**
     * onCreateViewHolder, cria os varios viewHolders que irão
     *
     * @param viewGroup o ViewGroup que contem esta viewHolder
     * @param i         tipo do view utilizado, neste caso não é utilizado, pois só temos 1 tipo de view
     * @return o ViewHolder criado
     */
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // contexto atual
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ingredients_list_content, viewGroup, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int position) {
        if (mIngredientsList.get(position) != null) {
            String ingredientName = mIngredientsList.get(position).getIngredientName();
            String ingredientQuantity = String.valueOf(mIngredientsList.get(position).getIngredientQuantity());
            String ingredientMeasure = mIngredientsList.get(position).getIngredientMeasure();

            ingredientsViewHolder.mIngredientNameTextView.setText(ingredientName);
            ingredientsViewHolder.mIngredientQuantityTextView.setText(ingredientQuantity);
            ingredientsViewHolder.mIngredientMeasureTextView.setText(ingredientMeasure);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mIngredientsList) return 0;
        return mIngredientsList.size();
    }

    public void setIngredientsList(List<RecipeIngredients> ingredientsList) {
        mIngredientsList = ingredientsList;
        notifyDataSetChanged();
    }
}
