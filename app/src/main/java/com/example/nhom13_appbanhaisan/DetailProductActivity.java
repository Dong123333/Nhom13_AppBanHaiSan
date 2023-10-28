package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView back;
    private TextView soLuong;
    private Button btncart,btnpay;
    Product product;
    List<Cart> list;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);
        back=findViewById(R.id.imageView);
        btncart=findViewById(R.id.btnaddcart);
        btnpay=findViewById(R.id.button6);
        soLuong = findViewById(R.id.indexquality);
        list = new ArrayList<>();
        product = new Product();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);

            }
        });
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),OrderActivity.class);
                startActivity(intent);
            }
        });

    }
    public void addCart(){
        if(list.size()>0) {

        }else {
            Cart cart = new Cart();
            cart.setAnh(product.getAnh());
            cart.setTen(product.getTen_san_pham());
            cart.setQuyCach(product.getQuyCach());
            cart.setGia(product.getGia());
            cart.setSoCan(Long.parseLong(soLuong.getText().toString()));
            list.add(cart);
        }
    }
}
