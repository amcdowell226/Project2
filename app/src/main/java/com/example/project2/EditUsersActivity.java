package com.example.project2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.LiveData;

import com.example.project2.databinding.ActivityEditUsersBinding;

import com.example.project2.database.ProduceLogRepository;

import com.example.project2.database.entities.User;

public class EditUsersActivity extends AppCompatActivity {

    private ActivityEditUsersBinding binding;
    private ProduceLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = ProduceLogRepository.getRepository(getApplication());

        binding.addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        binding.deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();
            }
        });
    }

    private void createUser(){
        String username = binding.userNameChoiceEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank.");
            return;
        }

        String password = binding.passwordEditText.getText().toString();
        if(password.isEmpty()){
            toastMaker("Password may not be blank.");
            return;
        }

        String adminChoice = binding.adminChoiceEditText.getText().toString();

        User newUser = new User(username, password);
        if(adminChoice.equalsIgnoreCase("y")){
            newUser.setAdmin(true);
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                toastMaker("This user already exists.");
                binding.userNameChoiceEditText.setSelection(0);
            } else {
                repository.insertUser(newUser);
                toastMaker("New user has been added!");
            }
        });

//                showAdminAdditionDialog(newUser);

    }


    private void deleteUser(){
        String username = binding.userNameChoiceEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);
        userObserver.observe(this, user -> {
            if(user != null){
                repository.deleteUser(user);
                toastMaker("User has been deleted!");
            } else {
                toastMaker("This user does not exist."); // this gets repeated after deletion, dunno why
                binding.userNameChoiceEditText.setSelection(0);
            }
        });
    }

//        private void showAdminAdditionDialog(User added) {
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(EditUsersActivity.this);
//        final AlertDialog alertDialog = alertBuilder.create();
//
//        alertBuilder.setMessage("Make this user an admin?");
//
//        alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                added.setAdmin(true);
//            }
//        });
//        alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertDialog.dismiss();
//            }
//        });
//
//        alertBuilder.create().show();
//    }

    private void toastMaker(String message) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}