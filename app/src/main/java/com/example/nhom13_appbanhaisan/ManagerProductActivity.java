package com.example.nhom13_appbanhaisan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.ManagerProductAdapter;
import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManagerProductActivity extends AppCompatActivity {
    TextView them;
    ImageView back;
    ListView listView;
    ArrayList<Product> list;
    ManagerProductAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_product);
        them = findViewById(R.id.themSanPham);
        listView = findViewById(R.id.listManagerProduct);
        back = findViewById(R.id.back);

        list = new ArrayList<>();
        adapter = new ManagerProductAdapter(ManagerProductActivity.this,R.layout.managerproduct_layout,list);
        listView.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UpdateProductActivity.class);
                startActivity(intent);
            }
        });
        getValueProductFromFirebase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin của sản phẩm được chọn
                Product selectedProduct = list.get(position);
                ImageView sua = view.findViewById(R.id.sua);
                ImageView xoa = view.findViewById(R.id.xoa);
                sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ManagerProductActivity.this, InsertProductActivity.class);
                        intent.putExtra("productId", selectedProduct.getId());
                        startActivity(intent);
                    }
                });
                xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteConfirmationDialog(selectedProduct.getId());
                    }
                });

            }
        });
    }
    public void getValueProductFromFirebase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("products");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);
                    list.add(product);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManagerProductActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDeleteConfirmationDialog(final int productId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xóa sản phẩm từ Firebase
                        deleteProductFromFirebase(productId);
                        list.remove(getProductIndexById(productId));
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng AlertDialog nếu người dùng chọn "Không"
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    private void deleteProductFromFirebase(int productId) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products");
        reference.child(String.valueOf(productId)).removeValue();
    }
    private int getProductIndexById(int productId) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId()==productId) {
                return i;
            }
        }
        return -1;
    }

}
