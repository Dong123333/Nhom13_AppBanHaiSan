package com.example.nhom13_appbanhaisan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    LinearLayout trong;
    ExpandableHeightGridView list;
    List<Product> productList;
    ProductAdapter adapter;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        back = findViewById(R.id.backFavourite);
        trong = findViewById(R.id.favTrong);
        list = (ExpandableHeightGridView) findViewById(R.id.listFavourite);
        list.setExpanded(true);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(FavouriteActivity.this, productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(getApplicationContext(), DetailProductActivity.class);
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getValueFavouriteFromFirebase();
    }
    public void getValueFavouriteFromFirebase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("favourite");
            userCartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    productList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Product product = dataSnapshot.getValue(Product.class);
                        product.setId(dataSnapshot.getKey());
                        productList.add(product);
                    }
                    if(productList.size() == 0){
                        trong.setVisibility(View.VISIBLE);
                    } else {
                        trong.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(FavouriteActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

    }
}
