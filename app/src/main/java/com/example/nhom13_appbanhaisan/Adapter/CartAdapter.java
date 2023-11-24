package com.example.nhom13_appbanhaisan.Adapter;

import android.app.Activity;
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

import com.example.nhom13_appbanhaisan.Event.DeleteItemEvent;
import com.example.nhom13_appbanhaisan.Event.UpdateTotalEvent;
import com.example.nhom13_appbanhaisan.Model.Cart;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends ArrayAdapter<Cart> {
    Activity context;
    int resource;
    List<Cart> objects;
    private int selectedPosition = -1;

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public CartAdapter(@NonNull Activity context, int resource, @NonNull List<Cart> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);
        Cart cart = this.objects.get(position);
        ImageView image = convertView.findViewById(R.id.imageCart);
        TextView ten = convertView.findViewById(R.id.nameCart);
        TextView quyCach = convertView.findViewById(R.id.quyCach);
        TextView gia = convertView.findViewById(R.id.gia);
        TextView soCan = convertView.findViewById(R.id.soCan);
        TextView soTien = convertView.findViewById(R.id.soTien);
        Picasso.get().load(cart.getAnh()).into(image);
        ten.setText(cart.getTen());
        quyCach.setText("Quy cách: "+cart.getQuyCach());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        gia.setText("Giá: "+format.format(cart.getGia()));
        soCan.setText(cart.getSoCan() + "kg");
        soTien.setText(format.format(cart.getSoTien()));
        CheckBox checkBox = convertView.findViewById(R.id.checkboxCart);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int itemPrice = getItem(position).getSoTien();
                EventBus.getDefault().post(new UpdateTotalEvent(isChecked ? itemPrice : -itemPrice));
                handleCheckboxClick(position);
            }
        });
        return convertView;
    }
    private void handleCheckboxClick(int position) {
        selectedPosition = position;
    }

    public void clearSelection() {
        selectedPosition = -1;
    }
    public void removeItem(int position) {
        if (objects != null && position >= 0 && position < objects.size()) {
            objects.remove(position);
            notifyDataSetChanged();
        }
    }
}