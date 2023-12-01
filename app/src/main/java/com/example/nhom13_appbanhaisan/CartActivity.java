package com.example.nhom13_appbanhaisan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.nhom13_appbanhaisan.Model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private ImageView btnback;
    private ListView listView;
    private TextView tongTien,gioHangTrong,insert,txtTotal;
    private Button muaHang;
    CheckBox chonTatCa;
    CartAdapter adapter;
    List<Cart> list ;
    int currentTotal = 0;
    List<Integer> selectedPositions = new ArrayList<>() ;
    private SharedPreferenceManager sharedPreferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        txtTotal = findViewById(R.id.totalCart);
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
                    Iterator<Integer> iterator = selectedPositions.iterator();

                    while (iterator.hasNext()) {
                        Integer position = iterator.next();
                        if (position != -1 && position < list.size()) {
                            String id = list.get(position).getId();
                            deleteItemFirebase(id);
                        }

                        // Loại bỏ phần tử khỏi selectedPositions sử dụng iterator.remove()
                        iterator.remove();
                    }
                    if(chonTatCa.isChecked()==true){
                        for(int i = listView.getCount() - 1 ; i >=0 ; i--){
                            String id = list.get(i).getId();
                            deleteItemFirebase(id);
                            list.remove(i);
                        }
                    }

                    // Cập nhật tổng tiền và thông báo thay đổi
                    tongTien.setText(format.format(0));
                    adapter.notifyDataSetChanged();
                }
            }
        });

        muaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!list.isEmpty()) {
                    if(chonTatCa.isChecked()==true) {
                        List<String> idCart = new ArrayList<>();
                        List<Cart> selectedItems = new ArrayList<>();
                        for (int i = listView.getCount() - 1; i >= 0; i--) {
                            String id = list.get(i).getId();
                            String ten = list.get(i).getTen();
                            String quycach = list.get(i).getQuyCach();
                            int gia = list.get(i).getGia();
                            int can = list.get(i).getSoCan();
                            int tong = list.get(i).getSoTien();
                            String anh = list.get(i).getAnh();
                            Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                            selectedItems.add(new Cart(anh, ten, quycach, gia, can, tong));
                            idCart.add(id);
                            intent.putStringArrayListExtra("SELECTED_ITEMS_ID", (ArrayList<String>) idCart);
                            intent.putParcelableArrayListExtra("SELECTED_ITEMS", (ArrayList<? extends Parcelable>) selectedItems);
                            startActivity(intent);
                        }
                    }
                    else {
                        List<Cart> selectedItems = new ArrayList<>();
                        List<String> idCart = new ArrayList<>();
                        List<Integer> selectedPositions = adapter.getSelectedPosition();
                        Iterator<Integer> iterator = selectedPositions.iterator();
                        while (iterator.hasNext()) {
                            Integer position = iterator.next();
                            if (position != -1 && position < list.size()) {
                                String id = list.get(position).getId();
                                String ten = list.get(position).getTen();
                                String quycach = list.get(position).getQuyCach();
                                int gia = list.get(position).getGia();
                                int can = list.get(position).getSoCan();
                                int tong = list.get(position).getSoTien();
                                String anh = list.get(position).getAnh();
                                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                                selectedItems.add(new Cart(anh, ten, quycach, gia, can, tong));
                                idCart.add(id);
                                intent.putStringArrayListExtra("SELECTED_ITEMS_ID", (ArrayList<String>) idCart);
                                intent.putParcelableArrayListExtra("SELECTED_ITEMS", (ArrayList<? extends Parcelable>) selectedItems);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    selectedPositions.clear();
                }
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
    private void deleteItemFirebase(String id) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
            userCartRef.child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(@NonNull Task<Void> task) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (task.isSuccessful()) {
                                try {
                                    for (Integer position : selectedPositions) {
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

    }
    public void getValueCartFromFirebase(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("cart");
            userCartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Cart cart = dataSnapshot.getValue(Cart.class);
                        cart.setId(dataSnapshot.getKey());
                        list.add(cart);
                    }
                    if(list.size() == 0){
                        gioHangTrong.setVisibility(View.VISIBLE);
                    } else {
                        gioHangTrong.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                    calculateTotalQuantity();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CartActivity.this,"error",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

    }
    public void calculateTotalQuantity() {
        int total = adapter.getCount();
        txtTotal.setText("("+total+")");
        sharedPreferenceManager = SharedPreferenceManager.getInstance(this);
        sharedPreferenceManager.setSharedValue(String.valueOf(total));
    }
}
