package com.example.project2;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.databinding.ActivityEditProduceBinding;
import com.example.project2.databinding.ActivityEditUsersBinding;

public class EditProduceActivity extends AppCompatActivity {
    private ActivityEditProduceBinding binding;
    private ProduceLogRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProduceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ProduceLogRepository.getRepository(getApplication());

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LandingPage.landingActivityIntentFactory(getApplicationContext(), loggedInUserId));
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduce();
            }
        });

        binding.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduce();
            }
        });
    }

    private void addProduce() {
        String pName = binding.nameEditText.getText().toString();
        String pType = binding.typeEditText.getText().toString();

        if()
    }

    private void deleteProduce() {

    }
}