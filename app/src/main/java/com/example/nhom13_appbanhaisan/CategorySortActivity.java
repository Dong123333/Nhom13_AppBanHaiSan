package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CategorySortActivity extends AppCompatActivity {
    private ImageView back;
    private TextView title;
    ExpandableHeightGridView list;
    List<Product> productList;
    ProductAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        title = findViewById(R.id.titleCategory);
        back = findViewById(R.id.backCategory);
        list = (ExpandableHeightGridView) findViewById(R.id.listSortCategory);
        list.setExpanded(true);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(CategorySortActivity.this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(CategorySortActivity.this, DetailProductActivity.class);
                intent.putExtra("ID",product.getId());
                intent.putExtra("IMAGE_URL", product.getAnh());
                intent.putExtra("TEN", product.getTen_san_pham());
                intent.putExtra("GIA", product.getGia());
                intent.putExtra("QUY_CACH", product.getQuy_cach());
                intent.putExtra("TINH_TRANG", product.getTinh_trang());
                intent.putExtra("XUAT_XU", product.getXuat_xu());
                intent.putExtra("MON_NGON", product.getMon_ngon());
                intent.putExtra("SO_LUONG_CON", product.getSo_luong_ton_kho());
                intent.putExtra("SO_LUONG_DA_BAN", product.getSo_luong_da_ban());
                startActivity(intent);
            }
        });
        list.setAdapter(adapter);
        Intent intent = getIntent();
        String loai = intent.getStringExtra("LOAI");
        title.setText(loai);

        if (!loai.equals("Tất cả")){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("products");
            reference.orderByChild("loai").equalTo(loai).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Product product = dataSnapshot.getValue(Product.class);
                        product.setId(dataSnapshot.getKey());
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CategorySortActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("products");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Product product = dataSnapshot.getValue(Product.class);
                        product.setId(dataSnapshot.getKey());
                        productList.add(product);
                    }
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CategorySortActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            });
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}