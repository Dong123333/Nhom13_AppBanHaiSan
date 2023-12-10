package com.example.nhom13_appbanhaisan.Adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends ArrayAdapter<Cart> {
    Activity context;
    int resource;
    List<Cart> objects;
    public OrderAdapter(@NonNull Activity context, int resource, @NonNull List<Cart> objects) {
        super(context,resource,objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource,null);
        ImageView img = convertView.findViewById(R.id.imgItemOrder) ;
        TextView name = convertView.findViewById(R.id.nameOrder);
        TextView gia = convertView.findViewById(R.id.giaOrder);
        TextView can = convertView.findViewById(R.id.soCanOrder);
        TextView tien = convertView.findViewById(R.id.soTienOrder);
        Cart cartOrder = this.objects.get(position);
        Glide.with(context)
                .load(cartOrder.getAnh())
                .apply(new RequestOptions()
                        .override(800, 800)
                        .fitCenter())
                .into(img);
        name.setText(cartOrder.getTen());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        gia.setText("Giá: "+format.format(cartOrder.getGia()));
        can.setText("Số kg: "+cartOrder.getSoCan()+"kg");
        tien.setText(format.format(cartOrder.getSoTien()));
        return convertView;
    }
}