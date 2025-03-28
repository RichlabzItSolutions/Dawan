package com.dawan.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dawan.NotificationDetailsActivity;
import com.dawan.R;
import com.dawan.models.ModelNews;
import com.dawan.models.ModelNotification;
import com.dawan.utils.Constants;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.ViewHolder> {
    Context context;
    List<ModelNotification.Notification> notificationList;

    public AdapterNotifications(Context context,List<ModelNotification.Notification> notificationList) {
        this.context = context;
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public AdapterNotifications.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifications,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNotifications.ViewHolder holder, int position) {
        ModelNotification.Notification notification=notificationList.get(position);
        Glide.with(context).load(notification.getImage()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_noti);
        holder.tv_title.setText(notification.getTitle());
        holder.tv_desc.setText(notification.getDescription());
        setTime(holder.tv_time,notification.getAddedOn());
        Log.d("time", notification.getAddedOn());
        holder.itemView.setOnClickListener(v->{
            Constants.SelectedNotification=notificationList.get(holder.getAdapterPosition());
            context.startActivity(new Intent(context, NotificationDetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_time,tv_desc;
        ImageView iv_noti;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_noti=itemView.findViewById(R.id.iv_noti);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_desc=itemView.findViewById(R.id.tv_desc);

        }
    }
    public void setTime(TextView textView,String date){
        long currentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date pastTime = dateFormat.parse(date);
            long pastTimeMillis = pastTime.getTime();
            long timeDifferenceMillis = currentTimeMillis - pastTimeMillis;
            String timeAgo = Constants.getTimeAgoString(timeDifferenceMillis);
            textView.setText(String.valueOf(timeAgo));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
