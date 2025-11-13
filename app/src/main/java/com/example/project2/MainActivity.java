package com.example.project2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class MainActivity extends AppCompatActivity {

    // TODO: Detect if user has already logged in and set to true if so, skips this entire activity using sharedPreference
    // Context context = getActivity();
    //SharedPreferences sharedPref = context.getSharedPreferences(
    //getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginPrompt = findViewById(R.id.loginButton);

    }
// TODO: OnClickListener
}