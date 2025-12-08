package com.example.project2;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.ProduceLogDatabase;
import com.example.project2.database.ProductDAO;
import com.example.project2.database.RecipeLogDAO;
import com.example.project2.database.UserDAO;
import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.RecipeLog;
import com.example.project2.database.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DBUnitTest {
    private UserDAO userDAO;
    private ProductDAO productDAO;
    private RecipeLogDAO recipeLogDAO;
    private ProduceLogDatabase db;

    @Before
    public void createDb(){
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ProduceLogDatabase.class).build();
        userDAO = db.userDAO();
        productDAO = db.productDAO();
        recipeLogDAO = db.recipeLogDAO();
    }
    @After
    public void closeDb() throws IOException {
        db.close();
    }


    @Test
    public void insertUser(){
        User user = new User("testTest", "password");
        userDAO.insert(user);
        List<User> users = userDAO.getAllUsersList();
        assertNotNull(users.get(0));
        assertEquals("testTest", users.get(0).getUsername());
    }

    @Test
    public void deleteUser(){
        User user = new User("testTest", "password");
        User user2 = new User("test2", "test2");
        userDAO.insert(user);
        userDAO.insert(user2);
        List<User> users = userDAO.getAllUsersList();
        assertEquals(2, users.size());
        userDAO.delete(user);
        users = userDAO.getAllUsersList();
        assertFalse(users.contains(user2));

    }
//
//    @Test
//    public void updateUser(){
//
//    }
//
    @Test
    public void insertProduct(){
        Product p = new Product("bow-tie", "pasta");
        productDAO.insert(p);
        List<Product> pro = productDAO.getAllProduceList();
        assertNotNull(pro);
    }

    @Test
    public void deleteProduct(){
        Product p = new Product("bow-tie", "pasta");
        productDAO.insert(p);
        List<Product> pro = productDAO.getAllProduceList();
        assertNotNull(pro.get(0));
        productDAO.delete(p);
        pro = productDAO.getAllProduceList();
//        assertNull(pro);
        assertFalse(pro.contains(p));
    }
//
//    @Test
//    public void updateProduct(){
//
//    }
//
//    @Test
//    public void insertRecipe(){
//
//    }
//
    @Test
    public void deleteRecipe(){
        RecipeLog recipeLog = new RecipeLog(1, "bacon, tomato, cheese");
        recipeLogDAO.insert(recipeLog);
        List<RecipeLog> logs = recipeLogDAO.getAllRecipesList();
        assertNotNull(logs.get(0));
        recipeLogDAO.delete(recipeLog);
        assertFalse(logs.contains(recipeLog));
    }
//
//    @Test
//    public void updateRecipe(){
//
//    }
}
