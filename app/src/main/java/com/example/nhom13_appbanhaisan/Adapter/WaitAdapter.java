package com.example.nhom13_appbanhaisan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nhom13_appbanhaisan.Event.UpdateTotalEvent;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WaitAdapter extends ArrayAdapter<Cart> {
    Context context;
    int resource;
    List<Cart> objects;

    public WaitAdapter(@NonNull Context context, int resource, @NonNull List<Cart> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(this.resource, null);
        Cart cart = this.objects.get(position);
        ImageView image = convertView.findViewById(R.id.imgItemOrder);
        TextView ten = convertView.findViewById(R.id.nameOrder);
        TextView quyCach = convertView.findViewById(R.id.quyCach);
        TextView gia = convertView.findViewById(R.id.giaOrder);
        TextView soCan = convertView.findViewById(R.id.soCanOrder);
        TextView soTien = convertView.findViewById(R.id.soTienOrder);
        Glide.with(context)
                .load(cart.getAnh())
                .apply(new RequestOptions()
                        .override(800, 800)
                        .fitCenter())
                .into(image);
        ten.setText(cart.getTen());
        quyCach.setText("Quy cách: "+cart.getQuyCach());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        gia.setText("Giá: "+format.format(cart.getGia()));
        soCan.setText(cart.getSoCan() + "kg");
        soTien.setText(format.format(cart.getSoTien()));
        return convertView;
    }
    public int getCount() {
        return super.getCount();
    }
}
