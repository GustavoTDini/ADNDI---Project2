package com.example.adndi___project2.recipe_utilities;

import android.content.Context;

import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.database.RecipeSteps;

import timber.log.Timber;

public class DataUtilities {

    public static final String ID_INTENT_EXTRA = "recipe_id";

    public static final String ID_SHARED_EXTRA = "recipe_shared_id";

    public static final String ID_SHARED_PREFERENCE = "shared_id";

    public static final String PAGER_ORDER_EXTRA = "pageOrder";


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
