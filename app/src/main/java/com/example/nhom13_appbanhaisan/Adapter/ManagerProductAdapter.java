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
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ManagerProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;
    List<Product> objects;

    public ManagerProductAdapter(@NonNull Activity context,int resource, @NonNull List<Product> objects) {
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
        ImageView img = convertView.findViewById(R.id.imageManagerProduct);
        TextView name = convertView.findViewById(R.id.nameManagerProduct);
        TextView price = convertView.findViewById(R.id.priceManagerProduct);
        TextView quycach = convertView.findViewById(R.id.quyCachManagerProduct);
        TextView soluong = convertView.findViewById(R.id.soLuongManagerProduct);
        Product product = this.objects.get(position);
        name.setText(Html.fromHtml(product.getTen_san_pham(), Html.FROM_HTML_MODE_LEGACY));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        price.setText("Giá: "+format.format(product.getGia()));
        quycach.setText("Quy cách: " + product.getQuy_cach());
        soluong.setText("Số lượng: "+product.getSo_luong_ton_kho());
        Glide.with(context)
                .load(product.getAnh())
                .apply(new RequestOptions()
                        .override(800, 800)
                        .fitCenter())
                .into(img);
        return convertView;
    }
}
