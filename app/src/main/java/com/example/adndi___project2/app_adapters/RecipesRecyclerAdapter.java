package com.example.adndi___project2.app_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeData;

import java.util.List;

/**
 * Classe do Adapter que irá popular o RecyclerView do Fragment de receitas
 */
public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    // ClickHandler para definirmos a interface
    private final RecyclerAdapterOnClickHandler mClickHandler;

    private List<RecipeData> mRecipeList;

    // contexto atual
    private Context context;

    public RecipesRecyclerAdapter(RecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list_content, viewGroup, false);
        return new RecipesViewHolder(view, mClickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder viewHolder, int position) {
        if (mRecipeList.get(position) != null) {
            String recipeName = mRecipeList.get(position).getRecipeName();
            String recipeImageUrl = mRecipeList.get(position).getRecipeImageUrl();

            viewHolder.mRecipeNameTextView.setText(recipeName);
            Glide.with(context).load(recipeImageUrl).into(viewHolder.mRecipeImageView);
        }
    }

    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    /**
     * setRecipeData define a lista de Receitas e avisa a mudança de dados
     *
     * @param recipeList lista que utilizaremos
     */
    public void setRecipeData(List<RecipeData> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

}
