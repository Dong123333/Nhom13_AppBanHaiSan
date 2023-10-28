package com.example.nhom13_appbanhaisan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.CategoryAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private ImageView btncart,btnNoti,btnMenu,btnChat;
    private EditText inputSearch;
    private TwoWayView listCategory,listProduct1,listProduct2;
    ArrayList<Category> arrayListCategory;
    ArrayList<Product> arrayListProduct;
    CategoryAdapter categoryAdapter;
    ProductAdapter productAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btncart = findViewById(R.id.btn_cart);
        inputSearch = findViewById(R.id.input_search);
        btnNoti = findViewById(R.id.btn_noti);
        btnChat = findViewById(R.id.btn_chat);
        btnMenu = findViewById(R.id.btn_menu);
        listCategory = findViewById(R.id.listCategory);
        listProduct1 = findViewById(R.id.listProduct1);
        listProduct2= findViewById(R.id.listProduct2);
        arrayListCategory = new ArrayList<>();
        arrayListProduct = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(HomeActivity.this,R.layout.category_layout,arrayListCategory);
        productAdapter = new ProductAdapter(HomeActivity.this,R.layout.product_layout,arrayListProduct);
        listProduct1.setAdapter(productAdapter);
        listProduct2.setAdapter(productAdapter);
        listCategory.setAdapter(categoryAdapter);
        btncart.setOnClickListener(new View.OnClickListener() {
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
       getValueCategoryFromFirebase();
       getValueProductFromFirebase();

    }
    public void getValueCategoryFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("categories");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Category category = dataSnapshot.getValue(Category.class);
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
                    arrayListProduct.add(product);
                    setupOnClick(product);
                }
                productAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupOnClick(Product product) {
        listProduct1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailProductActivity.class);
                startActivity(intent);
            }
        });
        listProduct2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DetailProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
