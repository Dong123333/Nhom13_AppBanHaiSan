package com.example.nhom13_appbanhaisan;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class UserAccountManagementActivity extends  AppCompatActivity{
    private Button button;
    private LinearLayout btnmanagerProduct,btnmanagerAccount,btnmanagerMS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_management);
        button = findViewById(R.id.btndangxuatadmin);
        btnmanagerProduct = findViewById(R.id.managerProduct);
        btnmanagerAccount = findViewById(R.id.managerAccount);
        btnmanagerMS = findViewById(R.id.managerMS);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent = new Intent(UserAccountManagementActivity.this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnmanagerProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccountManagementActivity.this, ManagerProductActivity.class);
                startActivity(intent);
            }
        });
        btnmanagerAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccountManagementActivity.this, ManagerAccountActivity.class);
                startActivity(intent);
            }
        });
        btnmanagerMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserAccountManagementActivity.this, ManagerMSActivity.class);
                startActivity(intent);
            }
        });
    }

}
