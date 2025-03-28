package com.dawan.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dawan.LoginActivity;
import com.dawan.PostDetailsActivity;
import com.dawan.R;
import com.dawan.fragments.BottomSheetLogin;
import com.dawan.models.ModelGetVideos;
import com.dawan.models.ModelPaidPosts;
import com.dawan.models.VideosPaymentJson;
import com.dawan.models.paymentPaidPostJson;
import com.dawan.models.paymentPaidPostResponse;
import com.dawan.utils.API;
import com.dawan.utils.Constants;
import com.dawan.utils.FileTypeAsyncTask;
import com.dawan.utils.RetrofitClient;
import com.dawan.utils.SharedPref;
import com.dawan.utils.WebServices;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterVideosPost extends RecyclerView.Adapter<AdapterVideosPost.ViewHolder> {
    List<ModelGetVideos.Video> newsList;
    Context context;
    ModelGetVideos.Video post;
    WebServices webServices;
    FragmentManager fragManager;

    //TextToSpeech textToSpeech;
    String amount, paid;

    API api;
    String token;

    TextView masterCardId, evcID, paybleAmoutId, proceedToPayId, PaymentStatusMessage, PaymentStatusDescription;

    ImageView closeId, closeMaId, PaymentStatusId, closePaymentStatusId;

    EditText etMobileNumberID;

    String MobileNumber, paymentStatus;

    private ExecutorService executor;
    private Handler handler;

    public AdapterVideosPost(ModelGetVideos.Video post, List<ModelGetVideos.Video> newsList, Context context,  FragmentManager fragManager) {
        this.post = post;
        this.newsList = newsList;
        this.context = context;
        this.fragManager=fragManager;
        webServices = new WebServices(context);
    }

    @NonNull
    @Override
    public AdapterVideosPost.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_world_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVideosPost.ViewHolder holder, int position) {
        //holder.tv_date.setText(Constants.formatDate(post.getPostDate()));

        SharedPreferences sh1 = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
        token = sh1.getString("tokenbottam", "");

        Log.d("njo",  token);

        webServices = new WebServices(context);

        api = RetrofitClient.getRetrofitInstance().create(API.class);


        holder.tv_title.setText(post.getVideoTitle());
        holder.tv_save.setVisibility(View.GONE);
        holder.tv_cat.setVisibility(View.GONE);



        if (post.getPaid().equals("No")){

            holder.iv_play.setVisibility(View.VISIBLE);
            holder.iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "First is Payment Done then Start the video", Toast.LENGTH_SHORT).show();

                }
            });


        }
        else{

            holder.iv_play.setVisibility(View.VISIBLE);
            holder.videoView.setVisibility(View.VISIBLE);
            holder.iv_main.setVisibility(View.GONE);

            holder.iv_play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.tv_cat.setVisibility(View.GONE);
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

        }


        Dialog dialog = new Dialog(context);

        Dialog dialog1 = new Dialog(context);

        //Dialog dialog2 = new Dialog(PostDetailsActivity.this);

        amount = post.getPaidAmount();

        paid = post.getPaid();

        if (amount == null || amount.isEmpty() || paid == ("Yes")) {

            holder.tv_payNow.setVisibility(View.GONE);
            holder.tv_paid.setVisibility(View.GONE);

            // holder.tvDesc.setVisibility(View.VISIBLE);

        } else {

            holder.tv_payNow.setVisibility(View.VISIBLE);
            holder.tv_paid.setVisibility(View.VISIBLE);
            holder.tv_paid.setText("USD : " + amount);
            //binding.tvDesc.setVisibility(View.GONE);


            holder.tv_payNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (new SharedPref(context).getString(SharedPref.token).isEmpty() || new SharedPref(context).getString(SharedPref.token) == null) {
                        Toast.makeText(context, "You must login first", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);

                        context.startActivity(intent);

                        /*BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                        bottomSheetLogin.show(bottomSheetLogin.getActivity().getSupportFragmentManager(), bottomSheetLogin.getTag());
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();*/


                        // bottomSheetLogin.show(requireActivity().getSupportFragmentManager(), bottomSheetLogin.getTag());

                    } else {

                        dialog.setContentView(R.layout.custom_layout_payment);
                   /* dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    dialog.getWindow().getAttributes().windowAnimations = R.style.animation;*/

                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        Window window = dialog.getWindow();
                        //WindowManager.LayoutParams wlp = window.getAttributes();
                        dialog.setCanceledOnTouchOutside(true);
                        //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        masterCardId = dialog.findViewById(R.id.masterCardId);
                        evcID = dialog.findViewById(R.id.evcID);
                        paybleAmoutId = dialog.findViewById(R.id.paybleAmoutId);
                        closeId = dialog.findViewById(R.id.closeId);

                        paybleAmoutId.setText(amount + " USD");

                        closeId.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                dialog.dismiss();
                                // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                            }
                        });

                        evcID.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();


                                Dialog dialog3 = new Dialog(context);


                                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog3.setCancelable(false);
                                Window window = dialog3.getWindow();
                                //WindowManager.LayoutParams wlp = window.getAttributes();
                                dialog3.setCanceledOnTouchOutside(true);


                                dialog3.setContentView(R.layout.dilogbox_layout_evc);

                                dialog3.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog3.setCancelable(false);
                                //  Window window = dialog1.getWindow();
                                //WindowManager.LayoutParams wlp = window.getAttributes();
                                dialog3.setCanceledOnTouchOutside(true);
                                //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                etMobileNumberID = dialog3.findViewById(R.id.etMobileNumberID);
                                closeMaId = dialog3.findViewById(R.id.closeMaId);
                                proceedToPayId = dialog3.findViewById(R.id.proceedToPayId);



                                paybleAmoutId.setText(amount + " USD");

                                closeMaId.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog3.dismiss();
                                        // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                proceedToPayId.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        MobileNumber = etMobileNumberID.getText().toString();
                                        if (MobileNumber.isEmpty()) {
                                            Toast.makeText(context, "Please Enter EVC Number", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (!MobileNumber.substring(0,2).equals("61")){

                                            Toast.makeText(context, "EVC start From 61 Number", Toast.LENGTH_SHORT).show();

                                        }else {
                                            dialog3.dismiss();

                                            getPaymentStatus(holder);


                                        }

                                    }
                                });

                                dialog3.show();


                            }
                        });

                        masterCardId.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //Toast.makeText(PostDetailsActivity.this, "okay clicked", Toast.LENGTH_SHORT).show();


                                dialog1.setContentView(R.layout.dilogbox_layout_mastercard);

                                dialog1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog1.setCancelable(false);
                                //  Window window = dialog1.getWindow();
                                //WindowManager.LayoutParams wlp = window.getAttributes();
                                dialog1.setCanceledOnTouchOutside(true);
                                //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                etMobileNumberID = dialog1.findViewById(R.id.etMobileNumberID);
                                closeMaId = dialog1.findViewById(R.id.closeMaId);
                                proceedToPayId = dialog1.findViewById(R.id.proceedToPayId);

                                paybleAmoutId.setText(amount + " USD");

                                closeMaId.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog1.dismiss();
                                        // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                proceedToPayId.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        MobileNumber = etMobileNumberID.getText().toString();
                                        if (MobileNumber.isEmpty()) {
                                            Toast.makeText(context, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                                        }  else if (MobileNumber.length() < 9) {

                                            Toast.makeText(context, "Please Enter Your Valid EVC Mobile Number", Toast.LENGTH_SHORT).show();

                                        }else if (!MobileNumber.substring(0,2).equals("61") ==  !MobileNumber.substring(0,2).equals("63") ==  !MobileNumber.substring(0,2).equals("68") ==  !MobileNumber.substring(0,2).equals("90") ){

                                            Toast.makeText(context, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                        } else {
                                            dialog1.dismiss();

                                            getPaymentStatus(holder);


                                        }

                                    }
                                });

                                dialog1.show();

                            }


                        });

                        /*evcID.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                //Toast.makeText(PostDetailsActivity.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
                            }
                        });*/

                        dialog.show();


                    }

                }
            });


        }

       /* holder.iv_play.setOnClickListener(new View.OnClickListener() {
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
        });*/
        holder.tv_paid.setVisibility(View.VISIBLE);
        holder.tv_payNow.setVisibility(View.VISIBLE);
        holder.tv_paid.setText("USD : " + post.getPaidAmount());
       /* holder.tv_payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Pay Now", Toast.LENGTH_SHORT).show();
            }
        });*/

        holder.tv_desc.setVisibility(View.GONE);

        //holder.tv_desc.setText(content);

        holder.iv_main.setVisibility(View.GONE);
        //Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_main);

       // Log.d("sdf", post.getFile());
       // Log.d("bvc", post.getYoutubeLink());

        //Log.d("bnm",post.getPostContent());

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


        //holder.iv_play.setVisibility(View.GONE);
     /*   holder.videoView.setVideoURI(Uri.parse(post.getYoutubeLink()));
        holder.videoView.setMediaController(new MediaController(context));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        holder.videoView.setMediaController(null);*/

        //post.setFile("");
        //post.setFile("https://dawan.so/wp-content/uploads/2024/02/download-1.jpg");
        FileTypeAsyncTask task = new FileTypeAsyncTask(new FileTypeAsyncTask.FileTypeCallback() {
            @Override
            public void onFileTypeReceived(FileTypeAsyncTask.FileType fileType) {
                Log.e("fileType", "" + fileType);
                switch (fileType) {
                    case IMAGE:
                        // holder.videoView.setVisibility(View.GONE);
                        // holder.iv_main.setVisibility(View.VISIBLE);
                        // Glide.with(context).load(post.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(holder.iv_main);
                        break;
                    case VIDEO:
                        holder.iv_play.setVisibility(View.VISIBLE);
                        holder.videoView.setVisibility(View.VISIBLE);
                        holder.iv_main.setVisibility(View.GONE);



                       /* holder.iv_play.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                holder.tv_cat.setVisibility(View.GONE);
                                holder.iv_play.setVisibility(View.GONE);
                              *//*  holder.videoView.setVideoURI(Uri.parse(post.getFile()));
                                holder.videoView.setMediaController(new MediaController(context));
                                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                holder.videoView.setMediaController(null);*//*

                            }
                        });*/

                        break;
                    case UNKNOWN:
                        holder.videoView.setVisibility(View.VISIBLE);
                        //  holder.iv_main.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        task.execute(post.getFile());


       // Log.d("tyu", post.getFile());
    }



    public void showToast(String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
    }


    public void getPaymentStatus(@NonNull AdapterVideosPost.ViewHolder holder){

        String postId  = post.getId();

        Log.d("klp", postId);

        // cwd.show();
        api.paymentVideos(token, new VideosPaymentJson(post.getId(), MobileNumber)).enqueue(new Callback<paymentPaidPostResponse>() {
            @Override
            public void onResponse(Call<paymentPaidPostResponse> call, Response<paymentPaidPostResponse> response) {
                // cwd.dismiss();
                if(response.body()!=null){
                    paymentPaidPostResponse postDetailsResponse= response.body();

                    Log.d("kol", response.body().getMessage());

                    paymentStatus =  postDetailsResponse.getStatus();

                    Dialog dialog2 = new Dialog(context);

                    dialog2.setContentView(R.layout.dialogbox_layout_payment_status);

                    dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog2.setCancelable(false);
                    //  Window window = dialog1.getWindow();
                    //WindowManager.LayoutParams wlp = window.getAttributes();
                    dialog2.setCanceledOnTouchOutside(true);
                    //  window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    PaymentStatusId = dialog2.findViewById(R.id.PaymentStatusId);
                    closePaymentStatusId = dialog2.findViewById(R.id.closePaymentStatusId);
                    PaymentStatusMessage = dialog2.findViewById(R.id.PaymentStatusMessage);
                    PaymentStatusDescription = dialog2.findViewById(R.id.PaymentStatusDescription);


                    if (paymentStatus.equals("Fail")){

                        PaymentStatusId.setImageResource(R.drawable.icon_faild);
                        PaymentStatusMessage.setText("Your Payment denied");
                        PaymentStatusMessage.setTextColor(Color.parseColor("#E4364B"));
                        PaymentStatusDescription.setText("You can retry the payment below to continue this");
                      /*  holder.videoView.setVideoURI(Uri.parse(post.getFile()));
                        holder.videoView.setMediaController(new MediaController(context));
                        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                        holder.videoView.setMediaController(null);*/
                       // binding.tvDesc.setVisibility(View.GONE);
                    }
                    else{

                        PaymentStatusId.setImageResource(R.drawable.icon_succesfull);
                        PaymentStatusMessage.setText("Payment Successful!");
                        PaymentStatusMessage.setTextColor(Color.parseColor("#35B729"));
                        PaymentStatusDescription.setText("Your Payment USD. " + amount  + " Successfully Done");
                      //  binding.tvDesc.setVisibility(View.VISIBLE);
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



                    closePaymentStatusId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog2.dismiss();
                            // Toast.makeText(PostDetailsActivity.this, "clicked", Toast.LENGTH_SHORT).show();

                        }
                    });



                    dialog2.show();
/*
                    if(postDetailsResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        //onGetPostDetails.onGotPostDetails(postDetailsResponse);

                        showToast(postDetailsResponse.getMessage());

                    }
                    else {
                        showToast(postDetailsResponse.getMessage());
                    }*/

                }
                else {
                   // showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<paymentPaidPostResponse> call, Throwable t) {
                //cwd.dismiss();
               // showToast("something went wrong");
                t.printStackTrace();
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
            tv_payNow = itemView.findViewById(R.id.tv_payNow);
            tv_title = itemView.findViewById(R.id.tv_heading);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_save = itemView.findViewById(R.id.tv_save);
            tv_cat = itemView.findViewById(R.id.tv_cat);
            videoView = itemView.findViewById(R.id.videoViews);

        }
    }
}
