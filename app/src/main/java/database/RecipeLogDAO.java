package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.Product;
import database.entities.RecipeLog;

@Dao
public interface RecipeLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RecipeLog... recipe);

    @Delete
    void delete(RecipeLog recipe);

    @Query("SELECT * FROM " + "allRecipes" + " ORDER BY name")
    LiveData<List<Product>> getAllRecipes();

    @Query("DELETE from " + "produceTable")
    void deleteAll();

    @Query("SELECT * from " + "produceTable" + " WHERE id == :id")
    LiveData<RecipeLog> getRecipeById(int id);

    @Query("SELECT * from " + "produceTable" + " WHERE userId == :userId")
    LiveData<RecipeLog> getRecipesByUserId(int userId);
}
