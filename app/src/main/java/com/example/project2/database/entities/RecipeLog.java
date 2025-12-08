package com.example.project2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

import com.example.project2.database.ProduceLogDatabase;

@Entity(tableName = ProduceLogDatabase.RECIPES_TABLE)
public class RecipeLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private String recipe;

    @NonNull
    @Override
    public String toString() {
        return "RecipeLog{" +
                "recipe='" + recipe + '\'' +
                '}';
    }

    public RecipeLog(int userId, String recipe) {
        this.userId = userId;
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RecipeLog recipeLog = (RecipeLog) o;
        return id == recipeLog.id && userId == recipeLog.userId && Objects.equals(recipe, recipeLog.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, recipe);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
