package com.example.nhom13_appbanhaisan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nhom13_appbanhaisan.Fragment.WaitForConfirmationFragment;
import com.google.android.material.tabs.TabLayout;
public class PurchaseOrderActivity extends AppCompatActivity {
    private ImageView back;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchaseorder);
        back=findViewById(R.id.btn_back);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        Intent intent = getIntent();
        String name = intent.getStringExtra("TEN");
        String anh = intent.getStringExtra("ANH");
        String quycach = intent.getStringExtra("QUYCACH");
        int gia = intent.getIntExtra("GIA", 0);
        int can = intent.getIntExtra("CAN", 0);
        int tientt = intent.getIntExtra("TONG",0);

        Bundle bundle = new Bundle();
        bundle.putString("TEN", name);
        bundle.putString("ANH", anh);
        bundle.putString("QUYCACH", quycach);
        bundle.putInt("TONGTIEN", tientt);
        bundle.putInt("GIA", gia);
        bundle.putInt("CAN", can);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,bundle);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PersonalInfoManagementActivity.class);
                startActivity(intent);
            }
        });


    }

}
