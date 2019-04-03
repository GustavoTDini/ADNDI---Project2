package com.example.adndi___project2.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.adndi___project2.database.RecipeDatabase;

import org.jetbrains.annotations.NotNull;

public class GetStepsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipeDatabase mDb;
    private final int mRecipeId;
    private final int mOrder;

    public GetStepsViewModelFactory(RecipeDatabase database, int recipeId, int order) {
        mDb = database;
        mRecipeId = recipeId;
        mOrder = order;
    }

    @NotNull
    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetStepsViewModel(mDb, mRecipeId, mOrder);
    }
}
