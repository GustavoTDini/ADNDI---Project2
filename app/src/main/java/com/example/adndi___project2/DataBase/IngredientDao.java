package com.example.adndi___project2.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNewIngredient(RecipeIngredients ingredient);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateIngredient(RecipeIngredients ingredient);

    @Query("DELETE FROM ingredients WHERE recipe_id = :recipeId")
    void deleteRecipeIngredients(int recipeId);

    @Query("DELETE FROM ingredients")
    void emptyIngredients();

    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipeId")
    LiveData<List<RecipeIngredients>> loadRecipeIngredients(int recipeId);

    @Query("SELECT * FROM ingredients")
    LiveData<List<RecipeIngredients>> loadIngredients();

    @Query("SELECT * FROM ingredients WHERE recipe_id = :recipeId")
    List<RecipeIngredients> loadWidgetIngredients(int recipeId);
}
