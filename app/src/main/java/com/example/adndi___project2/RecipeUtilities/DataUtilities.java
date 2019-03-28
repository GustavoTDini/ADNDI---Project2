package com.example.adndi___project2.RecipeUtilities;

import android.content.Context;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeDatabase;
import com.example.adndi___project2.DataBase.RecipeIngredients;
import com.example.adndi___project2.DataBase.RecipeSteps;

import timber.log.Timber;

public class DataUtilities {

    public static final String ID_INTENT_EXTRA = "recipe_id";


    // Member variable for the Database
    private static RecipeDatabase mDb;

    public static void SaveRecipeDataToDB(final RecipeData recipe, Context context) {

        mDb = RecipeDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.RecipeDao().addNewRecipe(recipe);
                Timber.d(recipe.toString());
            }
        });
    }

    public static void SaveIngredientDataToDB(final RecipeIngredients ingredient, Context context) {

        mDb = RecipeDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.IngredientDao().addNewIngredient(ingredient);
                Timber.d(ingredient.toString());
            }
        });
    }

    public static void SaveStepsDataToDB(final RecipeSteps step, Context context) {

        mDb = RecipeDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.StepsDao().addNewStep(step);
                Timber.d(step.toString());
            }
        });
    }

    public static void eraseAllData(Context context) {

        mDb = RecipeDatabase.getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.StepsDao().emptySteps();
                mDb.IngredientDao().emptyIngredients();
                mDb.RecipeDao().emptyRecipes();
            }
        });
    }
}
