package com.example.adndi___project2.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.database.RecipeSteps;

import java.util.List;

import timber.log.Timber;

public class RecipeViewModel extends AndroidViewModel {

    private LiveData<List<RecipeData>> recipes;
    private LiveData<List<RecipeIngredients>> ingredients;
    private LiveData<List<RecipeSteps>> steps;

    public RecipeViewModel(Application application) {
        super(application);
        RecipeDatabase database = RecipeDatabase.getInstance(this.getApplication());
        Timber.d("Atualizando o Banco de Dados");
        recipes = database.RecipeDao().loadRecipes();
        ingredients = database.IngredientDao().loadIngredients();
        steps = database.StepsDao().loadSteps();
    }

    public LiveData<List<RecipeData>> getRecipes() {
        return recipes;
    }

    public LiveData<List<RecipeIngredients>> getIngredients() {
        return ingredients;
    }

    public LiveData<List<RecipeSteps>> getSteps() {
        return steps;
    }

}
