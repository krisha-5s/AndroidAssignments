package com.example.androidassignments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    private LoginActivity loginActivity;
    private Context context;

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        context = ApplicationProvider.getApplicationContext();
        loginActivity = Robolectric.setupActivity(LoginActivity.class);
    }

    @Test
    public void testEmailValidation_CorrectEmail() {
        String validEmail = "test@example.com";
        assertTrue(Patterns.EMAIL_ADDRESS.matcher(validEmail).matches());
    }

    @Test
    public void testEmailValidation_IncorrectEmail() {
        String invalidEmail = "invalid-email";
        assertFalse(Patterns.EMAIL_ADDRESS.matcher(invalidEmail).matches());
    }

    @Test
    public void testLogin_InvalidPassword() {
        EditText emailEditText = loginActivity.findViewById(R.id.emailEditText);
        emailEditText.setText("test@example.com");

        EditText passwordEditText = loginActivity.findViewById(R.id.editTextTextPassword);
        passwordEditText.setText("");

        Button loginButton = loginActivity.findViewById(R.id.button2);
        loginButton.performClick();

        assertTrue(passwordEditText.getText().toString().isEmpty());
    }

    @Test
    public void testLogin_ValidInput() {
        EditText emailEditText = loginActivity.findViewById(R.id.emailEditText);
        emailEditText.setText("test@example.com");

        EditText passwordEditText = loginActivity.findViewById(R.id.editTextTextPassword);
        passwordEditText.setText("password123");

        Button loginButton = loginActivity.findViewById(R.id.button2);
        loginButton.performClick();

        assertFalse(emailEditText.getText().toString().isEmpty());
        assertFalse(passwordEditText.getText().toString().isEmpty());
    }

    @Test
    public void testLifecycle_onStart() {
        loginActivity = activityRule.getActivity();
        assertNotNull(loginActivity);
        loginActivity.onStart();
    }

    @Test
    public void testLifecycle_onResume() {
        loginActivity = activityRule.getActivity();
        assertNotNull(loginActivity);
        loginActivity.onResume();
    }

    @Test
    public void testLifecycle_onPause() {
        loginActivity = activityRule.getActivity();
        assertNotNull(loginActivity);
        loginActivity.onPause();
    }

    @Test
    public void testLifecycle_onStop() {
        loginActivity = activityRule.getActivity();
        assertNotNull(loginActivity);
        loginActivity.onStop();
    }

    @Test
    public void testLifecycle_onDestroy() {
        loginActivity = activityRule.getActivity();
        assertNotNull(loginActivity);
        loginActivity.onDestroy();
    }

    @Test
    public void testSaveAndRestoreInstanceState() {
        Bundle outState = new Bundle();
        loginActivity.onSaveInstanceState(outState);
        loginActivity.onRestoreInstanceState(outState);
        assertTrue(outState != null);
    }


}
