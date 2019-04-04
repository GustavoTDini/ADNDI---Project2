package com.example.adndi___project2.recipe_utilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;

import com.example.adndi___project2.R;
import com.example.adndi___project2.database.RecipeData;
import com.example.adndi___project2.database.RecipeIngredients;
import com.example.adndi___project2.database.RecipeSteps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

/**
 * Classe com os metodos que irão trabalhar com os Dados de Json
 */
public class JsonUtilities {

    /**
     * Array com os drawables das imagens das receitas de sample
     */
    private static int[] drawableList = new int[]{R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheesecake};


    /**
     * addRecipeData adiciona os dados da receita
     *
     * @param recipeJson Json da Receita
     * @param context Contexto Atual
     * @param arrayIndex indice do Array para receber a imagem de drawableList correto
     */
    private static void addRecipeData(JSONObject recipeJson, Context context, int arrayIndex) {

        if (recipeJson == null) {
            return;
        }

        int recipeId = recipeJson.optInt("id");
        String recipeName = recipeJson.optString("name");
        int recipeServings = recipeJson.optInt("servings");
        String recipeImageUrl = getUrlForDrawable(drawableList[arrayIndex]);
        JSONArray recipeIngredients = recipeJson.optJSONArray("ingredients");
        JSONArray recipeSteps = recipeJson.optJSONArray("steps");

        RecipeData newRecipe = new RecipeData(recipeId, recipeName, recipeServings, recipeImageUrl);

        DataUtilities.SaveRecipeDataToDB(newRecipe, context);

        addIngredientsData(recipeIngredients, recipeId, context);

        addStepsData(recipeSteps, recipeId, context);

    }

    /**
     * addRecipeData adiciona os dados dos ingredientes da Receita com o respectivo Id
     *
     * @param ingredientsJsonString Json da Receita
     * @param context Contexto Atual
     * @param recipeId Id da receita
     */
    private static void addIngredientsData(JSONArray ingredientsJsonString, int recipeId, Context context) {

        if (ingredientsJsonString.length() < 1) {
            return;
        }

        try {

            for (int JsonArrayIndex = 0; JsonArrayIndex < ingredientsJsonString.length(); JsonArrayIndex++) {

                JSONObject thisRecipeIngredients = ingredientsJsonString.getJSONObject(JsonArrayIndex);

                String ingredientName = thisRecipeIngredients.optString("ingredient");
                int quantity = thisRecipeIngredients.optInt("quantity");
                String measure = thisRecipeIngredients.optString("measure");

                final RecipeIngredients ingredient = new RecipeIngredients(recipeId, ingredientName, quantity, measure);

                DataUtilities.SaveIngredientDataToDB(ingredient, context);

            }


        } catch (JSONException e) {
            Timber.e(e, "Problem parsing the Ingredients JSON results");
        }

    }

    /**
     * addRecipeData adiciona os dados dos passos da Receita com o respectivo Id
     *
     * @param stepsJsonString Json da Receita
     * @param context Contexto Atual
     * @param recipeId Id da receita
     */
    private static void addStepsData(JSONArray stepsJsonString, int recipeId, Context context) {

        if (stepsJsonString.length() < 1) {
            return;
        }

        try {

            for (int JsonArrayIndex = 0; JsonArrayIndex < stepsJsonString.length(); JsonArrayIndex++) {

                JSONObject thisRecipeStep = stepsJsonString.getJSONObject(JsonArrayIndex);

                int stepOrder = thisRecipeStep.optInt("id");
                String shortDescription = thisRecipeStep.optString("shortDescription", "");
                String description = thisRecipeStep.optString("description", "");
                String videoUrl = thisRecipeStep.optString("videoURL", "");
                String thumbnailUrl = thisRecipeStep.optString("thumbnailURL", "");

                final RecipeSteps step = new RecipeSteps(recipeId, stepOrder, shortDescription, description, videoUrl, thumbnailUrl);

                DataUtilities.SaveStepsDataToDB(step, context);

            }

        } catch (JSONException e) {
            Timber.e(e, "Problem parsing the Steps JSON results");
        }

    }

    /**
     * getUrlForDrawable recebe o Url aonde ficara a imagem
     *
     * @param drawableId id do resource da imagem
     */
    private static String getUrlForDrawable(int drawableId) {
        Resources.class.getPackage().getName();
        R.class.getPackage().getName();
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + drawableId).toString();
    }

    /**
     * createDataBaseSample cria o banco de dados de exemplo das receitas - baseado na Json disponibilizada
     *
     * @param jsonSample Json de amostra
     * @param context Contexto Atual
     */
    public static void createDataBaseSample(String jsonSample, Context context) {

        if (TextUtils.isEmpty(jsonSample)) {
            return;
        }

        try {

            JSONArray recipesArray = new JSONArray(jsonSample);

            for (int recipesIndex = 0; recipesIndex < recipesArray.length(); recipesIndex++) {

                JSONObject recipe = recipesArray.getJSONObject(recipesIndex);

                addRecipeData(recipe, context, recipesIndex);

            }

        } catch (JSONException e) {
            Timber.e(e, "Problem parsing the JSON results");
        }

    }
}
