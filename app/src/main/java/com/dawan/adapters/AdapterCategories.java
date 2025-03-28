package com.dawan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.R;
import com.dawan.models.ModelCategories;
import com.dawan.models.ModelPaidPosts;
import com.dawan.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategories extends RecyclerView.Adapter<AdapterCategories.ViewHolder> {
    Context context;
    List<ModelCategories.Categories> categoriesList;
    onTabClicked onTabClicked;
    List<TextView> tvlist;

    public AdapterCategories(Context context, List<ModelCategories.Categories> categoriesList,onTabClicked onTabClicked) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.onTabClicked=onTabClicked;
        tvlist=new ArrayList<>();
    }

    @NonNull
    @Override
    public AdapterCategories.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCategories.ViewHolder holder, int position) {
        ModelCategories.Categories categories=categoriesList.get(position);
        tvlist.add(holder.tab);
        if(position==0){
            holder.tab.setTextColor(Color.parseColor("#ffffff"));
            holder.tab.setBackgroundResource(R.drawable.custom_tab_indicator);
        }

        String name=categories.getName();
        Log.d("uiop", categories.getName());
        Log.d("cateid", categories.getId());
        String id =  categoriesList.get(position).getId();

        //name=name.substring(0, 1).toUpperCase() + name.substring(1);
        holder.tab.setText(name);
        holder.tab.setOnClickListener(v->{
            for(TextView tv:tvlist){
                tv.setTextColor(Color.parseColor("#000000"));
                tv.setBackground(null);
            }
            holder.tab.setTextColor(Color.parseColor("#ffffff"));
            holder.tab.setBackgroundResource(R.drawable.custom_tab_indicator);
            onTabClicked.TabClicked(categoriesList.get(position).getId());
        });

        /*holder.tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             String id =  categoriesList.get(position).getId();

             if (id.equals("62")){

                 Log.d("cate", id);

             }



            }
        });*/


    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tab;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tab=itemView.findViewById(R.id.tab);
        }
    }

    public interface onTabClicked {
        void TabClicked(String id);
    }
}
