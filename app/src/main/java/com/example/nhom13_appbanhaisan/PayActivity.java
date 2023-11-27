package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
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
}
