package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.OrderAdapter;
import com.example.nhom13_appbanhaisan.Library.ExpandableHeightGridView;
import com.example.nhom13_appbanhaisan.Model.Cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderActivity  extends AppCompatActivity {
    private ImageView back;
    TextView thanhToan;
    TextView soSanPham;
    TextView tongTienSP;
    TextView tongTienTT;
    TextView tongTienVanChuyen;
    private ExpandableHeightGridView listOrder;
    ArrayList<Cart> arrOrder;

    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        back = findViewById(R.id.btnbackOrder);
        thanhToan = findViewById(R.id.tratien);
        soSanPham = findViewById(R.id.demSP);
        tongTienSP = findViewById(R.id.tongtienSPOrder);
        tongTienTT = findViewById(R.id.tientra);
        tongTienVanChuyen = findViewById(R.id.tongvanchuyen);
        listOrder =(ExpandableHeightGridView) findViewById(R.id.listPrOrder);
        listOrder.setExpanded(true);
        arrOrder = new ArrayList<>();
        adapter = new OrderAdapter(OrderActivity.this, R.layout.order_layout, arrOrder);
        listOrder.setAdapter(adapter);


        findViewById(R.id.dathang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CartActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        List<String> id = intent.getStringArrayListExtra("SELECTED_ITEMS_ID");
        List<Cart> selectedItems = intent.getParcelableArrayListExtra("SELECTED_ITEMS");
        if (selectedItems != null && !selectedItems.isEmpty()) {
            arrOrder.addAll(selectedItems);
            adapter.notifyDataSetChanged();
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int quantity = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            Cart order = adapter.getItem(i);
            if (order != null) {
                quantity += order.getSoTien();
            }
        }
        tongTienSP.setText(format.format(quantity));
        soSanPham.setText(": " + String.valueOf(adapter.getCount()) + " sản phẩm");
        tongTienTT.setText(format.format(quantity + 80000));
        tongTienVanChuyen.setText(format.format(80000));
        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent payIntent = new Intent(getApplicationContext(), PayActivity.class);
                payIntent.putStringArrayListExtra("SELECTED_ITEMS_ID_PAY", (ArrayList<String>) id);
                payIntent.putExtra("TONGTIEN",tongTienTT.getText());
                payIntent.putParcelableArrayListExtra("SELECTED_ITEMS_PAY", arrOrder);
                startActivity(payIntent);
            }
        });

    }
}
