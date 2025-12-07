package database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import database.entities.Product;
import database.entities.RecipeLog;
import database.entities.User;

@Database(entities = {User.class, Product.class, RecipeLog.class}, version = 2)
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
