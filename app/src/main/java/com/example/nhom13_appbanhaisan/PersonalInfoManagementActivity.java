package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PersonalInfoManagementActivity extends AppCompatActivity {
    private ImageView btnhome,btncart,btnNoti,btnMenu,btnChat, btnSetting,btnFavourite,imageUser;
    private Button btnlogout,login,signup;
    private LinearLayout thietLap;
    private TextView hovaten;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_infor_management);
        btncart = findViewById(R.id.btn_cart);
        btnhome=findViewById(R.id.btn_home);
        btnNoti = findViewById(R.id.btn_notif);
        btnMenu = findViewById(R.id.btn_menu);
        btnChat=findViewById(R.id.imageView4);
        btnlogout=findViewById(R.id.logout);
        btnFavourite = findViewById(R.id.btn_favourite);
        btnSetting = findViewById(R.id.setting);
        hovaten = findViewById(R.id.hoten);
        login = findViewById(R.id.loginUser);
        signup = findViewById(R.id.signupUser);
        imageUser = findViewById(R.id.imageUser);
        thietLap = findViewById(R.id.thietlap);
        thietLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SettingActivity.class);
                startActivity(intent);
            }
        });


        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalInfoManagementActivity.class);
                startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FavouriteActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.choxacnhan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PurchaseOrderActivity.class);
                startActivity(intent);
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if(user != null){
                    mAuth.signOut();
                    Intent intent = new Intent(PersonalInfoManagementActivity.this, GetStartedActivity.class);
                    startActivity(intent);
                    SharedPreferences preferences = getSharedPreferences("your_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("saved_email", user.getEmail());
                    editor.apply();
                }
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       if (currentUser == null){
           findViewById(R.id.nameUser).setVisibility(View.GONE);
           findViewById(R.id.loginandregister).setVisibility(View.VISIBLE);
           btnlogout.setVisibility(View.GONE);
           login.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                   startActivity(intent);
               }
           });
           signup.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                   startActivity(intent);
               }
           });
       }
       else {
           findViewById(R.id.nameUser).setVisibility(View.VISIBLE);
           findViewById(R.id.loginandregister).setVisibility(View.GONE);
           btnlogout.setVisibility(View.VISIBLE);
           String id = currentUser.getUid();
           getValueNameUserFirebase(id);
           imageUser.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(getApplicationContext(),InsertUserActivity.class);
                   startActivity(intent);
               }
           });
       }

    }
    public void getValueNameUserFirebase(String id) {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(id).child("account");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("fullName").getValue(String.class);
                    String image = snapshot.child("image").getValue(String.class);
                    hovaten.setText(name);
                    if (image != null && !image.isEmpty()){
                        Glide.with(PersonalInfoManagementActivity.this)
                                .load(image)
                                .apply(new RequestOptions()
                                        .override(800, 800)
                                        .fitCenter())
                                .into(imageUser);
                    }else{
                        imageUser.setImageResource(R.drawable.user);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
