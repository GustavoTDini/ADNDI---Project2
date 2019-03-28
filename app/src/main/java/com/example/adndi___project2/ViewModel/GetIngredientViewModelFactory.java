package com.example.adndi___project2.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.adndi___project2.DataBase.RecipeDatabase;

public class GetIngredientViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final RecipeDatabase mDb;
    private final int mRecipeId;

    public GetIngredientViewModelFactory(RecipeDatabase database, int recipeId) {
        mDb = database;
        mRecipeId = recipeId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new GetIngredientViewModel(mDb, mRecipeId);
    }
}