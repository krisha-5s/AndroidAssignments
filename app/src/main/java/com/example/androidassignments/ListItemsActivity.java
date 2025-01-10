package com.example.androidassignments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ListItemsActivity extends AppCompatActivity {
    ImageButton imageButton;
    Switch switchButton;
    CheckBox checkBox;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_items);
        Log.d("listItemsActivity", "onCreate called");

        if(ContextCompat.checkSelfPermission(ListItemsActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ListItemsActivity.this ,new String[]{
                    Manifest.permission.CAMERA,
            },REQUEST_IMAGE_CAPTURE);
        }

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> openCamera());

        Button Back_btn = findViewById(R.id.button3);
        Back_btn.setOnClickListener(view -> {
            Intent intent=new Intent(ListItemsActivity.this, MainActivity.class);
            setResult(RESULT_OK,intent);
            startActivity(intent);
        });

        Button Logout_btn = findViewById(R.id.button4);
        Logout_btn.setOnClickListener(view -> {
            Intent intent=new Intent(ListItemsActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        switchButton=findViewById(R.id.switch1);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                CharSequence text;
                int duration;
                if(isChecked){
                    text = getText(R.string.switch_is_On);
                    duration = Toast.LENGTH_SHORT;
                }
                else{
                    text = getText(R.string.switch_is_Off);
                    duration = Toast.LENGTH_LONG;
                }

                Toast toast = Toast.makeText(ListItemsActivity.this, text, duration);
                toast.show();
            }
        });

       checkBox=findViewById(R.id.checkBox);
       checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               if(isChecked){
                   AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
                   builder.setMessage(R.string.set_Message)
                           .setTitle(R.string.set_Title)
                           .setPositiveButton(R.string.set_Positive_Button, new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                               Intent resultIntent = new Intent();
                                   resultIntent .putExtra("Response", R.string.Response);
                                   setResult(Activity.RESULT_OK, resultIntent);
                                   finish();
                               }
                           })
                           .setNegativeButton(R.string.set_Negative_Button, new DialogInterface.OnClickListener() {
                               public void onClick(DialogInterface dialog, int id) {
                                 checkBox.setChecked(false);
                               }
                           })
                           .show();
               }
           }
       });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle fetch = data.getExtras();
            Bitmap imageBitmap = (Bitmap) fetch.get("data");
            if(imageBitmap != null){
            imageButton.setImageBitmap(imageBitmap);
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ListItemsActivity", "onStart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ListItemsActivity", "onResume called");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ListItemsActivity", "onPause called");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ListItemsActivity", "onStop called");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ListItemsActivity", "onDestroy called");
    }
    @Override
    public void finish(){
        super.finish();
        Log.d("List activity","This activity is closed");
    }
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("ListItemsActivity", "onSaveInstanceState called");
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("ListItemsActivity", "onRestoreInstanceState called");
    }
}