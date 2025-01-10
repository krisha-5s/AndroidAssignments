package com.example.androidassignments;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MessageDetails extends AppCompatActivity {

    private long messageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        FrameLayout frameLayout = findViewById(R.id.fragmentContainer);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            messageId = bundle.getLong("messageId");

            MessageFragment fragment;
            if (getResources().getBoolean(R.bool.isTablet)) {
                fragment = new MessageFragment((ChatWindow) getParent());
            } else {
                fragment = new MessageFragment((ChatWindow) getParent());
            }

            fragment.setArguments(bundle);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragmentContainer, fragment);
            ft.commit();
        }


    }
}
