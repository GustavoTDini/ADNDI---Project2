package com.example.adndi___project2.DataBase;

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

    /**
     * Método creator do parcel para aproveitarmos as funcionalidades da classe
     */
    public static final Parcelable.Creator<RecipeIngredients> CREATOR = new Parcelable.Creator<RecipeIngredients>() {
        @Override
        public RecipeIngredients createFromParcel(Parcel in) {
            return new RecipeIngredients(in);
        }

        @Override
        public RecipeIngredients[] newArray(int size) {
            return new RecipeIngredients[size];
        }
    };

    // String que conecta a MainActivity com este Intent atraves de PutExtra
    public static final String INGREDIENT_PARCEL = "ingredientParcel";


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

    /**
     * Construtor da Classe utilizando Parcel
     */
    @Ignore
    private RecipeIngredients(Parcel in) {
        this.mIngredientId = in.readInt();
        this.mRecipeId = in.readInt();
        this.mIngredientName = in.readString();
        this.mIngredientQuantity = in.readInt();
        this.mIngredientMeasure = in.readString();
    }

    /**
     * Construtor da Classe vazio para Parceler
     */
    @Ignore
    public RecipeIngredients() {
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
