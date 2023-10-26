package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private ImageView btncart,btnNoti,btnMenu,btnChat;
    private EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btncart = findViewById(R.id.btn_cart);
        inputSearch = findViewById(R.id.input_search);
        btnNoti = findViewById(R.id.btn_noti);
        btnChat = findViewById(R.id.btn_chat);
        btnMenu = findViewById(R.id.btn_menu);
//        findViewById(R.id.btn_detail).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
//                startActivity(intent);
//            }
//        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
//        inputSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnNoti.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),NotiActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
//                startActivity(intent);
//            }
//        });
//        btnChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
