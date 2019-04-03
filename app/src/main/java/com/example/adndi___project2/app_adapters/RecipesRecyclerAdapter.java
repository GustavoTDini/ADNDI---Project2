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

public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesViewHolder> {

    // ClickHandler para definirmos a interface
    private final RecyclerAdapterOnClickHandler mClickHandler;

    private List<RecipeData> mRecipeList;

    // contexto atual
    private Context context;


    /**
     * Construtor da classe
     *
     * @param clickHandler interface para definirmos a função do click
     */
    public RecipesRecyclerAdapter(RecyclerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
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
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_list_content, viewGroup, false);
        return new RecipesViewHolder(view, mClickHandler);
    }

    /**
     * onBindViewHolder, povoa os MovieViewHolder com as Informações do MovieData
     *
     * @param viewHolder viewHolder a ser povoado, neste caso um MovieViewHOlder
     * @param position   posição do MovieData da list
     */
    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder viewHolder, int position) {
        if (mRecipeList.get(position) != null) {
            String recipeName = mRecipeList.get(position).getRecipeName();
            String recipeImageUrl = mRecipeList.get(position).getRecipeImageUrl();

            viewHolder.mRecipeNameTextView.setText(recipeName);
            Glide.with(context).load(recipeImageUrl).into(viewHolder.mRecipeImageView);
        }
    }

    /**
     * getItemCount, retorna o tamanho da lista
     *
     * @return int com o tamanho
     */
    @Override
    public int getItemCount() {
        if (null == mRecipeList) return 0;
        return mRecipeList.size();
    }

    /**
     * setMovieData define qual lista queremos usar no MovieGridAdapter
     *
     * @param recipeList lista que utilizaremos
     */
    public void setRecipeData(List<RecipeData> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }

}
