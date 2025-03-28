package com.dawan.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.R;
import com.dawan.models.ModelNews;

import java.util.List;

public class AdapterSubNews extends RecyclerView.Adapter<AdapterSubNews.ViewHolder> {
    List<ModelNews.subnews> subnewsList;
    Context context;

    public AdapterSubNews( Context context,List<ModelNews.subnews> subnewsList) {
        this.subnewsList = subnewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSubNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subnews_without_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubNews.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return subnewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
