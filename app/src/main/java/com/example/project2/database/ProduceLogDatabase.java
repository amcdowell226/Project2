package com.example.project2.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.RecipeLog;
import com.example.project2.database.entities.User;

@Database(entities = {User.class, Product.class, RecipeLog.class}, version = 1)
public abstract class ProduceLogDatabase extends RoomDatabase {
    public static final String USER_TABLE = "userTable";
    public static final String PRODUCT_TABLE = "productTable";
    public static final String RECIPES_TABLE = "allRecipes";
    private static final String DATABASE_NAME = "ProduceLogDatabase";
    private static volatile ProduceLogDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ProduceLogDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProduceLogDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    ProduceLogDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            Log.i(MainActivity.TAG, "DATABASE CREATED!");
            try {
                databaseWriteExecutor.execute(() -> {
                    UserDAO dao = INSTANCE.userDAO();
                    dao.deleteAll();
                    User admin = new User("admin1", "admin1");
                    admin.setAdmin(true);
                    dao.insert(admin);
                    User testUser1 = new User("testuser1", "testuser1");
                    dao.insert(testUser1);

                    ProductDAO pDAO = INSTANCE.productDAO();
                    pDAO.deleteAll();
                    Product beef = new Product("beef", "meat");
                    pDAO.insert(beef);
                    Product apple = new Product("apple", "fruit");
                    pDAO.insert(apple);
                    Product tomato = new Product("tomato", "vegetable");
                    pDAO.insert(tomato);

                    RecipeLogDAO rDAO = INSTANCE.recipeLogDAO();
                    rDAO.deleteAll();
                    RecipeLog u0test1 = new RecipeLog(0, "Recipe 1 for user 0");
                    RecipeLog u0test2 = new RecipeLog(0, "Recipe 2 for user 0");
                    RecipeLog u0test3 = new RecipeLog(0, "Recipe 3 for user 0");
                    rDAO.insert(u0test1, u0test2, u0test3);
                    RecipeLog u1test1 = new RecipeLog(1, "Recipe 1 for user 1");
                    RecipeLog u1test2 = new RecipeLog(1, "Recipe 2 for user 1");
                    RecipeLog u1test3 = new RecipeLog(1, "Recipe 3 for user 1");
                    rDAO.insert(u1test1, u1test2, u1test3);
                    RecipeLog u2test1 = new RecipeLog(2, "Recipe 1 for user 2");
                    RecipeLog u2test2 = new RecipeLog(2, "Recipe 2 for user 2");
                    RecipeLog u2test3 = new RecipeLog(2, "Recipe 3 for user 2");
                    rDAO.insert(u2test1, u2test2, u2test3);
                });
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    };

    public abstract UserDAO userDAO();
    public abstract ProductDAO productDAO();
    public abstract RecipeLogDAO recipeLogDAO();
}
