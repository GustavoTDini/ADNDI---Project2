package com.example.adndi___project2.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;

import java.util.List;

public class GetIngredientViewModel extends ViewModel {

    private LiveData<List<RecipeIngredients>> mIngredientsList;

    public GetIngredientViewModel(RecipeDatabase database, int recipeId) {
        mIngredientsList = database.IngredientDao().loadRecipeIngredients(recipeId);
    }

    public LiveData<List<RecipeIngredients>> getIngredients() {
        return mIngredientsList;
    }
}