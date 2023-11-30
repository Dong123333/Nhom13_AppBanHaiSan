package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText editTextEmail;
    Button layLaiMatKhau;
    ImageView back;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        editTextEmail = findViewById(R.id.email);
        layLaiMatKhau = findViewById(R.id.btnForget);
        back = findViewById(R.id.backForget);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layLaiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Vui lòng nhập địa chỉ email của bạn", Toast.LENGTH_SHORT).show();
                } else {
                    // Gửi yêu cầu đặt lại mật khẩu
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(ForgetPasswordActivity.this, "Yêu cầu đặt lại mật khẩu đã được gửi đến email của bạn", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Đã xảy ra lỗi
                                        Toast.makeText(ForgetPasswordActivity.this, "Email sai!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
