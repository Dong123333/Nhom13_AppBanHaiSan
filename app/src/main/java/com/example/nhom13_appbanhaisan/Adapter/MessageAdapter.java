package com.example.nhom13_appbanhaisan.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.nhom13_appbanhaisan.Model.Message;

import com.example.nhom13_appbanhaisan.R;
import java.util.List;


public class MessageAdapter extends ArrayAdapter<Message> {
    private Context context;
    private List<Message> objects;
    private String currentUserId;

    public MessageAdapter(@NonNull Context context, @NonNull List<Message> objects, String currentUserId) {
        super(context, R.layout.messager_item, objects);
        this.context = context;
        this.objects = objects;
        this.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.messager_item, parent, false);

        TextView text = convertView.findViewById(R.id.textMS);
        Message message = objects.get(position);
        text.setText(message.getText());

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) text.getLayoutParams();

        if (currentUserId.equals(message.getSenderId())) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            text.setTextColor(ContextCompat.getColor(context, R.color.blue));
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            text.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        return convertView;

    }
}
