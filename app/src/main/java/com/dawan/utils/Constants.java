package com.dawan.utils;

import com.dawan.models.ModelNotification;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Constants {
    //public static final String BASE_URL = "https://richlabz.com/dev/dawan/";
   public static final String BASE_URL = "https://dawan.so/mobileapp/";
    public static final String SUCCESS = "success";
    public static final String SUCCESSES = "Success";
    public static final String Register = "api/register";
    public static final String Login = "api/login";
    public static final String RequestOtp = "api/forgotPassword";
    public static final String ChangePassword = "api/verifyOTP";
    public static final String getQueryList = "api/getQueryList";
    public static final String sendFeedback = "api/sendFeedback";
    public static final String getFaqsList = "api/getFaqsList";
    public static final String getPostList = "api/getPostList";
    public static final String getNotifications = "api/getNotificationList";
    public static final String search = "api/searchNews";
    public static final String savePost = "api/saveStory";
    public static final String removeSavedPost = "api/removeStory";
    public static final String getPostDetails = "api/getPostDetails";
    public static final String getComment = "api/getPostCommentsList";
    public static final String postComment = "api/addPostComment";
    public static final String getSaved = "api/getSavedStoriesList";
    public static final String getCategories = "api/getCategoryList";
    public static final String updateUserDeviceToken = "api/updateUserDeviceToken";
    public static final String verifyNewUserOtp = "api/verifyNewUserOTP";
    public static final String getPaidPosts = "api/getPaidPosts";
    public static final String paymentPaidPost = "api/payment";


    public static final String paymentVideos = "api/video/payment";

    public static final String Utaagan = "api/about_us";
    public static final String getPostVideos =  "api/getVideos";


    public static String SearchStr="";
    public static String PostID="";

    public static ModelNotification.Notification SelectedNotification;


    public static String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        String formattedDate = "";
        try {
            Date date = inputFormat.parse(inputDate);
            formattedDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }


    public static String getTimeAgoString(long timeDifferenceMillis) {
        long seconds = timeDifferenceMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;

        if (seconds < 60) {
            return seconds + " s ago";
        } else if (minutes < 60) {
            return minutes + " m ago";
        } else if (hours < 24) {
            return hours + " h ago";
        } else if (days < 7) {
            return days + " d ago";
        } else if (weeks < 4) {
            return weeks + " w ago";
        } else if (months < 12) {
            return months + " M ago";
        } else {
            return years + " y ago";
        }
    }



}
