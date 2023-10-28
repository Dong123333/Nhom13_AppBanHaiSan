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

import com.example.nhom13_appbanhaisan.Model.Category;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;
    List<Category> objects;
    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects) {
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
        ImageView img = convertView.findViewById(R.id.imageCategory);
        TextView name = convertView.findViewById(R.id.nameCategory);
        Category category = this.objects.get(position);
        name.setText(Html.fromHtml(category.getTen_san_pham(), Html.FROM_HTML_MODE_LEGACY));
        Picasso.get().load(category.getAnh()).into(img);
        return convertView;
    }
}
