package com.example.adndi___project2.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeIngredients;

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