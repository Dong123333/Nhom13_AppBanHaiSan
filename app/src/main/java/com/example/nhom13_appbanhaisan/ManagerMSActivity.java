package com.example.nhom13_appbanhaisan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.ManagerMSAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ManagerProductAdapter;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerMSActivity extends AppCompatActivity {
    ImageView back;
    ListView listView;
    List<Users> list;
    List<String> listKey;
    ManagerMSAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_managermessage);
        listView = findViewById(R.id.listManagerMessage);
        back = findViewById(R.id.backManagerChat);
        list = new ArrayList<>();
        listKey = new ArrayList<>();
        adapter = new ManagerMSAdapter(ManagerMSActivity.this,R.layout.managerms_item,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),AdminChatActivity.class);
                String userKey = listKey.get(position);
                intent.putExtra("ID",userKey);
                startActivity(intent);
            }
        });
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String key = userSnapshot.getKey();
                    listKey.add(key);
                    DataSnapshot accountSnapshot = userSnapshot.child("account");
                    if (accountSnapshot.exists()) {
                        String fullName = accountSnapshot.child("fullName").getValue(String.class);
                        String image = accountSnapshot.child("image").getValue(String.class);
                        Users users = new Users();
                        users.setFullName(fullName);
                        users.setImage(image);
                        list.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserAccountManagementActivity.class);
                startActivity(intent);
            }
        });
    }
}
