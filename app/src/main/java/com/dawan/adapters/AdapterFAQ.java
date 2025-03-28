package com.dawan.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.R;
import com.dawan.models.ModelFAQ;

import java.util.List;

public class AdapterFAQ extends RecyclerView.Adapter<AdapterFAQ.ViewHolder> {

Context context;
List<ModelFAQ.FAQ> faqList;
ViewHolder prevHolder;

    public AdapterFAQ(Context context, List<ModelFAQ.FAQ> faqList) {
        this.context = context;
        this.faqList = faqList;
    }

    @NonNull
    @Override
    public AdapterFAQ.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_faq,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFAQ.ViewHolder holder, int position) {
        ModelFAQ.FAQ faq=faqList.get(position);
        if(holder.isExpand){
            expand(holder);
        }
        else {
            unexpand(holder);
        }
        holder.tv_que.setText(faq.getQuestion());
        holder.tv_ans.setText(faq.getAnswer());
        holder.tr_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.isExpand){
                    unexpand(holder);
                }
                else {
                    expand(holder);
                }
            }
        });

    }

    private void expand(ViewHolder holder) {
        if(prevHolder!=null){
            unexpand(prevHolder);
        }
        holder.iv_expand.setRotation(0);
        holder.tv_ans.setVisibility(View.VISIBLE);
        holder.rl_faq.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.faq_bg,null));
        holder.isExpand=true;
        prevHolder=holder;
    }
    private void unexpand(ViewHolder holder) {
        holder.iv_expand.setRotation(180);
        holder.tv_ans.setVisibility(View.GONE);
        holder.rl_faq.setBackgroundColor(Color.WHITE);
        holder.isExpand=false;
    }

    @Override
    public int getItemCount() {
        return faqList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_expand;
        TextView tv_que,tv_ans;
        RelativeLayout rl_faq;
        TableRow tr_title;
        boolean isExpand=false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_expand=itemView.findViewById(R.id.iv_expand);
            tv_que=itemView.findViewById(R.id.tv_question);
            tv_ans=itemView.findViewById(R.id.tv_answer);
            rl_faq=itemView.findViewById(R.id.rl_faq);
            tr_title=itemView.findViewById(R.id.tr_title);
        }
    }
}
