package com.example.project2;

import android.os.Bundle;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private void makeRecipe(int id) {
        RecipeLog rl = new RecipeLog(id,
                "brokie you cant afford no groceries");
        HashMap<String, LiveData<ArrayList<Product>>> foods = new HashMap<>();
        List<String> method = new ArrayList<>(Arrays.asList("Boil", "Simmer", "Fry", "Stir-Fry"));
        List<String> temp = new ArrayList<>(Arrays.asList("High", "Medium-High", "Medium", "Medium-Low", "Low"));
        List<String> mix = new ArrayList<>(Arrays.asList("mix", "stir"));
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for(String t : Product.types) {
            foods.put(t, ProduceLogRepository.getAllProduceByType(t));
        }

        for(int i = 0; i < rand.nextInt(5,10))



        rl.setRecipe(sb.toString());
    }
}