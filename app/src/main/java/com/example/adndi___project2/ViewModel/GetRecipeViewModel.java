package com.example.adndi___project2.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;

public class GetRecipeViewModel extends ViewModel {

    private LiveData<RecipeData> mRecipe;

    public GetRecipeViewModel(RecipeDatabase database, int recipeId) {
        mRecipe = database.RecipeDao().loadRecipeById(recipeId);
    }

    public LiveData<RecipeData> getRecipe() {
        return mRecipe;
    }
}