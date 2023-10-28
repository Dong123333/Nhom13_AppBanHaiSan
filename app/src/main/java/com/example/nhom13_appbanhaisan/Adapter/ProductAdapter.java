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

import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;
    List<Product> objects;
    public ProductAdapter(@NonNull Activity context, int resource, @NonNull List<Product> objects) {
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
        ImageView img = convertView.findViewById(R.id.imageProduct);
        TextView name = convertView.findViewById(R.id.nameProduct);
        TextView price = convertView.findViewById(R.id.price);
        TextView sold = convertView.findViewById(R.id.sold);
        Product product = this.objects.get(position);
        name.setText(Html.fromHtml(product.getTen_san_pham(), Html.FROM_HTML_MODE_LEGACY));
        price.setText(product.getGia());
        sold.setText("Đã bán "+product.getSo_luong_da_ban());
        Picasso.get().load(product.getAnh()).into(img);
        return convertView;
    }
}
