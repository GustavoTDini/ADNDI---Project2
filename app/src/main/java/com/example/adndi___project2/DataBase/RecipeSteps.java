package com.example.adndi___project2.DataBase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "steps")
@ForeignKey(entity = RecipeData.class, parentColumns = "recipe_id", childColumns = "recipe_id")
public class RecipeSteps {

    /**
     * Método creator do parcel para aproveitarmos as funcionalidades da classe
     */
    public static final Parcelable.Creator<RecipeSteps> CREATOR = new Parcelable.Creator<RecipeSteps>() {
        @Override
        public RecipeSteps createFromParcel(Parcel in) {
            return new RecipeSteps(in);
        }

        @Override
        public RecipeSteps[] newArray(int size) {
            return new RecipeSteps[size];
        }
    };

    // String que conecta a MainActivity com este Intent atraves de PutExtra
    public static final String STEPS_PARCEL = "stepsParcel";


    @PrimaryKey(autoGenerate = true)
    private int mStepId;
    @ColumnInfo(name = "recipe_id")
    private int mRecipeId;
    private int mStepOrder;
    private String mStepShortDescription;
    private String mStepDescription;
    private String mStepVideoUrl;
    private String mStepThumbnailUrl;


    /**
     * Construtor da Classe
     */

    public RecipeSteps(int stepId, int recipeId, int stepOrder, String stepShortDescription,
                       String stepDescription, String stepVideoUrl, String stepThumbnailUrl) {
        this.mStepId = stepId;
        this.mRecipeId = recipeId;
        this.mStepOrder = stepOrder;
        this.mStepShortDescription = stepShortDescription;
        this.mStepDescription = stepDescription;
        this.mStepVideoUrl = stepVideoUrl;
        this.mStepThumbnailUrl = stepThumbnailUrl;
    }

    @Ignore
    public RecipeSteps(int recipeId, int stepOrder, String stepShortDescription,
                       String stepDescription, String stepVideoUrl, String stepThumbnailUrl) {
        this.mRecipeId = recipeId;
        this.mStepOrder = stepOrder;
        this.mStepShortDescription = stepShortDescription;
        this.mStepDescription = stepDescription;
        this.mStepVideoUrl = stepVideoUrl;
        this.mStepThumbnailUrl = stepThumbnailUrl;
    }


    /**
     * Construtor da Classe utilizando Parcel
     */
    @Ignore
    private RecipeSteps(Parcel in) {
        this.mStepId = in.readInt();
        this.mRecipeId = in.readInt();
        this.mStepOrder = in.readInt();
        this.mStepDescription = in.readString();
        this.mStepDescription = in.readString();
        this.mStepVideoUrl = in.readString();
        this.mStepThumbnailUrl = in.readString();
    }

    /**
     * Construtor da Classe vazio para Parceler
     */
    @Ignore
    public RecipeSteps() {
    }

    public int getStepId() {
        return mStepId;
    }

    public void setStepId(int stepId) {
        this.mStepId = stepId;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(int recipeId) {
        this.mRecipeId = recipeId;
    }

    public int getStepOrder() {
        return mStepOrder;
    }

    public void setStepOrder(int stepOrder) {
        this.mStepOrder = stepOrder;
    }

    public String getStepShortDescription() {
        return mStepShortDescription;
    }

    public void setStepShortDescription(String stepShortDescription) {
        this.mStepShortDescription = stepShortDescription;
    }

    public String getStepDescription() {
        return mStepDescription;
    }

    public void setStepDescription(String stepDescription) {
        this.mStepDescription = stepDescription;
    }

    public String getStepVideoUrl() {
        return mStepVideoUrl;
    }

    public void setStepVideoUrl(String stepVideoUrl) {
        this.mStepVideoUrl = stepVideoUrl;
    }

    public String getStepThumbnailUrl() {
        return mStepThumbnailUrl;
    }

    public void setStepThumbnailUrl(String stepThumbnailUrl) {
        this.mStepThumbnailUrl = stepThumbnailUrl;
    }

    @Override
    public String toString() {
        return "RecipeSteps{" +
                "StepId=" + mStepId +
                ", RecipeId=" + mRecipeId +
                ", StepOrder=" + mStepOrder +
                ", StepShortDescription='" + mStepShortDescription + '\'' +
                ", StepDescription='" + mStepDescription + '\'' +
                ", StepVideoUrl='" + mStepVideoUrl + '\'' +
                ", StepThumbnailUrl='" + mStepThumbnailUrl + '\'' +
                '}';
    }
}
