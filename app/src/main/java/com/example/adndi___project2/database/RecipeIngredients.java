package com.example.adndi___project2.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "ingredients")
@ForeignKey(entity = RecipeData.class, parentColumns = "recipe_id", childColumns = "recipe_id")
public class RecipeIngredients {

    @PrimaryKey(autoGenerate = true)
    private int mIngredientId;
    @ColumnInfo(name = "recipe_id")
    private int mRecipeId;
    private String mIngredientName;
    private int mIngredientQuantity;
    private String mIngredientMeasure;

    /**
     * Construtor da Classe
     */

    public RecipeIngredients(int ingredientId, int recipeId, String ingredientName, int ingredientQuantity, String ingredientMeasure) {
        this.mIngredientId = ingredientId;
        this.mRecipeId = recipeId;
        this.mIngredientName = ingredientName;
        this.mIngredientQuantity = ingredientQuantity;
        this.mIngredientMeasure = ingredientMeasure;
    }

    @Ignore
    public RecipeIngredients(int recipeId, String ingredientName, int ingredientQuantity, String ingredientMeasure) {
        this.mRecipeId = recipeId;
        this.mIngredientName = ingredientName;
        this.mIngredientQuantity = ingredientQuantity;
        this.mIngredientMeasure = ingredientMeasure;
    }

    public int getIngredientId() {
        return mIngredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.mIngredientId = ingredientId;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(int recipeId) {
        this.mRecipeId = recipeId;
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.mIngredientName = ingredientName;
    }

    public int getIngredientQuantity() {
        return mIngredientQuantity;
    }

    public void setIngredientQuantity(int ingredientQuantity) {
        this.mIngredientQuantity = ingredientQuantity;
    }

    public String getIngredientMeasure() {
        return mIngredientMeasure;
    }

    public void setIngredientMeasure(String ingredientMeasure) {
        this.mIngredientMeasure = ingredientMeasure;
    }

    @Override
    public String toString() {
        return "RecipeIngredients{" +
                "IngredientId=" + mIngredientId +
                ", RecipeId=" + mRecipeId +
                ", IngredientName='" + mIngredientName + '\'' +
                ", IngredientQuantity=" + mIngredientQuantity +
                ", IngredientMeasure='" + mIngredientMeasure + '\'' +
                '}';
    }
}
