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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;
    List<Product> objects;

    private OnItemClickListener listener;
    public ProductAdapter(@NonNull Activity context,  @NonNull List<Product> objects, OnItemClickListener listener) {
        super(context,R.layout.product_layout,objects);
        this.context=context;
        this.resource=R.layout.product_layout;
        this.objects=objects;
        this.listener = listener;
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
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        price.setText(format.format(product.getGia()));
        sold.setText("Đã bán "+product.getSo_luong_da_ban());
        Picasso.get().load(product.getAnh()).into(img);

        // Gọi sự kiện click khi item được nhấn
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(getItem(position));
                }
            }
        });
        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
}
