package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nhom13_appbanhaisan.Adapter.OrderAdapter;
import com.example.nhom13_appbanhaisan.Model.Cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class OrderActivity  extends AppCompatActivity {
    private ImageView back;


    private ListView listOrder;



    ArrayList<Cart> arrOrder;

    OrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        back=findViewById(R.id.back);
        listOrder = findViewById(R.id.listPrOrder);
        arrOrder = new ArrayList<>();
        TextView thanhToan = findViewById(R.id.tratien) ;
        TextView soSanPham = findViewById(R.id.demSP);
        TextView tongTienSP = findViewById(R.id.tongtienSPOrder);
        TextView tongTienTT = findViewById(R.id.tientra);
        findViewById(R.id.dathang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),PayActivity.class);
                    startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("TEN");
        String anh = intent.getStringExtra("ANH");
        String quycach = intent.getStringExtra("QUYCACH");
        int gia = intent.getIntExtra("GIA", 0);
        int can = intent.getIntExtra("CAN", 0);
        int tong = intent.getIntExtra("TONG", 0);

        arrOrder.add(new Cart(anh, name, quycach, gia, can, tong));
        adapter = new OrderAdapter(OrderActivity.this, R.layout.order_layout, arrOrder);

        listOrder.setAdapter(adapter);
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        int quantity = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            Cart order = adapter.getItem(i);
            quantity += order.getSoTien();

        }

        tongTienSP.setText(Integer.toString(quantity));
        soSanPham.setText(": "+format.format(adapter.getCount())+" sản phẩm");
        tongTienTT.setText(format.format(quantity+80000));
        Toast.makeText(OrderActivity.this,name, Toast.LENGTH_SHORT).show();
        thanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PayActivity.class);
                intent.putExtra("tongtien", tongTienTT.getText());
                startActivity(intent);
            }
        });

    }
}
