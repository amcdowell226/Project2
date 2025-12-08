package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project2.databinding.ActivityAdminBinding;

import com.example.project2.database.ProduceLogRepository;

public class AdminActivity extends AppCompatActivity {
    private ActivityAdminBinding binding;
    private ProduceLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ProduceLogRepository.getRepository(getApplication());

        binding.editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EditUsersActivity.class);
                startActivity(intent);
            }
        });

        binding.editProduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, EditProduceActivity.class);
                startActivity(intent);
            }
        });
    }

    static Intent adminIntentFactory(Context context){
        return new Intent(context, AdminActivity.class);
    }


}