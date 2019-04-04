package com.example.adndi___project2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNewRecipe(RecipeData recipe);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(RecipeData recipe);

    @Delete
    void deleteRecipe(RecipeData recipe);

    @Query("DELETE FROM recipes")
    void emptyRecipes();

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipeId")
    LiveData<RecipeData> loadRecipeById(int recipeId);

    @Query("SELECT * FROM recipes WHERE recipe_id = :recipeId")
    RecipeData getRecipeById(int recipeId);

    @Query("SELECT * FROM recipes")
    LiveData<List<RecipeData>> loadRecipes();

}
