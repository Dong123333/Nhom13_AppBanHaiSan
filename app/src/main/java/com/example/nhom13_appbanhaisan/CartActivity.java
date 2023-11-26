package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.CartAdapter;
import com.example.nhom13_appbanhaisan.Event.UpdateTotalEvent;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private ImageView btnback;
    private ListView listView;
    private TextView tongTien,gioHangTrong,insert;
    private Button muaHang;
    CheckBox chonTatCa;
    CartAdapter adapter;
    List<Cart> list ;
    int currentTotal = 0;
    List<Integer> selectedPositions = new ArrayList<>() ;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        btnback = findViewById(R.id.btn_back);
        listView = findViewById(R.id.listCart);
        tongTien = findViewById(R.id.tongTien);
        muaHang = findViewById(R.id.muaHang);
        gioHangTrong = findViewById(R.id.gioHangTrong);
        insert = findViewById(R.id.btn_insert);
        chonTatCa = findViewById(R.id.chonTatCa);
        list = new ArrayList<>();
        adapter = new CartAdapter(CartActivity.this, R.layout.cart_layout, list);
        listView.setAdapter(adapter);
        EventBus.getDefault().register(this);
        getValueCartFromFirebase();
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        chonTatCa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked==true){
                    int tongtien = 0;
                    for (int i = 0; i< list.size();i++){
                        tongtien+= list.get(i).getSoTien();
                    }

                    tongTien.setText(format.format(tongtien));
                }
                else {
                    tongTien.setText(format.format(0));
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectedPosition(selectedPositions);
            }
        });
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.isEmpty()) {
                    List<Integer> selectedPositions = adapter.getSelectedPosition();
                    for(Integer position : selectedPositions){
                        if (position != -1 && position < list.size()) {
                            int id = list.get(position).getId();
                            deleteItemFirebase(id);
                        } else {

                        }
                    }
                } else {
                }
                tongTien.setText(format.format(0));
                selectedPositions.clear();
            }
        });
    }
    protected void onDestroy() {
        // Hủy đăng ký EventBus trong onDestroy hoặc onStop
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(UpdateTotalEvent event) {
        // Xử lý sự kiện nhận được từ EventBus
        int itemPrice = event.getItemPrice();
        currentTotal += itemPrice;
        updateTotalTextView(currentTotal);

    }
    private void updateTotalTextView(int newTotal) {
        // Cập nhật TextView hiển thị tổng tiền mới
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        tongTien.setText("" + format.format(newTotal));
    }
    private void deleteItemFirebase(int id) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("cart");
        reference.child(String.valueOf(id)).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (task.isSuccessful()) {
                            try {
                                for(Integer position : selectedPositions){
                                    if (!list.isEmpty() && position >= 0 && position < list.size()) {
                                        adapter.removeItem(position);
                                        list.remove(position);
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            } catch (Exception x) {

                            }
                        }
                    }
                });
            }
        });

    }
    public void getValueCartFromFirebase(){
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("cart");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    list.add(cart);
                }
                if(list.size() == 0){
                    gioHangTrong.setVisibility(View.VISIBLE);
                } else {
                    gioHangTrong.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CartActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
