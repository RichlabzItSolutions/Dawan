package com.dawan.utils;
import com.dawan.models.GetPostCategoryJson;
import com.dawan.models.GetPostUtaaganResponse;
import com.dawan.models.ModelCategories;
import com.dawan.models.ModelChangePassword;
import com.dawan.models.ModelComments;
import com.dawan.models.ModelCommonResponse;
import com.dawan.models.ModelFAQ;
import com.dawan.models.ModelFeedback;
import com.dawan.models.ModelLoginRegister;
import com.dawan.models.ModelNotification;
import com.dawan.models.ModelPaidPosts;
import com.dawan.models.ModelPost;
import com.dawan.models.ModelQuerry;
import com.dawan.models.ModelGetVideos;
import com.dawan.models.VideosPaymentJson;
import com.dawan.models.paymentPaidPostJson;
import com.dawan.models.paymentPaidPostResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface API {

    @POST(Constants.Register)
    Call<ModelCommonResponse> register(@Body ModelLoginRegister.RegisterRequest request);
    @POST(Constants.Login)
    Call<ModelLoginRegister.LoginResponse> login(@Body ModelLoginRegister.LoginRequest request);
    @POST(Constants.RequestOtp)
    Call<ModelCommonResponse> requestOtp(@Body ModelChangePassword.ChangePasswordRequest request);
    @POST(Constants.ChangePassword)
    Call<ModelCommonResponse> changePassword(@Body ModelChangePassword.ChangePasswordRequest request);
    @POST(Constants.getQueryList)
    Call<ModelQuerry.QueryListResponse> getQueryList(@Header("Authorization") String Auth);
    @POST(Constants.sendFeedback)
    Call<ModelCommonResponse> sendFeedback(@Header("Authorization") String Auth,@Body ModelFeedback.FeedbackRequest request);
    @POST(Constants.getFaqsList)
    Call<ModelFAQ.FAQResponse> getFAQS(@Header("Authorization") String Auth);
    @POST(Constants.getPostList)
    Call<ModelPost.getPostResponse> getPostList(@Header("Authorization") String Auth,@Body ModelPost.postRequest request);

    @POST(Constants.getPostList)
    Call<ModelPost.getPostResponse> getPostListtgn(@Header("Authorization") String Auth,@Body ModelPost.postRequest request);
    @POST(Constants.getPaidPosts)
    Call<ModelPaidPosts.getPostPaidResponse> getPaidPostList(@Header("Authorization") String Auth);
    @POST(Constants.getNotifications)
    Call<ModelNotification.getNotificationResponse> getNotifications(@Header("Authorization") String Auth);

    @POST(Constants.search)
    Call<ModelPost.getPostResponse> search(@Header("Authorization") String Auth,@Body ModelPost.searchRequest request);

    @POST(Constants.savePost)
    Call<ModelCommonResponse> savePost(@Header("Authorization") String Auth,@Body ModelPost.saveRequest request);
    @POST(Constants.removeSavedPost)
    Call<ModelCommonResponse> removeSavedPost(@Header("Authorization") String Auth,@Body ModelPost.saveRequest request);
    @POST(Constants.getPostDetails)
    Call<ModelPost.postDetailsResponse> getPostDetails(@Header("Authorization") String Auth,@Body ModelPost.saveRequest request);
    @POST(Constants.getComment)
    Call<ModelComments.GetCommentsResponse> getComments(@Header("Authorization") String Auth, @Body ModelComments.commentRequest request);
    @POST(Constants.postComment)
    Call<ModelCommonResponse> postComment(@Header("Authorization") String Auth, @Body ModelComments.commentRequest request);
    @POST(Constants.getSaved)
    Call<ModelPost.getPostResponse> getSaved(@Header("Authorization") String Auth);
    @POST(Constants.getCategories)
    Call<ModelCategories.categoriesResponse> getCategories(@Header("Authorization") String Auth);
    @POST(Constants.updateUserDeviceToken)
    Call<ModelCommonResponse> updateUserDeviceToken(@Body ModelLoginRegister.LoginRequest request);
    @POST(Constants.verifyNewUserOtp)
    Call<ModelCommonResponse> verifyNewUserOtp(@Body ModelLoginRegister.verifyNewUserRequest request);
    @POST(Constants.paymentPaidPost)
    Call<paymentPaidPostResponse> paymentPaidPost(@Header("Authorization") String Auth, @Body paymentPaidPostJson request);
    @POST(Constants.paymentVideos)
    Call<paymentPaidPostResponse> paymentVideos(@Header("Authorization") String Auth, @Body VideosPaymentJson request);


    @POST(Constants.getPostList)
    Call<paymentPaidPostResponse> getPostTgn(@Header("Authorization") String Auth, @Body GetPostCategoryJson request);
    @POST(Constants.Utaagan)
    Call<GetPostUtaaganResponse> getUtaagan(@Header("Authorization") String Auth);

  /*  @POST(Constants.getPostList)
    Call<ModelPost.getPostResponse> getPostTgn(@Header("Authorization") String Auth, @Body ModelPost.postRequest request);*/
    @POST(Constants.getPostVideos)
    Call<ModelGetVideos.videosResponse> getVideos();


}