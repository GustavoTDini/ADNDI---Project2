package com.example.adndi___project2.view_model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.adndi___project2.database.RecipeDatabase;

public class GetRecipeViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipeDatabase mDb;
    private final int mRecipeId;

    public GetRecipeViewModelFactory(RecipeDatabase database, int recipeId) {
        mDb = database;
        mRecipeId = recipeId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetRecipeViewModel(mDb, mRecipeId);
    }
}