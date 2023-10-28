package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Adapter.CartAdapter;
import com.example.nhom13_appbanhaisan.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private ImageView btnback;
    private RecyclerView recyclerView;
    private TextView tongTien;
    private Button muaHang;
    List<Cart> list;
    CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnback = findViewById(R.id.btn_back);
        recyclerView = findViewById(R.id.listCart);
        tongTien = findViewById(R.id.tongTien);
        muaHang = findViewById(R.id.muaHang);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        list = new ArrayList<>();
        adapter = new CartAdapter(getApplicationContext(),list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
