package com.example.nhom13_appbanhaisan.Adapter;

import android.app.Activity;
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

public class CartAdapter extends ArrayAdapter<Cart> {
    Context context;
    int resource;
    List<Cart> objects;
    private List<Integer> selectedPositions = new ArrayList<>();
    private boolean selectAll = false;

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
        notifyDataSetChanged();
    }

    public void setSelectedPosition(List<Integer> position) {
        selectedPositions = position;
    }

    public List<Integer> getSelectedPosition() {
        return selectedPositions;
    }

    public CartAdapter(@NonNull Context context, int resource, @NonNull List<Cart> objects) {
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
        CheckBox checkBox = convertView.findViewById(R.id.checkboxCart);
        checkBox.setChecked(selectAll || selectedPositions.contains(position));
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int itemPrice = getItem(position).getSoTien();
                EventBus.getDefault().post(new UpdateTotalEvent(isChecked ? itemPrice : -itemPrice));
                if (position == -1) {
                    setSelectAll(isChecked);
                } else {
                    if (isChecked) {
                        selectedPositions.add(position);
                    } else {
                        selectedPositions.remove(Integer.valueOf(position));
                    }
                }

            }
        });
        return convertView;
    }

    public void removeItem(int position) {
        if (objects != null && position >= 0 && position < objects.size()) {
            objects.remove(position);
            notifyDataSetChanged();
        }
    }
    public int getCount() {
        return super.getCount();
    }
}
