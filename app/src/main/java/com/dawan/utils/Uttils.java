package com.dawan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;


import com.dawan.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Uttils {


    public static Uttils uttils;



    public static Dialog dialoug_Progress;

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    static boolean bl_Internet;





    public Uttils(FragmentActivity activity) {

        uttils = this;


    }


    // For Email Id Is Valid Or Not
    public static boolean isValidEmail(String string) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();

    }




    public static boolean getInternetConnection(Context context) {


        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected() == true) {


            bl_Internet = true;

        } else {


            bl_Internet = false;

        }

        return bl_Internet;

    }


/*
    public static void showProgressDialoug(Context context) {


        Activity activity = (Activity) context;

        dialoug_Progress = new Dialog(context);
        dialoug_Progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialoug_Progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialoug_Progress.getWindow().setGravity(Gravity.CENTER);

        dialoug_Progress.setCanceledOnTouchOutside(true);


        //setting custom layout to dialog_car_variant
        dialoug_Progress.setContentView(R.layout.dialoug_progressbar);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        dialoug_Progress.getWindow().setLayout(width, height);

        if (dialoug_Progress != null && !dialoug_Progress.isShowing()) {
            dialoug_Progress.show();

        }

    }


    public static void dismissDialoug() {


        if (dialoug_Progress != null && dialoug_Progress.isShowing()) {
            dialoug_Progress.dismiss();

        }
    }
*/


    public static void showToast(Context context, String str_Message) {

        Toast toast = Toast.makeText(context, str_Message, Toast.LENGTH_LONG);
        toast.show();

    }



}
