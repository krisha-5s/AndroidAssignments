package com.example.androidassignments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowToast;

import static org.junit.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);

    private MainActivity mainActivity;
    private Button helloButton;
    private Button startChatButton;

    @Before
    public void setUp() throws Exception {
        mainActivity = activityRule.getActivity();
        helloButton = mainActivity.findViewById(R.id.button);
        startChatButton = mainActivity.findViewById(R.id.button5);

        assertNotNull( helloButton);
        assertNotNull(startChatButton);
    }

    @After
    public void tearDown() throws Exception {
        mainActivity = null;
        helloButton = null;
        startChatButton = null;
    }

    @Test
    public void onCreate() {
        assertNotNull(mainActivity);
        assertNotNull( helloButton);
        assertNotNull(startChatButton);
        Log.d("MainActivityTest", "onCreate executed");
    }

    @Test
    public void onActivityResult() {
        Intent data = new Intent();
        data.putExtra("Response", "Success");

        mainActivity.onActivityResult(MainActivity.REQUEST_CODE, Activity.RESULT_OK, data);
        String toastMessage = ShadowToast.getTextOfLatestToast();

        assertNotNull("Toast should be displayed", toastMessage);
        assertEquals("Success", toastMessage);
    }

    @Test
    public void testHelloButtonNavigation() {
        helloButton.performClick();

        Intent expectedIntent = new Intent(mainActivity, ListItemsActivity.class);
        Intent actualIntent = shadowOf(mainActivity).getNextStartedActivity();

        assertNotNull(actualIntent);
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
        assertEquals(expectedIntent.getFlags(), actualIntent.getFlags());
        assertEquals(expectedIntent.getAction(), actualIntent.getAction());
        assertEquals(expectedIntent.getCategories(), actualIntent.getCategories());
    }

    @Test
    public void testStartChatButton() {
        startChatButton.performClick();

        Intent expectedIntent = new Intent(mainActivity, ChatWindow.class);
        Intent actualIntent = shadowOf(mainActivity).getNextStartedActivity();

        assertNotNull(actualIntent);
        assertEquals(expectedIntent.getComponent(), actualIntent.getComponent());
        assertEquals(expectedIntent.getFlags(), actualIntent.getFlags());
        assertEquals(expectedIntent.getAction(), actualIntent.getAction());
        assertEquals(expectedIntent.getCategories(), actualIntent.getCategories());
    }
    @Test
    public void testLifecycle_onStart() {
        mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity);
        mainActivity.onStart();
    }

    @Test
    public void testLifecycle_onResume() {
        mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity);
        mainActivity.onResume();
    }

    @Test
    public void testLifecycle_onPause() {
        mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity);
        mainActivity.onPause();
    }

    @Test
    public void testLifecycle_onStop() {
        mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity);
        mainActivity.onStop();
    }

    @Test
    public void testLifecycle_onDestroy() {
        mainActivity = activityRule.getActivity();
        assertNotNull(mainActivity);
        mainActivity.onDestroy();
    }

    @Test
    public void testSaveAndRestoreInstanceState() {
        Bundle outState = new Bundle();
        mainActivity.onSaveInstanceState(outState);
        mainActivity.onRestoreInstanceState(outState);
        assertTrue(outState != null);
    }
}

