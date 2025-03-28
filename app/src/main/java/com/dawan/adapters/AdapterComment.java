package com.dawan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.R;
import com.dawan.models.ModelComments;
import com.dawan.models.ModelFAQ;

import java.util.List;

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

Context context;
List<ModelComments.comment> commentList;

    public AdapterComment(Context context, List<ModelComments.comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public AdapterComment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterComment.ViewHolder holder, int position) {
        ModelComments.comment comment=commentList.get(position);
        holder.tv_name.setText(comment.getCommenterName());
        holder.tv_comment.setText(comment.getCommentContent());
    }


    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_comment;
        boolean isExpand=false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.tv_name);
            tv_comment=itemView.findViewById(R.id.tv_comment);
        }
    }
}
