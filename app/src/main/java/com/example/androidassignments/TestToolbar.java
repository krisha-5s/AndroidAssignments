package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class TestToolbar extends AppCompatActivity {

private String customMessage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        customMessage = getString(R.string.snack_bar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showSnackbar(view));
    }

    private void showSnackbar(View view) {
        Snackbar.make(view, customMessage, Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_one) {
            Log.d("Toolbar", getString(R.string.option_1));
            showChoice1Snackbar();
            return true;

        } else if (id == R.id.action_two) {
            Log.d("Toolbar", getString(R.string.option_2));
            showExitConfirmationDialog();
            return true;

        } else if (id == R.id.action_three) {
            Log.d("Toolbar", getString(R.string.option_3));
            showCustomDialog();
            return true;
            // Show the app version information
        } else if (id == R.id.action_about) {
            Toast.makeText(this, getString(R.string.version_num), Toast.LENGTH_SHORT).show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showChoice1Snackbar() {
        Snackbar.make(findViewById(R.id.toolbar), !customMessage.isEmpty() ? customMessage:getString(R.string.snack_bar) , Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show();
    }

    void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.go_back));
        builder.setPositiveButton(getString(R.string.set_Positive_Button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        builder.setNegativeButton(getString(R.string.set_Negative_Button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_custom, null);
        builder.setView(dialogView)
                .setTitle(getString(R.string.dialog_title_custom))
                .setPositiveButton(getString(R.string.set_Positive_Button), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleDialogPositiveClick(dialogView);
                    }
                });
        builder.setNegativeButton(getString(R.string.set_Negative_Button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void handleDialogPositiveClick(View dialogView) {
        EditText editTextNewMessage = dialogView.findViewById(R.id.new_message);
        customMessage = editTextNewMessage.getText().toString();
        if (customMessage.isEmpty()) {
            customMessage = getString(R.string.snack_bar);
        }
    }
}

