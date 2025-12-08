package com.example.project2;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.project2.databinding.ActivitySignupBinding;

import com.example.project2.database.ProduceLogRepository;
import com.example.project2.database.entities.User;


public class SignupActivity extends AppCompatActivity {
    private ActivitySignupBinding binding;
    private ProduceLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ProduceLogRepository.getRepository(getApplication());

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void setContentView(ConstraintLayout root) {
    }

    private void createUser() {
        String username = String.valueOf(binding.usernameLayout);
        if (username.isEmpty()) {
            toastMaker("Username may not be blank.");
            return;
        }

        String password = String.valueOf(binding.passwordLayout);
        if (password.isEmpty()) {
            toastMaker("Password may not be blank.");
            return;
        }

        User newUser = new User(username, password);

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if (user != null) {
                toastMaker("This user already exists.");
            } else {
                // TODO: Make this connect properly when merged.
//                repository.insertUser(newUser);
                toastMaker("New user has been added!");
            }
        });
    }
    private void toastMaker (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}