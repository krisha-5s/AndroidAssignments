package com.example.androidassignments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ChatDatabaseHelperInstrumentalTest {

     @Rule
    public ActivityTestRule<ChatWindow> activityRule = new ActivityTestRule<>(ChatWindow.class);

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        dbHelper = new ChatDatabaseHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
    }

    @Test
    public void testDatabaseCreation() {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        cursor.moveToFirst();
        assertNotNull("Table not found", cursor.getString(0));
        assertEquals("Table name doesn't match", ChatDatabaseHelper.TABLE_NAME, cursor.getString(0));
        cursor.close();
    }

    @Test
    public void testDatabaseUpgrade() {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, "First message");
        db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
        dbHelper.onUpgrade(db, 2, 3);
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        cursor.moveToFirst();
        assertNotNull("Table not found after upgrade", cursor.getString(0));
        assertEquals("Table name doesn't match after upgrade", ChatDatabaseHelper.TABLE_NAME, cursor.getString(0));
        Cursor dataCursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);
        assertEquals("Data exists after upgrade", 0, dataCursor.getCount());
        cursor.close();
        dataCursor.close();
    }
}
