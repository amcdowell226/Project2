package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.Product;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product... product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + "produceTable" + " ORDER BY name")
    LiveData<List<Product>> getAllProducts();

    @Query("DELETE from " + "produceTable")
    void deleteAll();

    @Query("SELECT * from " + "produceTable" + " WHERE name == :productName")
    LiveData<Product> getProduceByName(String productName);

    @Query("SELECT * from " + "produceTable" + " WHERE type == :productType")
    LiveData<Product> getProduceByType(String productType);

    @Query("SELECT * from " + "produceTable" + " WHERE id == :productId")
    LiveData<Product> getProduceByProductId(int productId);
}
