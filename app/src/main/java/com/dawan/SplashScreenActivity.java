package com.dawan;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.dawan.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreenActivity extends AppCompatActivity {
    WebServices webServices;
    String token="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        FirebaseApp.initializeApp(SplashScreenActivity.this);

        webServices=new WebServices(SplashScreenActivity.this);
        ActivityResultLauncher<String> permisiionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean o) {
                subscribeNotification();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permisiionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
        }
        else {
            subscribeNotification();

        }

    }

    private void subscribeNotification() {

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SplashScreenActivity.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                           // Log.w("fmc", "Fetching FCM registration token failed", task.getException());
                            startHandler();
                            Log.e("FCM_ERROR", "Token deletion failed", task.getException());

                        } else{

                            token = task.getResult();
                            //  Log.d("fmc", "" + token);
                            webServices.updateUserDeviceToken(token, new WebServices.onResponse() {
                                @Override
                                public void onResponse() {
                                    FirebaseMessaging.getInstance().subscribeToTopic("commonNotification")
                                            .addOnCompleteListener(task -> {

                                                if (task.isSuccessful()) {
                                                    //Log.d("fmc", "Subscribed to topic successfully");
                                                    startHandler();
                                                } else {
                                                    // Log.e("fmc", "Subscription to topic failed");
                                                    // startHandler();
                                                }

                                            });
                                }
                            });

                        }

                    }
                });

    }

    private void startHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashScreenActivity.this,MainActivity.class);

                if(getIntent()!=null){
                    boolean post=getIntent().getBooleanExtra("postdetail",false);
                    if(post){
                        i.putExtra("postId",getIntent().getStringExtra("postId"));
                    }
                    else {
                        i.putExtra("postId",getIntent().getStringExtra("0"));
                    }
                }
                startActivity(i);
                finish();
            }
        },1500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}