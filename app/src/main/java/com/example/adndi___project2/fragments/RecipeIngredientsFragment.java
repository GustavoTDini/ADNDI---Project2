package com.example.adndi___project2.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adndi___project2.R;
import com.example.adndi___project2.app_adapters.IngredientsRecyclerAdapter;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.recipe_utilities.DataUtilities;
import com.example.adndi___project2.view_model.GetIngredientViewModel;
import com.example.adndi___project2.view_model.GetIngredientViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import timber.log.Timber;

public class RecipeIngredientsFragment extends Fragment {

    private int mRecipeId = 0;

    public RecipeIngredientsFragment() {
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredients_fragment, container, false);

        final RecyclerView recyclerView = rootView.findViewById(R.id.rv_ingredients_list);

        if (getArguments() != null && getArguments().containsKey( DataUtilities.ID_INTENT_EXTRA )) {
            mRecipeId = getArguments().getInt( DataUtilities.ID_INTENT_EXTRA );
        }

        Timber.d("RecyclerView: %s", recyclerView.toString());

        ingredientsViewModel(recyclerView);

        return rootView;
    }

    private void ingredientsViewModel(final RecyclerView recyclerView) {
        RecipeDatabase mDb = RecipeDatabase.getInstance(getContext());

        GetIngredientViewModelFactory factory = new GetIngredientViewModelFactory(mDb, mRecipeId);
        final GetIngredientViewModel viewModel
                = ViewModelProviders.of(this, factory).get(GetIngredientViewModel.class);

        viewModel.getIngredients().observe(this, new Observer<List<RecipeIngredients>>() {
            @Override
            public void onChanged(@Nullable List<RecipeIngredients> recipeIngredients) {
                setupRecyclerView(recyclerView, recipeIngredients);

            }
        });
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<RecipeIngredients> recipesList) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        IngredientsRecyclerAdapter mIngredientsAdapter = new IngredientsRecyclerAdapter();
        mIngredientsAdapter.setIngredientsList(recipesList);
        recyclerView.setAdapter(mIngredientsAdapter);
    }

}
