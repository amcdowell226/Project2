package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.User;

@Dao
public interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product... product);

    @Delete
    void delete(Product product);

    @Query("SELECT * FROM " + ProduceLogDatabase.PRODUCT_TABLE + " ORDER BY name")
    LiveData<List<Product>> getAllProducts();

    @Query("DELETE from " + ProduceLogDatabase.PRODUCT_TABLE)
    void deleteAll();

    @Query("SELECT * from " + ProduceLogDatabase.PRODUCT_TABLE + " WHERE name == :productName")
    LiveData<Product> getProduceByName(String productName);

    @Query("SELECT * from " + ProduceLogDatabase.PRODUCT_TABLE)
    List<Product> getAllProduceList();

    @Query("SELECT * from " + ProduceLogDatabase.PRODUCT_TABLE + " WHERE type == :productType")
    LiveData<Product> getProduceByType(String productType);

    @Query("SELECT * from " + ProduceLogDatabase.PRODUCT_TABLE + " WHERE type == :productType")
    LiveData<List<Product>> getAllProduceByType(String productType);

    @Query("SELECT * from " + ProduceLogDatabase.PRODUCT_TABLE + " WHERE id == :productId")
    LiveData<Product> getProduceByProductId(int productId);

    @Query("UPDATE " + ProduceLogDatabase.PRODUCT_TABLE + " SET name = :name WHERE id == :productId")
    void updateProductName(String name, int productId);
}
