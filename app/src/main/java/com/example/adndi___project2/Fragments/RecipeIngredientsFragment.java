package com.example.adndi___project2.Fragments;

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

import com.example.adndi___project2.AppAdaptersAndViewHolders.IngredientsRecyclerAdapter;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeIngredients;
import com.example.adndi___project2.R;
import com.example.adndi___project2.ViewModel.GetIngredientViewModel;
import com.example.adndi___project2.ViewModel.GetIngredientViewModelFactory;

import java.util.List;

import timber.log.Timber;

public class RecipeIngredientsFragment extends Fragment {

    private int mRecipeId = 0;

    private RecipeDatabase mDb;

    public RecipeIngredientsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_ingredients_fragment, container, false);

        mDb = RecipeDatabase.getInstance(getContext());

        final RecyclerView recyclerView = rootView.findViewById(R.id.rv_ingredients_list);

        Timber.d("RecyclerView: %s", recyclerView.toString());

        ingredientsViewModel(recyclerView);

        return rootView;
    }

    private void ingredientsViewModel(final RecyclerView recyclerView) {

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

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

}
