package com.example.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import com.example.project2.database.entities.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + ProduceLogDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + ProduceLogDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + ProduceLogDatabase.USER_TABLE)
    List<User> getAllUsersList();

    @Query("SELECT * from " + ProduceLogDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from " + ProduceLogDatabase.USER_TABLE + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);

    @Query("UPDATE " + ProduceLogDatabase.USER_TABLE + " SET username = :username WHERE id == :userId")
    void updateUserName(String username, int userId);
}
