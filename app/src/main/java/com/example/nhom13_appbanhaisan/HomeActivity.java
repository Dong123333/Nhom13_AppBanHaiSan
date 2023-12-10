package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Adapter.CategoryAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ProductForYouAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.RecyclerViewItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements RecyclerViewItemClickListener {
    private ImageView btncart,btnNoti,btnMenu,btnChat,btnCart,btnHome,btnFavourite;
    private TextView txtTotal,inputSearch;
    private RecyclerView listCategory, listProductForYou;
    private ExpandableHeightGridView listProduct;
    ArrayList<Category> arrayListCategory;
    ArrayList<Product> arrayListProduct;
    ArrayList<Product> arrayListProductForYou;
    List<String> loai;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;
    ProductForYouAdapter productAdapterForYou;
    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        txtTotal = findViewById(R.id.total);
        btncart = findViewById(R.id.btn_cart1);
        btnCart = findViewById(R.id.cart);
        inputSearch = findViewById(R.id.input_search);
        btnNoti = findViewById(R.id.btn_noti);
        btnChat = findViewById(R.id.btn_chat);
        btnMenu = findViewById(R.id.btn_menu);
        btnHome = findViewById(R.id.btn_home);
        btnFavourite = findViewById(R.id.btn_favourite);
        listCategory = findViewById(R.id.listCategory);
        listProduct = (ExpandableHeightGridView) findViewById(R.id.listProduct);
        listProduct.setExpanded(true);
        listProductForYou= findViewById(R.id.listProductForYou);
        arrayListCategory = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        loai = new ArrayList<>();
        arrayListProductForYou = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(HomeActivity.this,arrayListCategory,this);
        productAdapter = new ProductAdapter(HomeActivity.this,arrayListProduct, new ProductAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Product product) {
                // Chuyển đến trang chi tiết và truyền dữ liệu
                Intent intent = new Intent(HomeActivity.this, DetailProductActivity.class);
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
        productAdapterForYou = new ProductForYouAdapter(HomeActivity.this, arrayListProductForYou, new ProductForYouAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(HomeActivity.this, DetailProductActivity.class);
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
        listProduct.setAdapter(productAdapter);
        listProductForYou.setAdapter(productAdapterForYou);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        listProductForYou.setLayoutManager(linearLayoutManager1);
        listCategory.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        listCategory.setLayoutManager(linearLayoutManager2);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });
        inputSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonalInfoManagementActivity.class);
                startActivity(intent);
            }
        });
        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                startActivity(intent);
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FavouriteActivity.class);
                startActivity(intent);
            }
        });

        getValueCategoryFromFirebase();
        getValueProductFromFirebase();
        getValueProductForYouFromFirebase();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            txtTotal.setVisibility(View.GONE);
        }
        else {
            txtTotal.setVisibility(View.VISIBLE);
            sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
            txtTotal.setText(sharedPreferenceManager.getSharedValue());
            sharedPreferenceManager.setOnSharedValueChangeListener(new SharedPreferenceManager.OnSharedValueChangeListener() {
            @Override
            public void onSharedValueChange(String newValue) {
                txtTotal.setText(newValue);
            }
        });
        }
    }
    public void getValueCategoryFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("categories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
                    String name = dataSnapshot.child("ten_san_pham").getValue(String.class);
                    loai.add(name);
                    arrayListCategory.add(category);
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getValueProductFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    product.setId(dataSnapshot.getKey());
                    arrayListProduct.add(product);
                }
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getValueProductForYouFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("products");
        reference.orderByChild("danh_cho_ban").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    product.setId(dataSnapshot.getKey());
                    arrayListProductForYou.add(product);
                }
                productAdapterForYou.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(),CategorySortActivity.class);
        String category = loai.get(position);
        intent.putExtra("LOAI",category);
        startActivity(intent);
    }
}
