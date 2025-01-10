package com.example.androidassignments;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    private static final String ACTIVITY_NAME = "ChatWindow";
    private ListView chatListView;
    private EditText msgEditText;
    private Button send_btn;
    private ArrayList<String> chatMsg;
    private ChatAdapter chatAdapter;

    private ChatDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    private static final int REQUEST_CODE_DELETE = 1;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatListView = findViewById(R.id.chatListView);
        msgEditText = findViewById(R.id.msgEditText);
        send_btn = findViewById(R.id.send_btn);

        chatMsg = new ArrayList<>();
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();

        loadChatMessages();

        chatAdapter = new ChatAdapter(this, chatMsg);
        chatListView.setAdapter(chatAdapter);

        send_btn.setOnClickListener(v -> {
            String message = msgEditText.getText().toString();
            if (!message.isEmpty()) {
                ContentValues values = new ContentValues();
                values.put(ChatDatabaseHelper.KEY_MESSAGE, message);
                long id = db.insert(ChatDatabaseHelper.TABLE_NAME, null, values);

                chatMsg.add(message);
                chatAdapter.notifyDataSetChanged();
                msgEditText.setText("");

                Log.i(ACTIVITY_NAME, "Message saved to database with ID: " + id);
            }
        });

        chatListView.setOnItemClickListener((parent, view, position, id) -> {
            boolean isTabletLayout = findViewById(R.id.frameLayout) != null;
            Bundle bundle = new Bundle();
            String message = chatMsg.get(position);  // Use actual message from the list
            bundle.putString("message", message);
            bundle.putLong("messageId", id);

            if (isTabletLayout) {
                MessageFragment fragment = new MessageFragment(this);
                fragment.setArguments(bundle);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, fragment);
                ft.commit();
            } else {
                Intent intent = new Intent(ChatWindow.this, MessageDetails.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CODE_DELETE);
            }
        });
    }


    private void loadChatMessages() {
        cursor = db.query(ChatDatabaseHelper.TABLE_NAME,
                new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null);

        Log.i(ACTIVITY_NAME, "Cursor’s column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column name: " + cursor.getColumnName(i));
        }

        chatMsg.clear();
        while (cursor.moveToNext()) {
            @SuppressLint("Range")
            String message = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            chatMsg.add(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
        if (db != null) db.close();
    }

    public void deleteMessage(long messageId) {
        db.delete(ChatDatabaseHelper.TABLE_NAME,
                ChatDatabaseHelper.KEY_ID + " = ?",
                new String[]{String.valueOf(messageId)});

        if (findViewById(R.id.frameLayout) != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentById(R.id.frameLayout));
            ft.commit();
        }
        loadChatMessages();
        chatAdapter.notifyDataSetChanged();
    }

    private void updateListView() {
        loadChatMessages();
        chatAdapter.notifyDataSetChanged();
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        private Cursor cursor;
        public ChatAdapter(Context ctx, ArrayList<String> chatMsg) {
            super(ctx, 0, chatMsg);
        }

        @Override
        public int getCount() {
            return chatMsg.size();
        }

        @Override
        public String getItem(int position) {
            return chatMsg.get(position);
        }

        @Override
        public long getItemId(int position) {
            dbHelper = new ChatDatabaseHelper(getContext());
            db = dbHelper.getWritableDatabase();
            cursor = db.query(ChatDatabaseHelper.TABLE_NAME,
                    new String[]{ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                    null, null, null, null, null);
            long curr_index = -1;
            if (cursor != null && cursor.moveToPosition(position)) {
                int idColumnIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID);
                if (idColumnIndex != -1) {
                    curr_index = cursor.getLong(idColumnIndex);
                }
            }
            return curr_index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View result;
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            if (position % 2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            } else {
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            TextView message = result.findViewById(R.id.chatview);
            message.setText(getItem(position));
            return result;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DELETE && resultCode == RESULT_OK) {
            long messageId = data.getLongExtra("messageId", -1);

            if (messageId != -1) {
                deleteMessage(messageId);
                updateListView();
            }
        }
    }
}
