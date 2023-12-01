package com.example.nhom13_appbanhaisan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser == null) {
                    Intent intent = new Intent(SplashScreenActivity.this, GetStartedActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    if (currentUser.getEmail().equals("admin@gmail.com")){
                        Intent intent = new Intent(SplashScreenActivity.this, UserAccountManagementActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        },3000);
    }
}