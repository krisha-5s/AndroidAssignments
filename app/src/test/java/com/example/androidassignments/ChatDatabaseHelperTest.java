package com.example.androidassignments;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class ChatDatabaseHelperTest {
    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dbHelper = new ChatDatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        dbHelper.close();
    }

    @Test
    public void testDatabaseCreation() {
        assertNotNull("Database is null", db);
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue("Table does not exist", cursor.moveToFirst());
        cursor.close();
    }

    @Test
    public void testInsertMessage() {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, "Hello, World!");
        long id = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

        assertTrue("Insert failed", id != -1);
    }

    @Test
    public void testRetrieveMessage() {
        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, "Hello, Robolectric!");
        long id = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

        // Query the database for the inserted message
        Cursor cursor = db.query(ChatDatabaseHelper.TABLE_NAME, null, ChatDatabaseHelper.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);

        assertTrue("No record found", cursor.moveToFirst());
        assertEquals("Hello, Robolectric!", cursor.getString(cursor.getColumnIndexOrThrow(ChatDatabaseHelper.KEY_MESSAGE)));
        cursor.close();
    }

    @Test
    public void testUpgradeDatabase() {
        dbHelper.onUpgrade(db, 1, 2);
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + ChatDatabaseHelper.TABLE_NAME + "';", null);
        assertTrue("Table should be recreated after upgrade", cursor.moveToFirst());
        cursor.close();
    }
}
