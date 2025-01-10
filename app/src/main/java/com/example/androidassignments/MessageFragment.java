package com.example.androidassignments;

import android.app.Activity;  // Importing the Activity class to access RESULT_OK
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class MessageFragment extends Fragment {

    private ChatWindow chatWindow;
    private TextView messageTextView;
    private TextView messageIdTextView;
    private Button deleteButton;

    public MessageFragment(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View rootView = inflater.inflate(R.layout.fragment_message_details, container, false);

        messageTextView = rootView.findViewById(R.id.messageTextView);
        messageIdTextView = rootView.findViewById(R.id.messageIdTextView);
        deleteButton = rootView.findViewById(R.id.deleteMessageButton);

        Bundle args = getArguments();
        if (args != null) {
            String message = args.getString("message");
            long messageId = args.getLong("messageId");
            messageTextView.setText(message);
            messageIdTextView.setText("ID: " + messageId);

            deleteButton.setOnClickListener(v -> {
                if (chatWindow != null) {
                    chatWindow.deleteMessage(messageId);
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("messageId", messageId);
                    getActivity().setResult(Activity.RESULT_OK, resultIntent);
                    getActivity().finish();
                }
            });
        }

        return rootView;
    }
}
