package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project2.databinding.ActivityLoginBinding;
import com.example.project2.databinding.ActivityRecipeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import database.ProduceLogRepository;
import database.entities.Product;
import database.entities.RecipeLog;
import database.entities.User;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private ProduceLogRepository repository;
    private int loggedInUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserId = getIntent().getIntExtra("loggedIn_UserId_RecipeActivity", -1);

        repository = ProduceLogRepository.getRepository(getApplication());

        binding.backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LandingPage.landingActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currRecipe = makeRecipe(loggedInUserId);
                binding.randomRecipeView.setText(currRecipe);
            }
        });
    }

    private String makeRecipe(int id) {
        RecipeLog rl = new RecipeLog(id, "brokie you cant afford no groceries");
        HashMap<String, List<Product>> foods = new HashMap<>();
        List<String> method = new ArrayList<>(Arrays.asList("Boil", "Simmer", "Fry", "Stir-Fry"));
        List<String> temp = new ArrayList<>(Arrays.asList("High", "Medium-High", "Medium", "Medium-Low", "Low"));
        List<String> mix = new ArrayList<>(Arrays.asList("Mix", "Stir"));
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for(String t : Product.types) {
            repository.getAllProduceByType(t).observe(this, products -> {
                foods.put(t, products);
            });
        }

        int rLength = rand.nextInt(10);
        for(int i = 0; i < rLength; i++) {
            String cType = Product.types.get(rand.nextInt(Product.types.size()));
            String cFood = foods.get(cType).get(rand.nextInt(foods.get(cType).size())).getName();
            String cType2 = Product.types.get(rand.nextInt(Product.types.size()));;
            String cFood2 = foods.get(cType2).get(rand.nextInt(foods.get(cType2).size())).getName();
            if(i%2 == 0) {
                sb.append(method.get(rand.nextInt(method.size())));
                sb.append(" ").append(cFood);
                sb.append(" at ").append(temp.get(rand.nextInt(temp.size())));
                sb.append("\n");
            } else {
                sb.append(mix.get(rand.nextInt(mix.size())));
                sb.append(" ").append(cFood).append(" into ");
                sb.append(cFood2).append("\n");
            }
        }

        rl.setRecipe(sb.toString());
        return rl.getRecipe();
    }

    static Intent recipeActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra("loggedIn_UserId_RecipeActivity", userId);
        return intent;
    }
}