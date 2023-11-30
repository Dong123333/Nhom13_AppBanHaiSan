package com.example.nhom13_appbanhaisan.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Adapter.ProductAdapter.OnItemClickListener;
import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductForYouAdapter extends RecyclerView.Adapter<ProductForYouAdapter.ViewHolder> {
    Context context;
    ArrayList<Product> list;
    private OnItemClickListener listener;

    public ProductForYouAdapter(Context context, ArrayList<Product> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductForYouAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productforyou_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductForYouAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product product = list.get(position);
        holder.name.setText(Html.fromHtml(product.getTen_san_pham(), Html.FROM_HTML_MODE_LEGACY));
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.price.setText(format.format(product.getGia()));
        holder.sold.setText("Đã bán "+product.getSo_luong_da_ban());
        Picasso.get().load(product.getAnh()).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name,price,sold;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageProduct);
            name = itemView.findViewById(R.id.nameProduct);
            price = itemView.findViewById(R.id.price);
            sold = itemView.findViewById(R.id.sold);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(list.get(position));
                    }
                }
            });
        }

    }
    public interface OnItemClickListener {
        void onItemClick(Product product);
    }
}
