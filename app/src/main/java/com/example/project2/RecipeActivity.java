package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.database.ProduceLogDatabase;
import com.example.project2.databinding.ActivityRecipeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.RecipeLog;

public class RecipeActivity extends AppCompatActivity {
    private ActivityRecipeBinding binding;
    private ProduceLogRepository repository;
    private int loggedInUserId;
    private HashMap<String, List<Product>> foods;
    private List<String> method = new ArrayList<>(Arrays.asList("Boil", "Simmer", "Fry", "Stir-Fry"));
    private List<String> temp = new ArrayList<>(Arrays.asList("High", "Medium-High", "Medium", "Medium-Low", "Low"));
    private List<String> mix = new ArrayList<>(Arrays.asList("Mix", "Stir"));
    private boolean dataLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserId = getIntent().getIntExtra("loggedIn_UserId_RecipeActivity", -1);
        foods = new HashMap<>();
        repository = ProduceLogRepository.getRepository(getApplication());

        binding.generateButton.setEnabled(false);

        for (String t : ProduceLogDatabase.TYPES) {
            repository.getAllProduceByType(t).observe(this, products -> {
                if (products != null && !products.isEmpty()) {
                    Log.d("inputting t and products into hashmap", "Types: " + t + " products: " + products);
                    Log.d("foods before update ", foods.toString());
                    foods.put(t, products);
                    Log.d("foods after update ", foods.toString());
                }
                checkIfAllDataLoaded();
            });
        }

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


        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        int rLength = rand.nextInt(10);
        for(int i = 0; i < rLength; i++) {
            String cType = ProduceLogDatabase.TYPES.get(rand.nextInt(ProduceLogDatabase.TYPES.size()));
            String cFood = Objects.requireNonNull(foods.get(cType)).get(rand.nextInt(Objects.requireNonNull(foods.get(cType)).size())).getName();
            String cType2 = ProduceLogDatabase.TYPES.get(rand.nextInt(ProduceLogDatabase.TYPES.size()));;
            String cFood2 = Objects.requireNonNull(foods.get(cType2)).get(rand.nextInt(Objects.requireNonNull(foods.get(cType2)).size())).getName();
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
        repository.insertRecipe(rl);
        return rl.getRecipe();
    }

    private void checkIfAllDataLoaded() {
        if (foods.keySet().containsAll(ProduceLogDatabase.TYPES)) {
            dataLoaded = true;
            binding.generateButton.setEnabled(true);
        }
    }

    static Intent recipeActivityIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, RecipeActivity.class);
        intent.putExtra("loggedIn_UserId_RecipeActivity", userId);
        return intent;
    }
}