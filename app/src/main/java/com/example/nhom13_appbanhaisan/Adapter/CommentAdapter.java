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
import com.example.nhom13_appbanhaisan.Model.Comment;
import com.example.nhom13_appbanhaisan.Model.Product;
import com.example.nhom13_appbanhaisan.R;
import com.squareup.picasso.Cache;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;

public class CommentAdapter extends ArrayAdapter<Comment> {
    Activity context;
    int resource;
    List<Comment> objects;
    public CommentAdapter(@NonNull Activity context,int resource,  @NonNull List<Comment> objects) {
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
        CircleImageView img = convertView.findViewById(R.id.imageUserComment);
        TextView name = convertView.findViewById(R.id.usernameComment);
        TextView description = convertView.findViewById(R.id.description);
        TextView date = convertView.findViewById(R.id.date);
        Comment comment = this.objects.get(position);
        name.setText(comment.getName());
        description.setText(comment.getDescription());
        date.setText(comment.getDate());
        Glide.with(context)
                .load(comment.getImage())
                .apply(new RequestOptions()
                        .override(800, 800)
                        .fitCenter())
                .into(img);
        return convertView;
    }
}
