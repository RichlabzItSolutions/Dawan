package com.dawan.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dawan.PostDetailsActivity;
import com.dawan.R;
import com.dawan.fragments.BottomSheetLogin;
import com.dawan.models.ModelPaidPosts;
import com.dawan.models.ModelPost;
import com.dawan.utils.Constants;
import com.dawan.utils.FileTypeAsyncTask;
import com.dawan.utils.SharedPref;
import com.dawan.utils.WebServices;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdapterPaidPosts extends RecyclerView.Adapter<AdapterPaidPosts.ViewHolder> {
    List<ModelPaidPosts.paidPost> newsList;
    Context context;
    ModelPaidPosts.paidPost post;
    WebServices webServices;
    FragmentManager fragManager;

    private ExecutorService executor;
    private Handler handler;

    public AdapterPaidPosts(ModelPaidPosts.paidPost post, List<ModelPaidPosts.paidPost> newsList, Context context, FragmentManager fragManager) {
        this.post = post;
        this.newsList = newsList;
        this.context = context;
        this.fragManager=fragManager;
        webServices = new WebServices(context);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterPaidPosts.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_world_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaidPosts.ViewHolder holder, int position) {
            holder.tv_date.setText(Constants.formatDate(post.getPostDate()));
            holder.tv_title.setText(post.getTitle());
            /*holder.tv_paid.setVisibility(View.VISIBLE);
            holder.tv_payNow.setVisibility(View.VISIBLE);
            holder.tv_paid.setText("USD : "+post.getPaidAmount());
            holder.tv_payNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Pay Now", Toast.LENGTH_SHORT).show();
                }
            });*/
            String content = post.getPostContent();
            holder.tv_desc.setText(Html.fromHtml(content));

          holder.tv_desc.setVisibility(View.VISIBLE);

        //holder.tv_desc.setText(content);

          holder.iv_main.setVisibility(View.VISIBLE);
            //Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_main);

        Log.d("sdf",post.getCategoryName());

        Log.d("bnm",post.getPostContent());

       /* executor = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());*/



       /* String input = "123456789";
        String lastFourChars = "";

        if (post.getFile().length() > 3) {
            lastFourChars = post.getFile().substring(post.getFile().length() - 3);
            Log.d("sdi", lastFourChars);

        } else {
            lastFourChars = post.getFile();
            Log.d("sdl", lastFourChars);

        }

        Log.d("sdo", lastFourChars);

        int n = post.getFile().length();*/

        //post.setFile("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
        //post.setFile("https://dawan.so/wp-content/uploads/2024/02/download-1.jpg");
        FileTypeAsyncTask task = new FileTypeAsyncTask(new FileTypeAsyncTask.FileTypeCallback() {
            @Override
            public void onFileTypeReceived(FileTypeAsyncTask.FileType fileType) {
                Log.e("fileType", "" + fileType);
                switch (fileType) {
                    case IMAGE:
                        holder.videoView.setVisibility(View.GONE);
                        holder.iv_main.setVisibility(View.VISIBLE);
                        Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_main);
                        break;
                    case VIDEO:
                        holder.iv_play.setVisibility(View.VISIBLE);
                        holder.videoView.setVisibility(View.VISIBLE);
                        holder.iv_main.setVisibility(View.GONE);

                        holder.iv_play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                holder.iv_play.setVisibility(View.GONE);
                                holder.videoView.setVideoURI(Uri.parse(post.getFile()));
                                holder.videoView.setMediaController(new MediaController(context));
                                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                holder.videoView.setMediaController(null);

                            }
                        });

                        break;
                    case UNKNOWN:
                        holder.videoView.setVisibility(View.GONE);
                        holder.iv_main.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        task.execute(post.getFile());




        /*if (lastFourChars.equals("jpg")){

            holder.iv_main.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.GONE);
            Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_main);

        }
        else {

            holder.iv_main.setVisibility(View.GONE);
            holder.videoView.setVisibility(View.VISIBLE);

            String videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4";
            // Uri object to refer the
            // resource from the videoUrl
            Uri uri = Uri.parse(videoUrl);

            // sets the resource from the
            // videoUrl to the videoView
            holder.videoView.setVideoURI(uri);

            // creating object of
            // media controller class
            MediaController mediaController = new MediaController(context);

            // sets the anchor view
            // anchor view for the videoView
            mediaController.setAnchorView(holder.videoView);

            // sets the media player to the videoView
            mediaController.setMediaPlayer(holder.videoView);

            // sets the media controller to the videoView
            holder.videoView.setMediaController(mediaController);

            // starts the video
            holder.videoView.start();

        }*/

        Log.d("tyu", post.getFile());
            holder.tv_cat.setText(post.getCategoryName());
            holder.rv_subnews.setLayoutManager(new LinearLayoutManager(context));
            holder.rv_subnews.setNestedScrollingEnabled(false);
            holder.rv_subnews.setAdapter(new AdapterSubPaidPostWithImage(context, newsList, false));
            if (post.getSaved()) {
                holder.tv_save.setText("Saved");
                Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.baseline_bookmark_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    holder.tv_save.setCompoundDrawables(null, null, drawable, null);
                }
            } else {
//                holder.tv_save.setText("Save News");
                Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.baseline_bookmark_border_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    holder.tv_save.setCompoundDrawables(null, null, drawable, null);
                }
            }
            holder.tv_save.setOnClickListener(v -> {
                if (new SharedPref(context).getString(SharedPref.token).isEmpty() || new SharedPref(context).getString(SharedPref.token) == null) {
                    Toast.makeText(context, "You must login first", Toast.LENGTH_SHORT).show();
                    BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                    bottomSheetLogin.show(fragManager, bottomSheetLogin.getTag());
                } else {
                    if (post.getSaved()) {
                        RemoveSaved(holder);

                    } else {
                        SavePost(holder);
                    }
                }
            });


        Log.d("amount", post.getPaidAmount());
        Log.d("paid", post.getPaid());

            holder.itemView.setOnClickListener(v -> {

                context.startActivity(new Intent(context, PostDetailsActivity.class)
                        .putExtra("postid", post.getId())
                        .putExtra("amount", post.getPaidAmount())
                        .putExtra("paid", post.getPaid()));

            });

    }

    private void RemoveSaved(ViewHolder holder) {
        webServices.RemoveSavedPost(post.getId(), new WebServices.onResponse() {
            @Override
            public void onResponse() {
                post.setSaved(false);
//                holder.tv_save.setText("Save News");
                Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.baseline_bookmark_border_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    holder.tv_save.setCompoundDrawables(null, null, drawable, null);
                }

            }
        });
    }

    private void SavePost(ViewHolder holder) {
        webServices.SavePost(post.getId(), new WebServices.onResponse() {
            @Override
            public void onResponse() {
                post.setSaved(true);
                holder.tv_save.setText("Saved");
                Drawable drawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.baseline_bookmark_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    holder.tv_save.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        webServices.dismissDialog();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv_subnews;
        TextView tv_date, tv_paid, tv_payNow, tv_title, tv_desc, tv_save,tv_cat;
        ImageView iv_main;
        ImageView iv_play;
        VideoView videoView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_subnews = itemView.findViewById(R.id.rv_subnews);
            iv_play = itemView.findViewById(R.id.iv_play);
            iv_main = itemView.findViewById(R.id.iv1);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_paid = itemView.findViewById(R.id.tv_paid);
            //tv_payNow = itemView.findViewById(R.id.tv_payNow);
            tv_title = itemView.findViewById(R.id.tv_heading);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_save = itemView.findViewById(R.id.tv_save);
            tv_cat = itemView.findViewById(R.id.tv_cat);
            videoView = itemView.findViewById(R.id.videoViews);

        }
    }
}
