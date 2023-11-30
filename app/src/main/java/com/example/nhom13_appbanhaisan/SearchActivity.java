package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Adapter.CategoryAdapter;
import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Library.GridViewScrollable;
import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private ImageView back;
    private EditText inputSearch;
    private ExpandableHeightGridView listsearch;

    ArrayList<Product> arrayListProduct;

    ProductAdapter searchAdapter;
    ArrayList<Product> arrayListSearch;
    private Collator collator = Collator.getInstance(new Locale("vi", "VN"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        back=findViewById(R.id.backspace);

        inputSearch=findViewById(R.id.inputsearch);
        listsearch = (ExpandableHeightGridView) findViewById(R.id.listSearch);
        listsearch.setExpanded(true);


        arrayListProduct = new ArrayList<>();

        //set sự kiện click vào sản phẩm để truyền sang trang chi tiêết
        arrayListSearch = new ArrayList<>();
        searchAdapter = new ProductAdapter(SearchActivity.this, arrayListSearch, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(SearchActivity.this, DetailProductActivity.class);
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
        listsearch.setAdapter(searchAdapter);
        getValueProductFromFirebase();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SearchActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterProducts(CharSequence charSequence) {
        arrayListSearch.clear();
        String query = charSequence.toString().toLowerCase().trim();
        // Add products that match the search text
        if (query.isEmpty()) {
            arrayListSearch.addAll(arrayListProduct);
        } else {
            for (Product product : arrayListProduct) {
                boolean isMatch = true;
                String[] keywords = query.split("\\s+");
                for (String keyword : keywords) {
                    if (!product.getTen_san_pham().toLowerCase().contains(keyword)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    arrayListSearch.add(product);
                }
            }
        }

        // Notify the adapter that the data has changed
        searchAdapter.notifyDataSetChanged();
    }


}