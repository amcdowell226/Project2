package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.database.entities.Product;
import com.example.project2.database.entities.User;
import com.example.project2.databinding.ActivityEditProduceBinding;
import com.example.project2.databinding.ActivityEditUsersBinding;

public class EditProduceActivity extends AppCompatActivity {
    private ActivityEditProduceBinding binding;
    private ProduceLogRepository repository;
    private int loggedInUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProduceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loggedInUserId = getIntent().getIntExtra("loggedIn_UserId_EditProduceActivity", -1);

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

        if(pName.isEmpty()){
            toastMaker("Produce needs to be named");
            return;
        }
        if(pType.isEmpty()){
            toastMaker("Produce needs a type");
            return;
        }

        Product p = new Product(pName, pType);
        repository.insertProduct(p);
    }

    private void deleteProduce() {
        String pName = binding.nameEditText.getText().toString();
        if(pName.isEmpty()){
            toastMaker("Produce needs to be named");
            return;
        }

        LiveData<Product> productObserver = repository.getProduceByName(pName);
        productObserver.observe(this, product -> {
            if(product != null){
                repository.deleteProduct(product);
                toastMaker("Produce has been deleted!");
            } else {
                toastMaker("This produce does not exist.");
                binding.nameEditText.setSelection(0);
            }
        });
    }

    static Intent editProduceIntentFactory(Context context, int userId) {
        Intent intent = new Intent(context, EditProduceActivity.class);
        intent.putExtra("loggedIn_UserId_EditProduceActivity", userId);
        return intent;
    }
    private void toastMaker(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}