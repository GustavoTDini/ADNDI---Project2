package com.example.adndi___project2.DataBase;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface StepsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addNewStep(RecipeSteps steps);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSteps(RecipeSteps steps);

    @Query("DELETE FROM steps WHERE recipe_id = :recipeId")
    void deleteRecipeSteps(int recipeId);

    @Query("DELETE FROM steps")
    void emptySteps();

    @Query("SELECT * FROM steps WHERE recipe_id = :recipeId")
    LiveData<List<RecipeSteps>> loadRecipeStepById(int recipeId);

    @Query("SELECT * FROM steps WHERE recipe_id = :recipeId AND mStepOrder =:stepOrder")
    LiveData<RecipeSteps> loadRecipeStepByOrder(int recipeId, int stepOrder);

    @Query("SELECT * FROM steps")
    LiveData<List<RecipeSteps>> loadSteps();

    @Query("SELECT * FROM steps WHERE recipe_id = :recipeId")
    List<RecipeSteps> loadWidgetSteps(int recipeId);

}
