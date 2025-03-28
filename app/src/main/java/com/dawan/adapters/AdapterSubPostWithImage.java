package com.dawan.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dawan.R;
import com.dawan.PostDetailsActivity;
import com.dawan.models.ModelPost;
import com.dawan.utils.Constants;
import com.dawan.utils.FileTypeAsyncTask;
import com.dawan.utils.WebServices;

import java.util.List;

public class AdapterSubPostWithImage extends RecyclerView.Adapter<AdapterSubPostWithImage.ViewHolder> {
    List<ModelPost.post> subnewsList;
    Context context;
    boolean saveLayout;
    WebServices webServices;
    FileTypeAsyncTask task;

    public AdapterSubPostWithImage(Context context, List<ModelPost.post> subnewsList,boolean saveLayout) {
        this.subnewsList = subnewsList;
        this.context = context;
        this.saveLayout=saveLayout;
        webServices=new WebServices(context);
    }

    @NonNull
    @Override
    public AdapterSubPostWithImage.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subnews_with_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSubPostWithImage.ViewHolder holder, int position) {
        ModelPost.post post = subnewsList.get(position);
        holder.tv_title.setText(post.getTitle());
         task = new FileTypeAsyncTask(new FileTypeAsyncTask.FileTypeCallback() {
            @Override
            public void onFileTypeReceived(FileTypeAsyncTask.FileType fileType) {
                Log.e("fileType",""+fileType);
                switch (fileType) {
                    case IMAGE:
                        holder.iv_play.setVisibility(View.GONE);
                        break;
                    case VIDEO:
                        holder.iv_play.setVisibility(View.VISIBLE);
                        break;
                    case UNKNOWN:
                        holder.iv_play.setVisibility(View.GONE);
                        break;
                }
            }
        });
        task.execute(post.getFile());


        Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_sub_news);
        holder.itemView.setOnClickListener(v->{
            context.startActivity(new Intent(context, PostDetailsActivity.class)
                    .putExtra("postid",subnewsList.get(holder.getAdapterPosition()).getId())
                    .putExtra("amount",subnewsList.get(holder.getAdapterPosition()).getPaid_amount())
                    .putExtra("paid",subnewsList.get(holder.getAdapterPosition()).getIs_paid())
            );
        });
        if(saveLayout){
            holder.iv_removeSave.setVisibility(View.VISIBLE);
            holder.iv_removeSave.setOnClickListener(v->{
                RemoveSaved(holder.getAdapterPosition(),subnewsList.get(holder.getAdapterPosition()).getId());
            });
        }
    }

    @Override
    public int getItemCount() {
        return subnewsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_sub_news,iv_removeSave,iv_play;
        TextView tv_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_sub_news = itemView.findViewById(R.id.iv_sub_news);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_removeSave = itemView.findViewById(R.id.iv_remove);
            iv_play = itemView.findViewById(R.id.iv_play);
        }
    }
    private void RemoveSaved(int pos,String id) {
        webServices.RemoveSavedPost(id, new WebServices.onResponse() {
            @Override
            public void onResponse() {
                subnewsList.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        task.cancel(true);
        webServices.dismissDialog();
    }
}
