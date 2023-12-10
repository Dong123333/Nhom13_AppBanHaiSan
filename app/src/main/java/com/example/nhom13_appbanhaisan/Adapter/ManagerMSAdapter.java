package com.example.nhom13_appbanhaisan.Adapter;

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
import com.example.nhom13_appbanhaisan.Model.Message;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.Model.Users;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManagerMSAdapter extends ArrayAdapter<Users> {
    Activity context;
    int resource;
    List<Users> objects;

    public ManagerMSAdapter(@NonNull Activity context,int resource, @NonNull List<Users> objects) {
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
        ImageView img = convertView.findViewById(R.id.imageMS);
        TextView name = convertView.findViewById(R.id.nameMS);
        Users users = this.objects.get(position);
        name.setText(users.getFullName());
        if(users.getImage() != null && !users.getImage().isEmpty()){
            Glide.with(context)
                    .load(users.getImage())
                    .apply(new RequestOptions()
                            .override(800, 800)
                            .fitCenter())
                    .into(img);
        }else {
            Glide.with(context)
                    .load(R.drawable.user)
                    .apply(new RequestOptions()
                            .override(800, 800)
                            .fitCenter())
                    .into(img);
        }

        return convertView;
    }
}
