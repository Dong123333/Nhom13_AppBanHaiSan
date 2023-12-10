package com.example.nhom13_appbanhaisan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom13_appbanhaisan.Model.Notification;
import com.example.nhom13_appbanhaisan.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    Context context;
    ArrayList<Notification> list;

    public NotificationAdapter(Context context, ArrayList<Notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Notification notification = list.get(position);
        holder.trangthai.setText(notification.getTrangthaidonhang());
        holder.nodung.setText("Đơn hàng "+ notification.getMadh()+" đang được xác nhận");
        holder.tongtien.setText("Với tổng tiền: "+ notification.getTongtien());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView trangthai, nodung, tongtien;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            trangthai = itemView.findViewById(R.id.trangthaidh);
            nodung = itemView.findViewById(R.id.noidungthongbao);
            tongtien = itemView.findViewById(R.id.tiendonhang);
        }
    }
}
