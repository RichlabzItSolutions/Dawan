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

public class AdapterSubNewsWithImage extends RecyclerView.Adapter<AdapterSubNewsWithImage.ViewHolder> {
    List<ModelNews.subnews> subnewsList;
    Context context;

    public AdapterSubNewsWithImage(Context context, List<ModelNews.subnews> subnewsList) {
        this.subnewsList = subnewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterSubNewsWithImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subnews_with_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubNewsWithImage.ViewHolder holder, int position) {

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
