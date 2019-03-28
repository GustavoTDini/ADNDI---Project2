package com.example.adndi___project2.RecipeUtilities;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;

import com.example.adndi___project2.DataBase.RecipeData;
import com.example.adndi___project2.DataBase.RecipeIngredients;
import com.example.adndi___project2.DataBase.RecipeSteps;
import com.example.adndi___project2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;


public class JsonUtilities {

    private static int[] drawableList = new int[]{R.drawable.nutella_pie, R.drawable.brownies, R.drawable.yellow_cake, R.drawable.cheesecake};


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

    private static String getUrlForDrawable(int drawableId) {
        Resources.class.getPackage().getName();
        R.class.getPackage().getName();
        return Uri.parse("android.resource://" + R.class.getPackage().getName() + "/" + drawableId).toString();
    }

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
