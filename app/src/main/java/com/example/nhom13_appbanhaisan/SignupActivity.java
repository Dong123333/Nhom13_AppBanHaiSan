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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private Button btndangki;
    private ImageView back, xemmk1, xemmk2;
    private EditText txtPw1, txtPw2, txtsdt, txthoten;
    boolean isABoolean;
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btndangki = findViewById(R.id.btndangki);
        back=findViewById(R.id.back);
        xemmk1 = findViewById(R.id.xemmk1);
        xemmk2 = findViewById(R.id.xemmk2);
        txtsdt = findViewById(R.id.sdt);
        txthoten = findViewById(R.id.hoten);
        txtPw1 = findViewById(R.id.password1);
        txtPw2 = findViewById(R.id.password2);
        checkBox = findViewById(R.id.checkboxdk);
        xemmk1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isABoolean){
                    isABoolean = true;
                    txtPw1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk1.setImageResource(R.drawable.removeeye);
                }
                else {
                    isABoolean = false;
                    txtPw1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    xemmk1.setImageResource(R.drawable.xemmk);
                }
            }
        });
        xemmk2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isABoolean){
                    isABoolean = true;
                    txtPw2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    xemmk2.setImageResource(R.drawable.removeeye);
                }
                else {
                    isABoolean = false;
                    txtPw2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("users");
            @Override
            public void onClick(View v) {
                String hotentxt = txthoten.getText().toString();
                String sdttxt = txtsdt.getText().toString();
                String mk1txt = txtPw1.getText().toString();
                String mk2txt = txtPw2.getText().toString();
                reference.orderByChild("so_dien_thoai").equalTo(sdttxt).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Số điện thoại đã tồn tại, hiển thị thông báo và dừng kiểm tra
                            Toast.makeText(getApplicationContext(), "Đã có tài khoản với số điện thoại này!", Toast.LENGTH_LONG).show();
                        } else {
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(TextUtils.isEmpty(hotentxt)|TextUtils.isEmpty(sdttxt)|TextUtils.isEmpty(mk1txt)|TextUtils.isEmpty(mk2txt)){
                                        Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các thông tin!", Toast.LENGTH_LONG).show();
                                    }else{
                                        if(!mk1txt.equals(mk2txt)){
                                            Toast.makeText(getApplicationContext(), "Mật khẩu sau khác mật khẩu trước!", Toast.LENGTH_LONG).show();
                                        }else {
                                            if (!checkBox.isChecked()){
                                                Toast.makeText(SignupActivity.this, "Vui lòng chọn chấp nhận điều khoản!", Toast.LENGTH_SHORT).show();
                                            }else{
                                                    Users users = new Users(hotentxt, sdttxt, mk1txt, mk2txt);
                                                    // Đếm số lượng sản phẩm hiện tại để tạo ID mới
                                                    long count = dataSnapshot.getChildrenCount() + 1;

                                                    // Đặt ID mới cho sản phẩm
                                                    users.setId((int) count);

                                                    // Thêm sản phẩm mới vào Firebase Realtime Database
                                                    reference.child(String.valueOf(count)).setValue(users);
                                                    Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                                    startActivity(intent);
                                                    Toast.makeText(getApplicationContext(), "Vui lòng đăng nhập!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Xử lý lỗi nếu có
                        Log.e("Firebase", "Lỗi khi kiểm tra số điện thoại: " + databaseError.getMessage());
                    }
                });
            }
        });
    }
}