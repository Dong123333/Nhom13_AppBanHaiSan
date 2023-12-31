package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private ImageView back, xemmk;
    private EditText txtPassword, txtEmail;
    private Button btnlogin;
    private TextView quenmk,signup;
    boolean isABoolean;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back = findViewById(R.id.btnback);
        txtEmail = findViewById(R.id.sdt);
        txtPassword = findViewById(R.id.mk);
        btnlogin = findViewById(R.id.btndangnhap);
        xemmk = findViewById(R.id.xemmk);
        quenmk =findViewById(R.id.quenmk);
        signup = findViewById(R.id.btnSignup);
        mAuth = FirebaseAuth.getInstance();
        SharedPreferences preferences = getSharedPreferences("your_prefs", MODE_PRIVATE);
        String savedEmail = preferences.getString("saved_email", "");
        txtEmail.setText(savedEmail);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(),GetStartedActivity.class);
               startActivity(intent);
            }
        });
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        xemmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isABoolean) {
                    isABoolean = true;
                    txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk.setImageResource(R.drawable.removeeye);
                } else {
                    isABoolean = false;
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    xemmk.setImageResource(R.drawable.xemmk);
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                } else {
                   mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            String userId = user.getUid();
                                            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("account");
                                            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        if (("admin@gmail.com").trim().equals(user.getEmail())) {
                                                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(LoginActivity.this, UserAccountManagementActivity.class);
                                                            startActivity(intent);
                                                        } else if (email.equals(user.getEmail())) {
                                                            Toast.makeText(LoginActivity.this,"Đăng nhập thành công",Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

                }
        });
    }
}