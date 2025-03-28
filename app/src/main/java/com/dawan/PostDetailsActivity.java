package com.dawan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.dawan.adapters.AdapterSubPaidPostWithImage;
import com.dawan.adapters.AdapterSubPostWithImage;

import com.dawan.databinding.ActivityPostDetailsBinding;
import com.dawan.fragments.BottomSheetComments;
import com.dawan.fragments.BottomSheetLogin;
import com.dawan.fragments.FragmentSaved;
import com.dawan.models.ModelPost;
import com.dawan.models.paymentPaidPostJson;
import com.dawan.models.paymentPaidPostResponse;
import com.dawan.utils.API;
import com.dawan.utils.Constants;
import com.dawan.utils.CustomWaitDialog;
import com.dawan.utils.FileTypeAsyncTask;
import com.dawan.utils.RetrofitClient;
import com.dawan.utils.SharedPref;
import com.dawan.utils.WebServices;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailsActivity extends AppCompatActivity {
    WebServices webServices;
    ActivityPostDetailsBinding binding;

    //TextToSpeech textToSpeech;
    String amount, paid;

    CustomWaitDialog cwd;

    API api;
    String token;

    TextView masterCardId, evcID, paybleAmoutId, proceedToPayId, PaymentStatusMessage, PaymentStatusDescription;

    ImageView closeId, closeMaId, PaymentStatusId, closePaymentStatusId;

    EditText etMobileNumberID;

    String MobileNumber, paymentStatus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPostDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices=new WebServices(PostDetailsActivity.this);

       /* SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sh.getString("token", "");

        Log.d("njm",  token);*/


        SharedPreferences sh1 = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        token = sh1.getString("tokenbottam", "");

        //Log.d("njo",  token);

        api = RetrofitClient.getRetrofitInstance().create(API.class);
        cwd = new CustomWaitDialog(PostDetailsActivity.this);
       // new SharedPref(PostDetailsActivity.this).getString(SharedPref.token);
       // token =  SharedPref(PostDetailsActivity.this).getString(SharedPref.token);






       // Dialog dialog = new Dialog(PostDetailsActivity.this);

       // Dialog dialog1 = new Dialog(PostDetailsActivity.this);

        //Dialog dialog2 = new Dialog(PostDetailsActivity.this);

        amount = getIntent().getStringExtra("amount");
        paid = getIntent().getStringExtra("paid");

        //Log.d("amount", amount);
       // Log.d("paid", paid);

        binding.fabComments.setOnClickListener(v->{
           if(new SharedPref(PostDetailsActivity.this).getString(SharedPref.token).isEmpty()||new SharedPref(PostDetailsActivity.this).getString(SharedPref.token)==null){
               Toast.makeText(PostDetailsActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
               BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
               bottomSheetLogin.show(getSupportFragmentManager(), bottomSheetLogin.getTag());
           }else {
               Constants.PostID = getIntent().getStringExtra("postid");
                //amount = getIntent().getStringExtra("amount");
               BottomSheetComments bottomSheetComments = new BottomSheetComments();
               bottomSheetComments.show(getSupportFragmentManager(), "BottomSheetComments");
           }
       });
       
       binding.textView.setOnClickListener(v->{
           getOnBackPressedDispatcher().onBackPressed();
       });
       setPostDetails();

    }



    // Do something with the data coming from the AlertDialog
    private void sendDialogDataToActivity(String data) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

    private void setPostDetails() {
        webServices.GetPostDetails(getIntent().getStringExtra("postid"), new WebServices.onGetPostDetails() {
            @Override
            public void onGotPostDetails(ModelPost.postDetailsResponse response) {
                binding.tvDate.setText(Constants.formatDate(response.getPostDate()));
                binding.tvTitle.setText(response.getTitle());
                //response.setFile("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
                FileTypeAsyncTask task = new FileTypeAsyncTask(new FileTypeAsyncTask.FileTypeCallback() {
                    @Override
                    public void onFileTypeReceived(FileTypeAsyncTask.FileType fileType) {
                        //Log.e("fileType", "" + fileType);
                        switch (fileType) {
                            case IMAGE:
                                binding.vv.setVisibility(View.GONE);
                                binding.iv1.setVisibility(View.VISIBLE);
                                break;
                            case VIDEO:
                                binding.vv.setVisibility(View.VISIBLE);
                                binding.iv1.setVisibility(View.GONE);
                                binding.vv.setVideoURI(Uri.parse(response.getFile()));
                                binding.vv.setMediaController(new MediaController(PostDetailsActivity.this));
                                binding.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                binding.vv.setMediaController(null);
                                break;
                            case UNKNOWN:
                                binding.vv.setVisibility(View.GONE);
                                binding.iv1.setVisibility(View.VISIBLE);
                                break;
                        }
                    }
                });
                task.execute(response.getFile());


                Dialog dialog = new Dialog(PostDetailsActivity.this);

                Dialog dialog1 = new Dialog(PostDetailsActivity.this);


               /* if (paid.equals("No")){

                    binding.wv.setVisibility(View.GONE);
                }
                else{

                    binding.wv.setVisibility(View.VISIBLE);
                    String content =response.getPostContent();

                    Log.d("asd",response.getPostContent());
                    content=content.replace("\\n","<br>");
                    content=content.replace("\\","");
                    content=content.replace("<img","<img style='max-width:100%; height:auto;' ");
                    binding.wv.loadData(content, "text/html", "UTF-8");
                    binding.wv.loadData(content, "text/html", "UTF-8");

                }*/



                if (amount == null ||  amount.isEmpty() ||  amount.equals("0") || paid == ("Yes")) {

                    binding.tvPayNow.setVisibility(View.GONE);
                    binding.tvPaid.setVisibility(View.GONE);
                    //  binding.tvDesc.setVisibility(View.VISIBLE);
                    binding.wv.setVisibility(View.VISIBLE);


                    String content =response.getPostContent();

                   // Log.d("asd",response.getPostContent());
                    content=content.replace("\\n","<br>");
                    content=content.replace("\\","");
                    content=content.replace("<img","<img style='max-width:100%; height:auto;' ");
                    binding.wv.loadData(content, "text/html", "UTF-8");
                    binding.wv.loadData(content, "text/html", "UTF-8");



                }
                else{

                    binding.textView.setText("Premium Posts");
                    binding.tvPayNow.setVisibility(View.VISIBLE);
                    binding.tvPaid.setVisibility(View.VISIBLE);
                    binding.tvPaid.setText("USD : "+amount);
                    //  binding.tvDesc.setVisibility(View.GONE);
                    //binding.wv.setVisibility(View.GONE);



                    String contentshort = response.getPostContent(); // Replace with actual response content

                    // Extract the first paragraph
                    String firstParagraph = extractFirstParagraph(contentshort);

                   /* // Display the first paragraph in a TextView
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(firstParagraph);*/


                    binding.wv.loadData(firstParagraph, "text/html", "UTF-8");
                  //  binding.wv.loadData(contentshort, "text/html", "UTF-8");


                    binding.tvPayNow.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(new SharedPref(PostDetailsActivity.this).getString(SharedPref.token).isEmpty()||new SharedPref(PostDetailsActivity.this).getString(SharedPref.token)==null){
                                Toast.makeText(PostDetailsActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
                                BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                                bottomSheetLogin.show(getSupportFragmentManager(), bottomSheetLogin.getTag());

                            }else {

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

                                paybleAmoutId.setText(amount+" USD");

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

                                        Dialog dialog3 = new Dialog(PostDetailsActivity.this);

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


                               /* etMobileNumberID.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                                        // No action needed here
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                        if (s.charAt(0) != '6') {
                                            etMobileNumberID.setText("");
                                            etMobileNumberID.setText("Number must start with 6");
                                            //etMobileNumberID.setTextColor(Color.RED);
                                        } else {
                                            etMobileNumberID.setText("");
                                        }
                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        // No action needed here
                                    }
                                });*/


                                        paybleAmoutId.setText(amount+" USD");

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

                                                String name="123456789";

                                                //String name="123456789";

                                               /*   if (!MobileNumber.substring(0,2).equals("63")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }

                                                 if (!MobileNumber.substring(0,2).equals("68")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }

                                                 if (!MobileNumber.substring(0,2).equals("90")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }*/



                                               // Log.d("fgt", name.substring(0,1));
                                                if (MobileNumber.isEmpty()) {
                                                    Toast.makeText(PostDetailsActivity.this, "Please Enter EVC Mobile Number", Toast.LENGTH_SHORT).show();
                                                }
                                                else if (MobileNumber.length() < 9) {

                                                    Toast.makeText(PostDetailsActivity.this, "Please Enter Your Valid EVC Mobile Number", Toast.LENGTH_SHORT).show();

                                                }else if (!MobileNumber.substring(0,2).equals("61") ==  !MobileNumber.substring(0,2).equals("63") ==  !MobileNumber.substring(0,2).equals("68") ==  !MobileNumber.substring(0,2).equals("90") ){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }
/*                                                else if (!MobileNumber.substring(0,2).equals("63")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }

                                                else if (!MobileNumber.substring(0,2).equals("68")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }

                                                else if (!MobileNumber.substring(0,2).equals("90")){

                                                    Toast.makeText(PostDetailsActivity.this, "EVC start From 61, 63, 68 or 90 Number", Toast.LENGTH_SHORT).show();

                                                }*/
                                                else{

                                                    getPaymentStatus();

                                                    dialog3.dismiss();



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

                                        paybleAmoutId.setText(amount+" USD");

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
                                                    Toast.makeText(PostDetailsActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                                                }
                                                else{
                                                    dialog1.dismiss();

                                                    getPaymentStatus();


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






                  /*  binding.rvSubnews.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));
                    binding.rvSubnews.setNestedScrollingEnabled(false);
                    binding.rvSubnews.setAdapter(new AdapterSubPaidPostWithImage(PostDetailsActivity.this, response.getSubPostList(),false));*/



                }



                /*String content =response.getPostContent();

                Log.d("asd",response.getPostContent());
                content=content.replace("\\n","<br>");
                content=content.replace("\\","");
                content=content.replace("<img","<img style='max-width:100%; height:auto;' ");
                binding.wv.loadData(content, "text/html", "UTF-8");*/
                //binding.tvDesc.setText(Html.fromHtml(content));
                binding.tvCat.setText(response.getCategoryName());

              //  binding.tvPaid

               /* textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {

                        // if No error is found then only it will run
                        if(i!=TextToSpeech.ERROR){
                            // To Choose language of speech
                            textToSpeech.setLanguage(Locale.UK);
                        }
                    }
                });

                // Adding OnClickListener
                binding.btnText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textToSpeech.speak(response.getPostContent(),TextToSpeech.QUEUE_FLUSH,null);
                    }
                });*/



                Glide.with(PostDetailsActivity.this).load(response.getFile()).error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg").into(binding.iv1);

                binding.rvSubnews.setLayoutManager(new LinearLayoutManager(PostDetailsActivity.this));
                binding.rvSubnews.setNestedScrollingEnabled(false);
                binding.rvSubnews.setAdapter(new AdapterSubPostWithImage(PostDetailsActivity.this, response.getSubPostList(),false));
                if(response.isSaved()){
                    binding.tvSave.setText("Saved");
                    Drawable drawable = ResourcesCompat.getDrawable(PostDetailsActivity.this.getResources(), R.drawable.baseline_bookmark_24, null);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        binding.tvSave.setCompoundDrawables(null, null, drawable, null);
                    }
                }else {
                   // binding.tvSave.setText("Save News");
                    Drawable drawable = ResourcesCompat.getDrawable(PostDetailsActivity.this.getResources(), R.drawable.baseline_bookmark_border_24, null);
                    if (drawable != null) {
                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                        binding.tvSave.setCompoundDrawables(null, null, drawable, null);
                    }
                }
                binding.tvSave.setOnClickListener(v -> {
                    if(new SharedPref(PostDetailsActivity.this).getString(SharedPref.token).isEmpty()||new SharedPref(PostDetailsActivity.this).getString(SharedPref.token)==null){
                        Toast.makeText(PostDetailsActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
                        BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                        bottomSheetLogin.show(getSupportFragmentManager(), bottomSheetLogin.getTag());
                    }else {
                        if (response.isSaved()) {
                            RemoveSaved(response);

                        } else {
                            SavePost(response);
                        }
                    }
                });
                binding.tvShare.setOnClickListener(v->{
                    String text=response.getTitle()+"\n\n"+"https://dawan.so/"+response.getPostName()+"/";
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                });

            }
        });

    }


    public void dismissDialog() {
        if (cwd != null && cwd.isShowing()) {
            cwd.dismiss();
        }
    }

    private void RemoveSaved(ModelPost.postDetailsResponse response) {
        webServices.RemoveSavedPost(response.getId(), new WebServices.onResponse() {
            @Override
            public void onResponse() {
                response.setSaved(false);
//                binding.tvSave.setText("Save News");
                Drawable drawable = ResourcesCompat.getDrawable(PostDetailsActivity.this.getResources(), R.drawable.baseline_bookmark_border_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    binding.tvSave.setCompoundDrawables(null, null, drawable, null);
                }

            }
        });
    }

    private void SavePost(ModelPost.postDetailsResponse response) {
        webServices.SavePost(response.getId(), new WebServices.onResponse() {
            @Override
            public void onResponse() {
                response.setSaved(true);
                binding.tvSave.setText("Saved");
                Drawable drawable = ResourcesCompat.getDrawable(PostDetailsActivity.this.getResources(), R.drawable.baseline_bookmark_24, null);
                if (drawable != null) {
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    binding.tvSave.setCompoundDrawables(null, null, drawable, null);
                }
            }
        });
    }

   /* public void dismissDialog() {
        if (cwd != null && cwd.isShowing()) {
            cwd.dismiss();
        }
    }*/

    public void showToast(String msg) {
        Toast.makeText(PostDetailsActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
    }


    private String extractFirstParagraph(String htmlContent) {
        // Parse HTML content with Jsoup
        Document document = Jsoup.parse(htmlContent);

        // Select the first <p> element
        Element firstParagraphElement = document.select("p").first();

        String firstParagraph = firstParagraphElement != null ? firstParagraphElement.text() : "";

        // Return the text of the first paragraph, or an empty string if not found
        return firstParagraph.length() > 100 ? firstParagraph.substring(0, 100) : firstParagraph;
    }

    public void getPaymentStatus(){

       String postId  = getIntent().getStringExtra("postid");

      // Log.d("klp", postId);

        cwd.show();
        api.paymentPaidPost(token, new paymentPaidPostJson(postId, MobileNumber)).enqueue(new Callback<paymentPaidPostResponse>() {
            @Override
            public void onResponse(Call<paymentPaidPostResponse> call, Response<paymentPaidPostResponse> response) {
               cwd.dismiss();
                if(response.body()!=null){
                    paymentPaidPostResponse postDetailsResponse= response.body();

                   // Log.d("kol", response.body().getMessage());

                    paymentStatus =  postDetailsResponse.getStatus();

                    Dialog dialog2 = new Dialog(PostDetailsActivity.this);

                    dialog2.setContentView(R.layout.dialogbox_layout_payment_status);

                    dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog2.setCancelable(false);
                    //  Window window = dialog1.getWindow();
                    //WindowManager.LayoutParams wlp = window.getAttributes();
                    dialog2.setCanceledOnTouchOutside(false);
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
                       // binding.tvDesc.setVisibility(View.GONE);
                        binding.wv.setVisibility(View.GONE);

                    }
                    else{

                        PaymentStatusId.setImageResource(R.drawable.icon_succesfull);
                        PaymentStatusMessage.setText("Payment Successful!");
                        PaymentStatusMessage.setTextColor(Color.parseColor("#35B729"));
                        PaymentStatusDescription.setText("Your Payment USD. " + amount  + " Successfully Done");
                       // binding.tvDesc.setVisibility(View.VISIBLE);
                        binding.wv.setVisibility(View.VISIBLE);

                        Intent intent = new Intent(PostDetailsActivity.this, MainActivity.class);
                        //intent.putExtra("strMobile", strMobile);
                        startActivity(intent);
                        finish();

                    }

                    closePaymentStatusId.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog2.dismiss();

                            Intent intent = new Intent(PostDetailsActivity.this, MainActivity.class);
                            //intent.putExtra("strMobile", strMobile);

                            startActivity(intent);
                            finish();
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
                    getPaymentStatus();
                }
            }

            @Override
            public void onFailure(Call<paymentPaidPostResponse> call, Throwable t) {
                cwd.dismiss();
             //   showToast("something went wrong");
                getPaymentStatus();
                t.printStackTrace();
            }
        });
    }
}
