package com.example.adndi___project2.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipeData {

    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    private int mRecipeId;
    private String mRecipeName;
    private int mRecipeServings;
    private String mRecipeImageUrl;

    /**
     * Construtor da Classe
     */
    public RecipeData(int recipeId, String recipeName, int recipeServings, String recipeImageUrl) {
        this.mRecipeId = recipeId;
        this.mRecipeName = recipeName;
        this.mRecipeServings = recipeServings;
        this.mRecipeImageUrl = recipeImageUrl;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(int recipeId) {
        this.mRecipeId = recipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        this.mRecipeName = recipeName;
    }

    public int getRecipeServings() {
        return mRecipeServings;
    }

    public void setRecipeServings(int recipeServings) {
        this.mRecipeServings = recipeServings;
    }

    public String getRecipeImageUrl() {
        return mRecipeImageUrl;
    }

    public void setRecipeImageUrl(String recipeImageUrl) {
        this.mRecipeImageUrl = recipeImageUrl;
    }

    @Override
    public String toString() {
        return "RecipeData{" +
                "RecipeId=" + mRecipeId +
                ", RecipeName='" + mRecipeName + '\'' +
                ", RecipeServings=" + mRecipeServings +
                ", RecipeImageUrl='" + mRecipeImageUrl + '\'' +
                '}';
    }
}
