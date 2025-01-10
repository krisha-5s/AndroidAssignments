package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "MainActivity";
    public static final int REQUEST_CODE = 10;
    private Spinner citySpinner;
    private String selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity", "onCreate called");

        Button List_items_btn = findViewById(R.id.button);
        List_items_btn.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, ListItemsActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        });

        Button start_chat_btn = findViewById(R.id.button5);
        start_chat_btn.setOnClickListener(view -> {
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");
            Intent intent=new Intent(MainActivity.this, ChatWindow.class);
            startActivity(intent);
        });

        Button test_toolbar_btn = findViewById(R.id.buttonTestToolbar);
        test_toolbar_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TestToolbar.class);
            startActivity(intent);
        });



        citySpinner = findViewById(R.id.citySpinner);
        String[] cities = {"Ottawa", "Toronto", "Vancouver", "Calgary", "Montreal", "Brampton", "Kitchener", "Waterloo"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, cities);
        citySpinner.setAdapter(adapter);
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cities[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity = "Ottawa";
            }
        });

        Button weather_btn = findViewById(R.id.button6);
        weather_btn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherForecast.class);
            intent.putExtra("selectedCity", selectedCity); // Pass the selected city
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String toastData = data.getStringExtra("Response");
            if(toastData != null){
                Log.d("MainActivity", "Returned to MainActivity.onActivityResult");
                Toast.makeText(MainActivity.this, toastData,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity", "onDestroy called");
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MainActivity", "onSaveInstanceState called");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("MainActivity", "onRestoreInstanceState called");
    }
}