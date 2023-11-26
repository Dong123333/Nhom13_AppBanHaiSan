package com.example.nhom13_appbanhaisan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private ImageView back, xemmk;
    private EditText txtSdt,txtPw;
    private Button btnlogin;
    boolean isABoolean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        back=findViewById(R.id.back);
        txtSdt=findViewById(R.id.sdt);
        txtPw=findViewById(R.id.mk);
        btnlogin=findViewById(R.id.btndangnhap);
        xemmk = findViewById(R.id.xemmk);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),GetStartedActivity.class);
                startActivity(intent);
            }
        });
        xemmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isABoolean){
                    isABoolean = true;
                    txtPw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk.setImageResource(R.drawable.removeeye);
                }
                else{
                    isABoolean = false;
                    txtPw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    xemmk.setImageResource(R.drawable.xemmk);
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users");
            @Override
            public void onClick(View v) {
                String sdttxt = txtSdt.getText().toString();
                String passtxt = txtPw.getText().toString();
                if(TextUtils.isEmpty(sdttxt)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passtxt)){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    reference.orderByChild("so_dien_thoai").equalTo(sdttxt).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Tài khoản tồn tại, kiểm tra mật khẩu
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String getpass = dataSnapshot.child("mat_khau").getValue(String.class);
                                    if (getpass.equals(passtxt)) {
                                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                                        if(sdttxt.equals("admin") && passtxt.equals("admin")){
                                            Intent intent = new Intent(getApplicationContext(), UserAccountManagementActivity.class);
                                            startActivity(intent);
                                        }else{
                                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Mật khẩu sai!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Số điện thoại sai!", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

        });

    }

}