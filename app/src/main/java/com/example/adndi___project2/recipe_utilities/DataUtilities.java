package com.example.adndi___project2.recipe_utilities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.database.RecipeDatabase;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.database.RecipeSteps;

import timber.log.Timber;

/**
 * Classe com os metodos que irão trabalhar com o banco de dados
 */
public class DataUtilities {

    public static final String ID_INTENT_EXTRA = "recipe_id";

    public static final String ID_SHARED_EXTRA = "recipe_shared_id";

    public static final String ID_SHARED_PREFERENCE = "shared_id";

    public static final String PAGER_ORDER_EXTRA = "pageOrder";


    // Member variable for the Database
    private static RecipeDatabase mDb;

    /**
     * Metodo para salvar um RecipeData no DataBase de ROOM
     *
     * @param recipe RecipeData a ser Salva
     * @param context Contexto Atual
     */
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

    /**
     * Metodo para salvar um RecipeIngredients no DataBase de ROOM
     *
     * @param ingredient RecipeIngredients a ser Salvo
     * @param context Contexto Atual
     */
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

    /**
     * Metodo para salvar um RecipeSteps no DataBase de ROOM
     *
     * @param step RecipeSteps a ser Salvo
     * @param context Contexto Atual
     */
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

    /**
     * Metodo para apagar todos os dados
     *
     * @param context Contexto Atual
     */
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
