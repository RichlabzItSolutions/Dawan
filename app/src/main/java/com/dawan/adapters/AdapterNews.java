package com.dawan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.MainActivity;
import com.dawan.R;
import com.dawan.fragments.FragmentLatestVideos;
import com.dawan.models.ModelNews;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> {
    List<ModelNews> newsList;
    Context context;

    public AdapterNews(List<ModelNews> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNews.ViewHolder holder, int position) {
        ModelNews modelNews=newsList.get(position);
        if(modelNews.isIsvideo()){
            holder.iv_play.setVisibility(View.VISIBLE);
            holder.iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).loadFrag(new FragmentLatestVideos());
                }
            });
            holder.iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).loadFrag(new FragmentLatestVideos());
                }
            });
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).loadFrag(new FragmentLatestVideos());
                }
            });
            holder.tv_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).loadFrag(new FragmentLatestVideos());
                }
            });
        }
        else {
            holder.iv_play.setVisibility(View.GONE);
        }
        if(modelNews.isIsheader()){
            holder.tv_title.setVisibility(View.VISIBLE);
        }
        else {
            holder.tv_title.setVisibility(View.GONE);
        }
        holder.rv_subnews.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_subnews.setAdapter(new AdapterSubNews(context,modelNews.getSubnewslist()));

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_subnews;
        ImageView iv_play,iv;
        TextView tv_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_subnews=itemView.findViewById(R.id.rv_subnews);
            iv_play=itemView.findViewById(R.id.iv_play);
            iv=itemView.findViewById(R.id.iv1);
            tv_title=itemView.findViewById(R.id.tv_title);
        }
    }
}
