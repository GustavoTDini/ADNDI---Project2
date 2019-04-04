package com.example.adndi___project2.app_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adndi___project2.R;


/**
 * Classe de ViewHolder que irá definir as view da RecyclerView da lista das receitas
 * - implementa o onClickListener para definir a ação de clique
 */
public class RecipesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    final TextView mRecipeNameTextView;
    final ImageView mRecipeImageView;

    // ClickHandler para definirmos a interface
    private RecyclerAdapterOnClickHandler mListener;

    RecipesViewHolder(View view, RecyclerAdapterOnClickHandler clickListener) {
        super(view);
        mRecipeNameTextView = view.findViewById(R.id.tv_recipe_main_title);
        mRecipeImageView = view.findViewById(R.id.iv_recipe_main_image);
        mListener = clickListener;
        view.setOnClickListener(this);

    }

    /**
     * Método a ser chamado em um click em uma view
     *
     * @param view view que foi clicada
     */
    @Override
    public void onClick(View view) {
        mListener.onClick(getAdapterPosition());
    }
}
