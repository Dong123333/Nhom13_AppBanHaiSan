package com.example.nhom13_appbanhaisan.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nhom13_appbanhaisan.Adapter.CartAdapter;
import com.example.nhom13_appbanhaisan.Adapter.WaitAdapter;
import com.example.nhom13_appbanhaisan.CartActivity;
import com.example.nhom13_appbanhaisan.DetailProductActivity;
import com.example.nhom13_appbanhaisan.LoginActivity;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class WaitForConfirmationFragment extends Fragment {
    List<Cart> list;
    WaitAdapter adapter;
    ListView listView;
    LinearLayout trong;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_waitforconfirmation, container, false);
        listView = view.findViewById(R.id.list);
        trong = view.findViewById(R.id.waitTrong);
        list = new ArrayList<>();
        adapter = new WaitAdapter(getContext(), R.layout.wait_layout, list);
        listView.setAdapter(adapter);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Bundle bundle = getArguments();
        if(bundle!=null){
            String anh = bundle.getString("ANH");
            String name = bundle.getString("TEN");
            String quycach = bundle.getString("QUYCACH");
            int tongtt = bundle.getInt("TONGTIEN");
            int gia = bundle.getInt("GIA");
            int can = bundle.getInt("CAN");
            if(anh != null && name != null && quycach != null){
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("wait");
                    Cart cart = new Cart(anh, name, quycach, gia, can, tongtt);
                    userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            long count = snapshot.getChildrenCount() + 1;
                            cart.setId((int) count);
                            userCartRef.child(String.valueOf(count)).setValue(cart);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

        }
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("wait");
            userCartRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Cart cart = dataSnapshot.getValue(Cart.class);
                        list.add(cart);
                    }
                    if (list.size() == 0) {
                        trong.setVisibility(View.VISIBLE);
                    } else {
                        trong.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        return view;
    }
}