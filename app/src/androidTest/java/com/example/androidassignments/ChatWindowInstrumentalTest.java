package com.example.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ChatWindowInstrumentalTest {

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        db.delete(ChatDatabaseHelper.TABLE_NAME, null, null);
        ActivityScenario.launch(ChatWindow.class);
    }

    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void testSendMessageAndDisplayInListView() {
        String testMessage = "Hello, this is a test message!";
        onView(withId(R.id.msgEditText)).perform(typeText(testMessage), closeSoftKeyboard());
        onView(withId(R.id.send_btn)).perform(click());
        onView(allOf(withId(R.id.chatview), withText(testMessage)))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testDatabasePersistence() {
        String message1 = "First message";
        String message2 = "Second message";

        // Send first message
        onView(withId(R.id.msgEditText)).perform(typeText(message1), closeSoftKeyboard());
        onView(withId(R.id.send_btn)).perform(click());

        // Send second message
        onView(withId(R.id.msgEditText)).perform(typeText(message2), closeSoftKeyboard());
        onView(withId(R.id.send_btn)).perform(click());
        ActivityScenario.launch(ChatWindow.class);
        onView(allOf(withId(R.id.chatview), withText(message1)))
                .check(ViewAssertions.matches(isDisplayed()));
        onView(allOf(withId(R.id.chatview), withText(message2)))
                .check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testEmptyMessageNotSent() {
        onView(withId(R.id.msgEditText)).perform(typeText(""), closeSoftKeyboard());
        onView(withId(R.id.send_btn)).perform(click());
        onView(withId(R.id.chatListView))
                .check(ViewAssertions.matches(hasChildCount(0)));
    }
}
