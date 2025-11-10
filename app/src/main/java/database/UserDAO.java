package database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import database.entities.User;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + "userTable" + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + "userTable")
    void deleteAll();

    @Query("SELECT * from " + "userTable" + " WHERE username == :username")
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from " + "userTable" + " WHERE id == :userId")
    LiveData<User> getUserByUserId(int userId);
}
