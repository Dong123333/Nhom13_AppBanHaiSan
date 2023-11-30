package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.nhom13_appbanhaisan.Fragment.WaitForConfirmationFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
public class PayActivity extends AppCompatActivity {
    private Button btnxacnhan;
    private CountDownTimer countDownTimer;
    private final long startTimeInMillis = 10 * 60 * 1000;
    private TextView thoigian ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ImageView back =findViewById(R.id.btnback);
        btnxacnhan=findViewById(R.id.xacnhan);
        Intent intent = getIntent();
        int id = intent.getIntExtra("ID",0);
        String name = intent.getStringExtra("TEN");
        String anh = intent.getStringExtra("ANH");
        String quycach = intent.getStringExtra("QUYCACH");
        int gia = intent.getIntExtra("GIA", 0);
        int can = intent.getIntExtra("CAN", 0);
        int tienchuaformat = intent.getIntExtra("tongtienchuaformat",0);
        String tientt = intent.getStringExtra("tongtien");
        TextView tien = findViewById(R.id.tientra) ;
        tien.setText(tientt) ;
        TextView madonhang = findViewById(R.id.madh) ;
         thoigian = findViewById(R.id.time);



        String randomString = generateRandomString(7); //
        madonhang.setText(randomString);
        startCountdownTimer();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProductFromFirebase(id);
                Intent intent = new Intent(getApplicationContext(), PurchaseOrderActivity.class);
                intent.putExtra("TEN", name);
                intent.putExtra("GIA", gia);
                intent.putExtra("CAN", can);
                intent.putExtra("TONG", tienchuaformat);
                intent.putExtra("ANH", anh);
                intent.putExtra("QUYCACH", quycach);
                startActivity(intent);
            }
        });
    }
    private String generateRandomString(int length) {
        // Chuỗi ký tự có thể sử dụng để tạo chuỗi ngẫu nhiên
        String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        // Tạo chuỗi ngẫu nhiên với độ dài mong muốn
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            randomString.append(characterSet.charAt(randomIndex));
        }

        return randomString.toString();
    }
    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(startTimeInMillis, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                long secondsLeft = millisUntilFinished / 1000;
                long minutes = secondsLeft / 60;
                long seconds = secondsLeft % 60;

                String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
                thoigian.setText(timeLeftFormatted);
            }

            @Override
            public void onFinish() {
                thoigian.setText("00:00");
                goOder();

            }
        }.start();
    }
    private void goOder() {
        Intent intent = new Intent(this, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
    private void deleteProductFromFirebase(int productId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
            userCartRef.child(String.valueOf(productId)).removeValue();
        }
    }
}
