package com.example.project2.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.database.entities.RecipeLog;

import java.util.List;

public class RecipeLogViewModel extends AndroidViewModel {
    private final ProduceLogRepository repository;

    public RecipeLogViewModel(Application application) {
        super(application);
        repository = ProduceLogRepository.getRepository(application);
    }

    public LiveData<List<RecipeLog>> getAllLogsById(int userId) {
        return repository.getRecipesByUserId(userId);
    }

    public void insert(RecipeLog recipe) {
        repository.insertRecipe(recipe);
    }
}
