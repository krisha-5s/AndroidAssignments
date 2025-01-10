package com.example.androidassignments;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class ChatWindowTest {

    private ChatWindow chatWindow;
    private ActivityController<ChatWindow> controller;
    private EditText msgEditText;
    private Button sendButton;
    private ListView chatListView;

    @Before
    public void setUp() {
        controller = Robolectric.buildActivity(ChatWindow.class).create().start().resume();
        chatWindow = controller.get();

        msgEditText = chatWindow.findViewById(R.id.msgEditText);
        sendButton = chatWindow.findViewById(R.id.send_btn);
        chatListView = chatWindow.findViewById(R.id.chatListView);
        assertNotNull(chatWindow.chatMsg);
        chatWindow.chatMsg = new ArrayList<>();
    }

    @After
    public void tearDown() {
        controller.pause().stop().destroy();
    }

    @Test
    public void testSendButtonAddsMessage() {
        assertNotNull(chatListView);
        assertNotNull(msgEditText);
        assertNotNull(sendButton);
        msgEditText.setText("Hello World");
        sendButton.performClick();
        assertEquals(1, chatWindow.chatMsg.size());
        assertEquals("Hello World", chatWindow.chatMsg.get(0));
        assertEquals("", msgEditText.getText().toString());
    }

    @Test
    public void testEditTextClearedAfterSend() {
        String testMessage = "Test message";
        msgEditText.setText(testMessage);
        sendButton.performClick();
        assertEquals("", msgEditText.getText().toString());
    }

    @Test
    public void testMultipleMessages() {
        msgEditText.setText("First message");
        sendButton.performClick();
        msgEditText.setText("Second message");
        sendButton.performClick();
        assertEquals(2, chatWindow.chatMsg.size());
        assertEquals("First message", chatWindow.chatMsg.get(0));
        assertEquals("Second message", chatWindow.chatMsg.get(1));
        assertEquals(2, chatListView.getAdapter().getCount());
    }

    @Test
    public void testSendButtonIgnoresEmptyMessages() {
        msgEditText.setText("");
        sendButton.performClick();
        assertEquals(0, chatWindow.chatMsg.size());
        assertEquals(0, chatListView.getAdapter().getCount());
    }

    @Test
    public void testListViewDisplaysMessagesCorrectly() {
        String message1 = "Hello!";
        msgEditText.setText(message1);
        sendButton.performClick();
        String message2 = "How are you?";
        msgEditText.setText(message2);
        sendButton.performClick();
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) chatListView.getAdapter();
        assertNotNull(adapter);
        assertEquals(2, adapter.getCount());
        assertEquals(message1, adapter.getItem(0));
        assertEquals(message2, adapter.getItem(1));
        assertEquals(View.VISIBLE, chatListView.getVisibility());
    }
}
