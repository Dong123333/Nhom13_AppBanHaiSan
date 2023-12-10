package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.CommentAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.Model.Comment;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailProductActivity extends AppCompatActivity {
    private ImageView back, imgProduct, gioHang;
    private EditText editTextComment;
    private TextView tenSanPham, donGia, quyCach, tinhTrang, xuatXu, monNgon, soLuong;
    private Button btnGiam, btnTang, btnChon,btnBuy,btnComment;
    private LinearLayout btnAddFavourite,btnAddCart;
    private boolean isCustomBackground = false;
    private ExpandableHeightGridView list;
    private List<Comment> commentList;
    private CommentAdapter adapter;

    private int currentQuantity = 0;
    private static final int MAX_QUANTITY = 10;
    private static final int MIN_QUANTITY = 0;
    FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_product);

        //anh xa cac doi tuong
        back=findViewById(R.id.imageView);
        btnAddCart=findViewById(R.id.addCart);
        btnAddFavourite = findViewById(R.id.addFavourite);
        soLuong = findViewById(R.id.indexquality);
        gioHang = findViewById(R.id.gioHang);
        editTextComment = findViewById(R.id.edtComment);
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
        btnBuy = findViewById(R.id.btnBuy);
        btnComment = findViewById(R.id.btnComment);

        list = (ExpandableHeightGridView) findViewById(R.id.listComment);
        list.setExpanded(true);
        commentList = new ArrayList<>();
        adapter = new CommentAdapter(DetailProductActivity.this,R.layout.comment_layout,commentList);
        list.setAdapter(adapter);
        getComment();

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
        int soluongban = intent.getIntExtra("SO_LUONG_DA_BAN",0);
        int soluongmua = Integer.parseInt(soLuong.getText().toString());

        //Hien thi dữ liệu lên Detail
        Picasso.get().load(img).into(imgProduct);
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
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String userId = currentUser.getUid();
                    DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
                    int soCan = Integer.parseInt(soLuong.getText().toString());
                    if(soCan != 0){
                        int soTien = gia * soCan;
                        userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Cart cart = new Cart(img, ten, quycach, gia, soCan, soTien);
                                userCartRef.push().setValue(cart)
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(DetailProductActivity.this, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e("Firebase", "Lỗi" + e.getMessage());
                                        });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else {
                        Toast.makeText(DetailProductActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        gioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnAddFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String userId = currentUser.getUid();
                    DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("favourite");
                    userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Product product = new Product(img,ten,gia,soluongban,quycach,"Ngon, bổ, rẻ",monngon,tinhtrang,xuatxu,soluongcon);
                            userCartRef.push().setValue(product).addOnSuccessListener(aVoid -> {
                                Toast.makeText(DetailProductActivity.this, "Đã thêm sản phẩm yêu thích", Toast.LENGTH_SHORT).show();
                            })
                                    .addOnFailureListener(e -> {
                                        Log.e("Firebase", "Lỗi" + e.getMessage());
                                    });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                    });
                }
            }
        });
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    int soCan = Integer.parseInt(soLuong.getText().toString());
                    if (soCan != 0) {
                        int soTien = gia * soCan;
                        Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                        intent.putExtra("IMAGE", img);
                        intent.putExtra("TEN", ten);
                        intent.putExtra("QUYCACH", quycach);
                        intent.putExtra("GIA", gia);
                        intent.putExtra("SOCAN", soCan);
                        intent.putExtra("SOTIEN", soTien);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DetailProductActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    Intent intent = new Intent(DetailProductActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    addComment();
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
    private void addComment(){
        String decription = editTextComment.getText().toString();
        if(decription != null && !decription.isEmpty()){
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String formatDate = dateFormat.format(calendar.getTime());
            Intent intent = getIntent();
            String id = intent.getStringExtra("ID");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null){
                String userId = user.getUid();
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("account");
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.child("fullName").getValue(String.class);
                            String image = snapshot.child("image").getValue(String.class);
                            Comment comment = new Comment(image,name,decription,formatDate);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comment").child(id);
                            reference.push().setValue(comment);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                editTextComment.setText("");
            }
        }else {
            Toast.makeText(DetailProductActivity.this,"Vui lòng nhập bình luận của bạn",Toast.LENGTH_SHORT).show();
        }

    }
    private void getComment(){
        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("comment").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentList.add(comment);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
