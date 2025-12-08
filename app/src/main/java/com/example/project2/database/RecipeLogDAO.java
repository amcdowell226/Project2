package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.RecipeLog;
import com.example.project2.database.entities.User;

@Dao
public interface RecipeLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeLog... recipe);

    @Delete
    void delete(RecipeLog recipe);

    @Query("SELECT * FROM " + ProduceLogDatabase.RECIPES_TABLE + " ORDER BY id")
    LiveData<List<Product>> getAllRecipes();

    @Query("SELECT * from " + ProduceLogDatabase.RECIPES_TABLE)
    List<RecipeLog> getAllRecipesList();

    @Query("DELETE from " + ProduceLogDatabase.RECIPES_TABLE)
    void deleteAll();

    @Query("SELECT * from " + ProduceLogDatabase.RECIPES_TABLE + " WHERE id == :id")
    LiveData<RecipeLog> getRecipeById(int id);

    @Query("SELECT * from " + ProduceLogDatabase.RECIPES_TABLE + " WHERE userId == :userId")
    LiveData<List<RecipeLog>> getRecipesByUserId(int userId);

    @Query("UPDATE " + ProduceLogDatabase.RECIPES_TABLE + " SET recipe = :recipe WHERE id == :recipeId")
    void updateRecipe(String recipe, int recipeId);
}
