package com.example.nhom13_appbanhaisan.Adapter;

import android.accounts.Account;
import android.app.Activity;
import android.text.Html;
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
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManagerAccountAdapter extends ArrayAdapter<Users> {
    Activity context;
    int resource;
    List<Users> objects;

    public ManagerAccountAdapter(@NonNull Activity context,int resource, @NonNull List<Users> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        convertView = inflater.inflate(this.resource, null);
        TextView name = convertView.findViewById(R.id.nameAccount);
        TextView gender = convertView.findViewById(R.id.genderAccount);
        TextView date = convertView.findViewById(R.id.dateAccount);
        TextView phone = convertView.findViewById(R.id.phoneAccount);
        Users users = this.objects.get(position);
        name.setText("Họ và tên: "+users.getFullName());
        gender.setText("Giới tính: "+users.getGender());
        date.setText("Ngày sinh: "+users.getDate());
        phone.setText("Số điện thoại: "+users.getPhone());
        return convertView;
    }
}
