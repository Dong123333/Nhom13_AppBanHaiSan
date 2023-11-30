package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Model.Cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView back, imgProduct, imgView1, imgView2, imgView3, imgView4;
    private TextView tenSanPham, donGia, quyCach, tinhTrang, xuatXu, monNgon, soLuong;
    private Button btnAddCart, btnGiam, btnTang, btnChon;
    private boolean isCustomBackground = false;

    private int currentQuantity = 0;
    private static final int MAX_QUANTITY = 10;
    private static final int MIN_QUANTITY = 0;
    FirebaseDatabase database;
    DatabaseReference reference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        //anh xa cac doi tuong
        back=findViewById(R.id.imageView);
        btnAddCart=findViewById(R.id.btnaddcart);
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
        int gia = intent.getIntExtra("GIA",0);
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
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        donGia.setText(format.format(gia));
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


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String userId = currentUser.getUid();
                    DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
                    int soCan = Integer.parseInt(soLuong.getText().toString());
                    int soTien = gia * soCan;
                    Cart cart = new Cart(img, ten, quycach, gia, soCan, soTien);
                    userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long count = snapshot.getChildrenCount() + 1;
                            cart.setId((int) count);
                            userCartRef.child(String.valueOf(count)).setValue(cart)
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(DetailProductActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Firebase", "Lỗi khi thêm sản phẩm mới vào Firebase: " + e.getMessage());
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    }
                }
        });
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
