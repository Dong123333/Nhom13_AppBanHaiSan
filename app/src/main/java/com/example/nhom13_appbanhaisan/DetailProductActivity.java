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

import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView back, imgProduct, imgView1, imgView2, imgView3, imgView4;
    private TextView tenSanPham, donGia, quyCach, tinhTrang, xuatXu, monNgon, soLuong;
    private Button btncart,btnpay, btnGiam, btnTang, btnChon;
    private boolean isCustomBackground = false;

    private int currentQuantity = 0;
    private static final int MAX_QUANTITY = 10;
    private static final int MIN_QUANTITY = 0;


    Product product;
    List<Cart> list;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        //anh xa cac doi tuong
        back=findViewById(R.id.imageView);
        btncart=findViewById(R.id.btnaddcart);
        btnpay=findViewById(R.id.button6);
        soLuong = findViewById(R.id.indexquality);
        //textview
        imgProduct = findViewById(R.id.imgselect);
        tenSanPham = findViewById(R.id.nameproduct);
        donGia = findViewById(R.id.price);
        quyCach = findViewById(R.id.quyCachProduct);
        tinhTrang = findViewById(R.id.tinhTrangProduct);
        xuatXu = findViewById(R.id.xuatXuProduct);
        monNgon = findViewById(R.id.monNgonProduct);
        //button
        btnGiam = findViewById(R.id.btnGiam);
        btnTang = findViewById(R.id.btnTang);
        btnChon = findViewById(R.id.btn_select);
        //hinh anh
        imgView1 = findViewById(R.id.img1);
        imgView2 = findViewById(R.id.img2);
        imgView3 = findViewById(R.id.img3);
        imgView4 = findViewById(R.id.img4);


        //Get value từ trang Home
        Intent intent = getIntent();
        String img = intent.getStringExtra("IMAGE_URL");
        String ten = intent.getStringExtra("TEN");
        String gia = intent.getStringExtra("GIA");
        String quycach = intent.getStringExtra("QUY_CACH");
        String monngon = intent.getStringExtra("MON_NGON");
        String xuatxu = intent.getStringExtra("XUAT_XU");
        String tinhtrang = intent.getStringExtra("TINH_TRANG");
        int soluongcon = intent.getIntExtra("SO_LUONG_CON", 0);
        int soluongmua = Integer.parseInt(soLuong.getText().toString());

        //Hien thi dữ liệu lên Detail
        Picasso.get().load(img).into(imgProduct);
        Picasso.get().load(img).into(imgView1);
        Picasso.get().load(img).into(imgView2);
        Picasso.get().load(img).into(imgView3);
        Picasso.get().load(img).into(imgView4);
        tenSanPham.setText(ten);
        donGia.setText(gia);
        quyCach.setText(quycach);
        tinhTrang.setText(tinhtrang);
        monNgon.setText(monngon);
        xuatXu.setText(xuatxu);
        btnChon.setText(quycach);
        soLuong.setText(String.valueOf(0));
        //thêm sự kiện cho btn
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCustomBackground) {
                    btnChon.setBackgroundResource(R.drawable.btn1);
                } else {
                    btnChon.setBackgroundResource(R.drawable.costum_select);
                }

                // Đảo ngược trạng thái
                isCustomBackground = !isCustomBackground;
            }

        });

        //tăng giảm số lượng
        updateButtons();

        btnGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });

        btnTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });



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
            cart.setQuyCach(product.getQuy_cach());
            cart.setGia(product.getGia());
            cart.setSoCan(Long.parseLong(soLuong.getText().toString()));
            list.add(cart);
        }
    }

    private void decreaseQuantity() {
        if (currentQuantity > MIN_QUANTITY) {
            currentQuantity--;
            soLuong.setText(String.valueOf(currentQuantity));
        }
        updateButtons();
    }

    private void increaseQuantity() {
        if (currentQuantity < MAX_QUANTITY) {
            currentQuantity++;
            soLuong.setText(String.valueOf(currentQuantity));
        }
        updateButtons();
    }

    private void updateButtons() {
        btnGiam.setEnabled(currentQuantity > MIN_QUANTITY);
        btnTang.setEnabled(currentQuantity < MAX_QUANTITY);
    }
}
