package database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import database.entities.User;

public class ProduceLogRepository {
    private final UserDAO userDAO;
//    private ArrayList<AllProduce> allProduce;

    private static ProduceLogRepository repository;

    private ProduceLogRepository(Application application) {
        ProduceLogDatabase db = ProduceLogDatabase.getDatabase(application);
        this.userDAO = db.userDAO();
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

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

}
