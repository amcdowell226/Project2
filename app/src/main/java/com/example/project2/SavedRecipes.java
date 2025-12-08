package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.databinding.ActivitySavedRecipiesBinding;
import com.example.project2.viewHolders.RecipeLogAdapter;
import com.example.project2.viewHolders.RecipeLogViewModel;

public class SavedRecipes extends AppCompatActivity {
    private RecipeLogViewModel recipeLogViewModel;
    private ActivitySavedRecipiesBinding binding;
    private int loggedInUserId = -1;
    private ProduceLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_saved_recipies);

        binding = ActivitySavedRecipiesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recipeLogViewModel = new ViewModelProvider(this).get(RecipeLogViewModel.class);
        RecyclerView recyclerView = binding.savedRecipesOutput;
        final RecipeLogAdapter adapter = new RecipeLogAdapter(new RecipeLogAdapter.RecipeLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = ProduceLogRepository.getRepository(getApplication());
        loggedInUserId = getIntent().getIntExtra("LoggedIn_UserId", -1);
        Log.d("loggedinUserId_Tag", String.valueOf(loggedInUserId));

        recipeLogViewModel.getAllLogsById(loggedInUserId).observe(this, recipes -> {
            adapter.submitList(recipes);
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LandingPage.landingActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });
    }

    static Intent savedRecipesActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, SavedRecipes.class);
        intent.putExtra("LoggedIn_UserId", userId);
        return intent;
    }
}