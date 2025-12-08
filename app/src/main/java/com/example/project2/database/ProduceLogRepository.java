package com.example.project2.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.RecipeLog;
import com.example.project2.database.entities.User;

public class ProduceLogRepository {
    private final UserDAO userDAO;
    private final ProductDAO productDAO;
    private final RecipeLogDAO recipeLogDAO;
    private ArrayList<Product> allProduce;

    private static ProduceLogRepository repository;

    private ProduceLogRepository(Application application) {
        ProduceLogDatabase db = ProduceLogDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
        this.productDAO = db.productDAO();
        this.recipeLogDAO = db.recipeLogDAO();
    }

    public static ProduceLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }

        Future<ProduceLogRepository> future = ProduceLogDatabase.databaseWriteExecutor.submit(
            new Callable<ProduceLogRepository>() {
                @Override
                public ProduceLogRepository call() throws Exception {
                    return new ProduceLogRepository(application);
                }
            }
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
//            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return null;
    }

    public List<User> getAllUsersList(){
        return userDAO.getAllUsersList();
    }
    public List<Product> getAllProduceList(){ return productDAO.getAllProduceList(); }
    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public LiveData<Product> getProduceByName(String productName) {
        return productDAO.getProduceByName(productName);
    }

    public LiveData<Product> getProduceByType(String productType) {
        return productDAO.getProduceByType(productType);
    }

    public LiveData<Product> getProduceByProductId(int productId) {
        return productDAO.getProduceByProductId(productId);
    }

    public LiveData<RecipeLog> getRecipeById(int recipeId) {
        return recipeLogDAO.getRecipeById(recipeId);
    }

    public LiveData<List<RecipeLog>> getRecipesByUserId(int userId) {
        return recipeLogDAO.getRecipesByUserId(userId);
    }

    public void insertUser(User... user) {
        ProduceLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.insert(user);
        });
    }

    public void deleteUser(User user) {
        ProduceLogDatabase.databaseWriteExecutor.execute(() ->
        {
            userDAO.delete(user);
        });
    }

    public void insertProduct(Product... product) {
        ProduceLogDatabase.databaseWriteExecutor.execute(() ->
        {
            productDAO.insert(product);
        });
    }

    public void insertRecipe(RecipeLog recipe) {
        ProduceLogDatabase.databaseWriteExecutor.execute(() ->
        {
            recipeLogDAO.insert(recipe);
        });
    }


}
