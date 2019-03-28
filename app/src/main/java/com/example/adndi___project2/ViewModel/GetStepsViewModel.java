package com.example.adndi___project2.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeSteps;

import java.util.List;

public class GetStepsViewModel extends ViewModel {
    private LiveData<List<RecipeSteps>> mSteps;

    public GetStepsViewModel(RecipeDatabase database, int recipeId) {
        mSteps = database.StepsDao().loadRecipeStepById(recipeId);
    }

    public LiveData<List<RecipeSteps>> getSteps() {
        return mSteps;
    }

}
