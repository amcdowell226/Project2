package com.example.project2;
import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.project2.database.entities.User;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeGeneratorTest {

    @Test
    public void userTest(){
        User user = new User("test1", "test2");
        assertEquals("test1", user.getUsername());
        assertEquals("test2", user.getPassword());
        user.setPassword("wahhh");
        assertNotEquals("test2", user.getPassword());
    }

}
