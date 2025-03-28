package com.dawan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.dawan.models.ModelCategories;
import com.dawan.models.ModelChangePassword;
import com.dawan.models.ModelComments;
import com.dawan.models.ModelCommonResponse;
import com.dawan.models.ModelFAQ;
import com.dawan.models.ModelFeedback;
import com.dawan.models.ModelGetVideos;
import com.dawan.models.ModelLoginRegister;
import com.dawan.models.ModelNotification;
import com.dawan.models.ModelPaidPosts;
import com.dawan.models.ModelPost;
import com.dawan.models.ModelQuerry;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServices {
    Context context;
    CustomWaitDialog cwd;
    API api;
    String token;
    String tokenbottam;
    private static Toast currentToast;

    public WebServices(Context context) {
        this.context = context;
        api = RetrofitClient.getRetrofitInstance().create(API.class);
        cwd = new CustomWaitDialog(context);
        token = new SharedPref(context).getString(SharedPref.token);

    }

    public void dismissDialog() {
        if (cwd != null && cwd.isShowing()) {
            cwd.dismiss();
        }
    }
    public void showToast(String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
    }

    public void Register(ModelLoginRegister.RegisterRequest request,onResponse onResponse){
        cwd.show();
        api.register(request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void Login(ModelLoginRegister.LoginRequest request,onResponse onResponse){
        cwd.show();
        api.login(request).enqueue(new Callback<ModelLoginRegister.LoginResponse>() {
            @Override
            public void onResponse(Call<ModelLoginRegister.LoginResponse> call, Response<ModelLoginRegister.LoginResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelLoginRegister.LoginResponse loginResponse= response.body();
                    showToast(loginResponse.getMessage());
                    if(loginResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        SharedPref sharedPref=new SharedPref(context);
                        sharedPref.setString(SharedPref.token,loginResponse.getData().getToken());
                        sharedPref.setString(SharedPref.id,loginResponse.getData().getId());
                        sharedPref.setString(SharedPref.name,loginResponse.getData().getName());
                        sharedPref.setString(SharedPref.email,loginResponse.getData().getEmail());
                        sharedPref.setString(SharedPref.mobile,loginResponse.getData().getMobile());


                        Log.d("tokan", loginResponse.getData().getToken());

                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelLoginRegister.LoginResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void RequestOtp(ModelChangePassword.ChangePasswordRequest request,onResponse onResponse){
        cwd.show();
        api.requestOtp(request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void ChangePassword(ModelChangePassword.ChangePasswordRequest request,onResponse onResponse){
        cwd.show();
        api.changePassword(request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }
                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void GetQueryList(onGetQueryList onGetQueryList){
        cwd.show();
        api.getQueryList(token).enqueue(new Callback<ModelQuerry.QueryListResponse>() {
            @Override
            public void onResponse(Call<ModelQuerry.QueryListResponse> call, Response<ModelQuerry.QueryListResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelQuerry.QueryListResponse queryListResponse= response.body();
                   // showToast(queryListResponse.getMessage());
                    if(queryListResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetQueryList.getQueryList(queryListResponse.getData());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelQuerry.QueryListResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void SendFeedback(ModelFeedback.FeedbackRequest request,onResponse onResponse){
        cwd.show();
        api.sendFeedback(token,request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void GetFAQ(onGetFAQS onGetFAQS){
        cwd.show();
        api.getFAQS(token).enqueue(new Callback<ModelFAQ.FAQResponse>() {
            @Override
            public void onResponse(Call<ModelFAQ.FAQResponse> call, Response<ModelFAQ.FAQResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelFAQ.FAQResponse faqResponse= response.body();
                   // showToast(faqResponse.getMessage());
                    if(faqResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetFAQS.getFAQS(faqResponse.getData());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelFAQ.FAQResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void GetPosts(String catId,onGetPost onGetPost){
        cwd.show();
        api.getPostList(token,new ModelPost.postRequest(catId)).enqueue(new Callback<ModelPost.getPostResponse>() {
            @Override
            public void onResponse(Call<ModelPost.getPostResponse> call, Response<ModelPost.getPostResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPost.getPostResponse postResponse= response.body();

                    Log.d("xcv", response.body().getStatus());

                    if(postResponse.getStatus().equals("Success")){
                        onGetPost.onGotPost(postResponse.getData());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("tokenbottam", token);

                        myEdit.apply();
                       // Log.d("xcv",response.body().getData().toString());
                      //  Log.d("xcv",onGetPost.onGotPost(postResponse.getData());


                    }
                    else {
                        onGetPost.onGotPost(new ArrayList<>());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("tokenbottam", token);

                        myEdit.apply();
                       // showToast(postResponse.getMessage());
                        // Log.d("xcv",postResponse.getData().toString());
                    }

                }
                else {
                   // showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPost.getPostResponse> call, Throwable t) {
                cwd.dismiss();
              //  showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }


    public void GetPostsTgn(String catId,onGetPostTgn onGetPostTgn){
        cwd.show();
        api.getPostList("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiIzNyIsIm5hbWUiOiJOaWt1bmogS2FuYW5pICIsImVtYWlsIjoia2FuYW5pbmlrdW5qMzZAZ21haWwuY29tIiwibW9iaWxlIjoiODk4MDYyNDgxNCJ9.OQT63nSQh_oJxEmD102wBKXcZ8hxsdtjLlxXIFGvJgk",new ModelPost.postRequest("62")).enqueue(new Callback<ModelPost.getPostResponse>() {
            @Override
            public void onResponse(Call<ModelPost.getPostResponse> call, Response<ModelPost.getPostResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPost.getPostResponse postResponsetgn= response.body();

                    String jay = postResponsetgn.getStatus();

                    Log.d("fgt", jay);
                    //   Log.d("xcv", response.body().toString());

                    if(postResponsetgn.getStatus().equals("Success")){
                        onGetPostTgn.onGotPostTgn(postResponsetgn.getData());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("tokenbottam", token);

                        myEdit.apply();
                        // Log.d("xcv",response.body().getData().toString());
                        //  Log.d("xcv",onGetPost.onGotPost(postResponse.getData());


                    }
                    else {
                         onGetPostTgn.onGotPostTgn(new ArrayList<>());
                        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("tokenbottam", token);

                        myEdit.apply();
                        // showToast(postResponse.getMessage());
                        // Log.d("xcv",postResponse.getData().toString());
                    }

                }
                else {
                  //  showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPost.getPostResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void GetPaidPosts(onGetPaidPosts onGetPaidPosts){
        cwd.show();
        api.getPaidPostList(token).enqueue(new Callback<ModelPaidPosts.getPostPaidResponse>()
        {
            @Override
            public void onResponse(Call<ModelPaidPosts.getPostPaidResponse> call, Response<ModelPaidPosts.getPostPaidResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPaidPosts.getPostPaidResponse paidPostResponse= response.body();

                   // Log.d("xcv", response.body().toString());

                    if(paidPostResponse.getStatus().equals(Constants.SUCCESSES)){
                     onGetPaidPosts.onGotPaidPosts(paidPostResponse.getData());
                        // Log.d("xcv",response.body().getData().toString());
                        //  Log.d("xcv",onGetPost.onGotPost(postResponse.getData());

                    }
                    else {
                        //onGetPaidPosts.onGotPaidPosts(new ArrayList<>());
                      //  WebServices.onGetPaidPosts.onGotPaidPosts(new ArrayList<>());
                       // showToast(paidPostResponse.getMessage());
                        // Log.d("xcv",postResponse.getData().toString());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPaidPosts.getPostPaidResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }


    public void GetNotification(onGetNotification onGetNotification){
        cwd.show();
        api.getNotifications(token).enqueue(new Callback<ModelNotification.getNotificationResponse>() {
            @Override
            public void onResponse(Call<ModelNotification.getNotificationResponse> call, Response<ModelNotification.getNotificationResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelNotification.getNotificationResponse notificationResponse= response.body();
                    showToast(notificationResponse.getMessage());
                    if(notificationResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetNotification.onGotNotification(notificationResponse.getData());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelNotification.getNotificationResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void Search(ModelPost.searchRequest request,onGetPost onGetPost){
        cwd.show();
        api.search(token,request).enqueue(new Callback<ModelPost.getPostResponse>() {
            @Override
            public void onResponse(Call<ModelPost.getPostResponse> call, Response<ModelPost.getPostResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPost.getPostResponse postResponse= response.body();

                    if(postResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetPost.onGotPost(postResponse.getData());
                    }
                    else {
                        onGetPost.onGotPost(new ArrayList<>());
                        showToast(postResponse.getMessage());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPost.getPostResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void SavePost(String postId,onResponse onResponse){
        cwd.show();
        api.savePost(token,new ModelPost.saveRequest(postId)).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void RemoveSavedPost(String postId,onResponse onResponse){
        cwd.show();
        api.removeSavedPost(token,new ModelPost.saveRequest(postId)).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void GetPostDetails(String postId,onGetPostDetails onGetPostDetails){
        cwd.show();
        api.getPostDetails(token,new ModelPost.saveRequest(postId)).enqueue(new Callback<ModelPost.postDetailsResponse>() {
            @Override
            public void onResponse(Call<ModelPost.postDetailsResponse> call, Response<ModelPost.postDetailsResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPost.postDetailsResponse postDetailsResponse= response.body();

                    if(postDetailsResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetPostDetails.onGotPostDetails(postDetailsResponse);

                        SharedPreferences sharedPreferences = context.getSharedPreferences("MySharedPref", context.MODE_PRIVATE);
                        SharedPreferences.Editor myEdit = sharedPreferences.edit();

                        myEdit.putString("token", token);

                        myEdit.apply();
                    }
                    else {
                        showToast(postDetailsResponse.getMessage());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPost.postDetailsResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void getComments(String postID,onGetComments onGetComments){
        cwd.show();
        api.getComments(token,new ModelComments.commentRequest(postID)).enqueue(new Callback<ModelComments.GetCommentsResponse>() {
            @Override
            public void onResponse(Call<ModelComments.GetCommentsResponse> call, Response<ModelComments.GetCommentsResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelComments.GetCommentsResponse commentResponse= response.body();
                    showToast(commentResponse.getMessage());
                    if(commentResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetComments.onGotComments(commentResponse.getData());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelComments.GetCommentsResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void postComment(ModelComments.commentRequest request,onResponse onResponse){
        cwd.show();
        api.postComment(token,request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCommonResponse commonResponse= response.body();
                    showToast(commonResponse.getMessage());
                    if(commonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }

    public void getSaved(onGetPost onGetPost){
        cwd.show();
        api.getSaved(token).enqueue(new Callback<ModelPost.getPostResponse>() {
            @Override
            public void onResponse(Call<ModelPost.getPostResponse> call, Response<ModelPost.getPostResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelPost.getPostResponse postResponse= response.body();
                   // showToast(postResponse.getMessage());
                    if(postResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetPost.onGotPost(postResponse.getData());
                    }

                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelPost.getPostResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }
    public void getCategories(onGetCategories onGetCategories){
        cwd.show();
        api.getCategories(token).enqueue(new Callback<ModelCategories.categoriesResponse>() {
            @Override
            public void onResponse(Call<ModelCategories.categoriesResponse> call, Response<ModelCategories.categoriesResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelCategories.categoriesResponse categoryResponse= response.body();
                    if(categoryResponse.getStatus().equals(Constants.SUCCESSES)){
                        onGetCategories.onGotCategories(categoryResponse.getData());
                       // Log.d("xcb",categoryResponse.getData().toString());
                    }
                    else {
                        //  showToast(categoryResponse.getMessage());
                        //Log.d("xcb",categoryResponse.getData().toString());
                    }

                   // Log.d("xcb",categoryResponse.getData().toString());
                }
                else {
                  //  showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelCategories.categoriesResponse> call, Throwable t) {
                cwd.dismiss();
               // showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }



    public void getVideos(onGetVideos onGetVideos){
        cwd.show();
        api.getVideos().enqueue(new Callback<ModelGetVideos.videosResponse>() {
            @Override
            public void onResponse(Call<ModelGetVideos.videosResponse> call, Response<ModelGetVideos.videosResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    ModelGetVideos.videosResponse videosResponses= response.body();
                    if(videosResponses.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetVideos.onGotVideos(videosResponses.getData());
                        // Log.d("xcb",categoryResponse.getData().toString());
                    }
                    else {
                        showToast(videosResponses.getMessage());
                      //  Log.d("xcb",videosResponses.getData().toString());
                    }

                    //.d("xcb", ModelGetVideos.videosResponse().toString());
                }
                else {
                    showToast("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<ModelGetVideos.videosResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }



    public void updateUserDeviceToken(String devToken,onResponse onResponse){
        api.updateUserDeviceToken(new ModelLoginRegister.LoginRequest(devToken)).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                if(response.body()!=null){
                    if(response.body().getStatus().equalsIgnoreCase(Constants.SUCCESS)){

                    }
                    else {
                        showToast(response.body().getMessage());
                    }

                }
                else {
                    showToast("Unable to update device token");
                }
                onResponse.onResponse();
            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                showToast("Unable to update device token");
                t.printStackTrace();
                onResponse.onResponse();
            }
        });
    }
    public void verifyNewUser(ModelLoginRegister.verifyNewUserRequest request,onResponse onResponse){
        cwd.show();
        api.verifyNewUserOtp(request).enqueue(new Callback<ModelCommonResponse>() {
            @Override
            public void onResponse(Call<ModelCommonResponse> call, Response<ModelCommonResponse> response) {
                cwd.dismiss();
                if(response.body()!=null){
                    showToast(response.body().getMessage());
                    if(response.body().getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onResponse.onResponse();
                    }

                }
                else {
                    showToast("something went wrong");
                }

            }

            @Override
            public void onFailure(Call<ModelCommonResponse> call, Throwable t) {
                cwd.dismiss();
                showToast("something went wrong");
                t.printStackTrace();
            }
        });
    }

    public interface onGetCategories{
        public void  onGotCategories(List<ModelCategories.Categories> categoryList);
    }

    public interface onGetVideos{
        public void  onGotVideos(List<ModelGetVideos.Video> videoList);
    }

    public interface onGetComments{
        public void onGotComments(List<ModelComments.comment> commentList);
    }
    public interface onGetPostDetails{
        public void onGotPostDetails(ModelPost.postDetailsResponse response);
    }
    public interface onGetNotification{
        public void onGotNotification(List<ModelNotification.Notification> notificationList);
    }

    public interface onGetPost{
        public void onGotPost(List<ModelPost.post> postList);

    }

    public interface onGetPostTgn{
        public void onGotPostTgn(List<ModelPost.post> postListTgn);

    }

    public interface onGetPaidPosts{
        public void  onGotPaidPosts(List<ModelPaidPosts.paidPost> paidPostList);
    }

    public interface onGetFAQS{
        public void getFAQS(List<ModelFAQ.FAQ> faqList);
    }
    public interface onGetQueryList{
        public void getQueryList(List<ModelQuerry.Query> queryList);
    }
    public interface onResponse{
        public void onResponse();
    }

}
