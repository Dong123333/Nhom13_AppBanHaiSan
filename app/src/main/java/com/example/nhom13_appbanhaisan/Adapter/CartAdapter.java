package com.example.nhom13_appbanhaisan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<Cart> list;

    public CartAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView ten,quyCach,gia,soCan,soTien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageCart);
            ten = itemView.findViewById(R.id.nameCart);
            quyCach = itemView.findViewById(R.id.quyCach);
            gia = itemView.findViewById(R.id.gia);
            soCan = itemView.findViewById(R.id.soCan);
            soTien = itemView.findViewById(R.id.soTien);
        }
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        Cart cart = list.get(position);
        holder.ten.setText(cart.getTen());
        holder.quyCach.setText(cart.getQuyCach());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.gia.setText(cart.getGia());
        holder.soCan.setText(decimalFormat.format(cart.getSoCan())+"kg");
        long tongTien = cart.getSoCan()*Long.parseLong(cart.getGia());
        holder.soTien.setText(decimalFormat.format(tongTien));
        Picasso.get().load(cart.getAnh()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
