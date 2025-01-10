package com.example.androidassignments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        Log.d("LoginActivity", "onCreate called");

        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText editTextTextPassword= findViewById(R.id.editTextTextPassword);
        Button Login_btn = findViewById(R.id.button2);

        SharedPreferences sharedPreferences=getSharedPreferences("MyLogin",MODE_PRIVATE);
        String loginEmail=sharedPreferences.getString("DefaultEmail", "email@domain.com");
        emailEditText.setText(loginEmail);

        Login_btn.setOnClickListener(view -> {
            String email= emailEditText.getText().toString();
            String password = editTextTextPassword.getText().toString().trim();

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(LoginActivity.this, R.string.email_invalid, Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.password_invalid, Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("DefaultEmail",email);
            editor.apply();

            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LoginActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LoginActivity", "onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LoginActivity", "onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LoginActivity", "onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LoginActivity", "onDestroy called");
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("LoginActivity", "onSaveInstanceState called");
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("LoginActivity", "onRestoreInstanceState called");
    }
}