package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.nhom13_appbanhaisan.Fragment.WaitForConfirmationFragment;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class PayActivity extends AppCompatActivity {
    private ImageView back;
    private Button btnxacnhan;
    private CountDownTimer countDownTimer;
    private final long startTimeInMillis = 10 * 60 * 1000;
    private TextView thoigian ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        btnxacnhan=findViewById(R.id.xacnhan);
        back = findViewById(R.id.btnback);
        Intent intent = getIntent();
        List<String> id = intent.getStringArrayListExtra("SELECTED_ITEMS_ID_PAY");
        List<Cart> selectedItems = intent.getParcelableArrayListExtra("SELECTED_ITEMS_PAY");
        String tientt = intent.getStringExtra("TONGTIEN");
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
                finish();
            }
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0 ; i < id.size(); i++){
                    String idCart = id.get(i);
                    deleteProductFromFirebase(idCart);
                }
                Intent newIntent = new Intent(getApplicationContext(), PurchaseOrderActivity.class);
                newIntent.putParcelableArrayListExtra("SELECTED_ITEMS_PURCHASE", (ArrayList<? extends Parcelable>) selectedItems);
                startActivity(newIntent);
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
    private void deleteProductFromFirebase(String productId) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
            userCartRef.child(productId).removeValue();
        }
    }
}
