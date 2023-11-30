package com.example.nhom13_appbanhaisan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nhom13_appbanhaisan.Model.Users;
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

public class SignupActivity extends AppCompatActivity {
    private Button btndangki;
    private ImageView back, xemmk1, xemmk2;
    private EditText txtPassword, txtConfirmPassword, txtEmail, txtFullName;
    boolean isABoolean;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btndangki = findViewById(R.id.btndangki);
        back=findViewById(R.id.btnback);
        xemmk1 = findViewById(R.id.xemmk1);
        xemmk2 = findViewById(R.id.xemmk2);
        txtEmail = findViewById(R.id.sdt);
        txtFullName = findViewById(R.id.hoten);
        txtPassword = findViewById(R.id.password1);
        txtConfirmPassword = findViewById(R.id.password2);
        checkBox = findViewById(R.id.checkboxdk);
        xemmk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isABoolean){
                    isABoolean = true;
                    txtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk1.setImageResource(R.drawable.removeeye);
                }
                else {
                    isABoolean = false;
                    txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    xemmk1.setImageResource(R.drawable.xemmk);
                }
            }
        });
        xemmk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isABoolean){
                    isABoolean = true;
                    txtConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk2.setImageResource(R.drawable.removeeye);
                }
                else {
                    isABoolean = false;
                    txtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    xemmk2.setImageResource(R.drawable.xemmk);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GetStartedActivity.class);
                startActivity(intent);
            }
        });

        btndangki.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = txtEmail.getText().toString();
                    String password = txtPassword.getText().toString();
                    String confirmPassword = txtConfirmPassword.getText().toString();
                    String fullName = txtFullName.getText().toString();

                    if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(fullName)) {
                        // Kiểm tra xem các trường đã được nhập đầy đủ hay chưa
                        Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    } else if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Mật khẩu cần ít nhất 6 ký tự!", Toast.LENGTH_LONG).show();
                        
                    } else if (!password.equals(confirmPassword)) {
                        // Kiểm tra xem mật khẩu và mật khẩu xác nhận có khớp nhau không
                        Toast.makeText(SignupActivity.this, "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    } else if (!checkBox.isChecked()) {
                        Toast.makeText(SignupActivity.this, "Vui lòng chấp nhận điều khoản!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Thực hiện đăng ký tài khoản trong Firebase Authentication
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Lưu thông tin người dùng vào Realtime Database
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if (user != null) {
                                                String userId = user.getUid();
                                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("account");
                                                Users users = new Users(fullName,email,password);
                                                userRef.setValue(users);
                                            }
                                            Toast.makeText(SignupActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                            startActivity(intent);

                                        } else {
                                            Toast.makeText(SignupActivity.this, "Đã có tài khoản này", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            });
        }
    }