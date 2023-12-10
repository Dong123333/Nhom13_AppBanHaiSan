package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.MessageAdapter;
import com.example.nhom13_appbanhaisan.Model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ImageView back,gui;
    ListView listView;
    List<Message> list;
    MessageAdapter adapter;
    private EditText text;
    private String currentUserId;
    private String adminId="eh5K9AWkIbgfddcFkGz7wRnLZM52";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        back = findViewById(R.id.backChat);
        text = findViewById(R.id.editTextMess);
        listView = findViewById(R.id.listMessage);
        gui = findViewById(R.id.send);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
            list = new ArrayList<>();
            adapter = new MessageAdapter(ChatActivity.this, list,currentUserId);
            listView.setAdapter(adapter);
            gui.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendMessage();
                }
            });

            DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages");
            String chatKey = generateChatKey(currentUserId,adminId);
            DatabaseReference chatRef = messagesRef.child(chatKey);
            chatRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    message.setId(dataSnapshot.getKey());
                    list.add(message);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }

            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }else {
            Intent intent = new Intent(ChatActivity.this, LoginActivity.class);
            startActivity(intent);
        }



    }

    private void sendMessage() {
        String messageText = text.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String chatKey = generateChatKey(currentUserId, adminId);
            DatabaseReference messagesRef = FirebaseDatabase.getInstance().getReference("messages").child(chatKey);
            Message message = new Message();
            message.setSenderId(currentUserId);
            message.setText(messageText);

            messagesRef.push().setValue(message);

            text.setText("");
        }
    }

    private String generateChatKey(String uid1, String uid2) {
        List<String> uids = new ArrayList<>();
        uids.add(uid1);
        uids.add(uid2);
        Collections.sort(uids);
        return TextUtils.join("_", uids);
    }

}
