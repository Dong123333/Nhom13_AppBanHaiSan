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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhom13_appbanhaisan.Adapter.ManagerAccountAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ManagerProductAdapter;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagerAccountActivity extends AppCompatActivity {
    ImageView back;
    ListView listView;
    ArrayList<Users> list;
    ManagerAccountAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageraccount_activity);
        listView = findViewById(R.id.listManagerAccount);
        back = findViewById(R.id.btnbackAccount);
        list = new ArrayList<>();
        adapter = new ManagerAccountAdapter(ManagerAccountActivity.this,R.layout.account_layout,list);
        listView.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getValueProductFromFirebase();
    }
    public void getValueProductFromFirebase() {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    DataSnapshot accountSnapshot = userSnapshot.child("account");
                    if (accountSnapshot.exists()) {
                        String fullName = accountSnapshot.child("fullName").getValue(String.class);
                        String gender = accountSnapshot.child("gender").getValue(String.class);
                        String date = accountSnapshot.child("date").getValue(String.class);
                        String phone = accountSnapshot.child("phone").getValue(String.class);
                        Users users = new Users();
                        users.setFullName(fullName);
                        users.setGender(gender);
                        users.setDate(date);
                        users.setPhone(phone);
                        list.add(users);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
