package com.example.androidassignments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Messages.db";
    public static final int VERSION_NUM = 2;
    public static final String TABLE_NAME = "chatMessages";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";

    private static final String TAG = "ChatDatabaseHelper";

    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating database table...");
         String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_MESSAGE + " TEXT);";
        db.execSQL(CREATE_TABLE);
        Log.i(TAG, "Database table created successfully.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database from version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Log.i(TAG, "Database upgrade complete.");
    }

 public void insertMessage(SQLiteDatabase db, String message) {
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE, message);
        db.insert(TABLE_NAME, null, values);
    }

    public Cursor getAllMessages(SQLiteDatabase db) {
         return db.query(TABLE_NAME, new String[]{KEY_ID, KEY_MESSAGE}, null, null, null, null, null);
    }

    public void deleteMessage(SQLiteDatabase db, long messageId) {
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(messageId)});
    }
}
