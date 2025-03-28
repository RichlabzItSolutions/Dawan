package com.dawan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.R;
import com.dawan.models.ModelNews;

import java.util.List;

public class AdapterNewsTrending extends RecyclerView.Adapter<AdapterNewsTrending.ViewHolder> {
    List<ModelNews> newsList;
    Context context;

    public AdapterNewsTrending(List<ModelNews> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNewsTrending.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_trending_news,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNewsTrending.ViewHolder holder, int position) {
        ModelNews modelNews=newsList.get(position);
        if(modelNews.isIsvideo()){
            holder.iv_play.setVisibility(View.VISIBLE);
        }
        else {
            holder.iv_play.setVisibility(View.GONE);
        }
        holder.rv_subnews.setLayoutManager(new LinearLayoutManager(context));
        holder.rv_subnews.setNestedScrollingEnabled(false);
        holder.rv_subnews.setAdapter(new AdapterSubNewsTrending(context,modelNews.getSubnewslist()));

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_subnews;
        ImageView iv_play;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_subnews=itemView.findViewById(R.id.rv_subnews);
            iv_play=itemView.findViewById(R.id.iv_play);
        }
    }
}
