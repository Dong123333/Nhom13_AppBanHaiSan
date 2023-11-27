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

import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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


        Picasso.get().load(cartOrder.getAnh()).into(img);
        name.setText(cartOrder.getTen());
        gia.setText("Giá: "+cartOrder.getGia()+"đ");
        can.setText("Số kg: "+cartOrder.getSoCan()+"kg");
        tien.setText(cartOrder.getSoTien()+"đ");


        return convertView;
    }
}
