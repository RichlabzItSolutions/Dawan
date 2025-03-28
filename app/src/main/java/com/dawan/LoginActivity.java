package com.dawan;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dawan.databinding.ActivityLoginBinding;
import com.dawan.models.ModelLoginRegister;
import com.dawan.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    WebServices webServices;

    String fcmToken = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        FirebaseApp.initializeApp(LoginActivity.this);

       /* FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                // Get new FCM registration token
                String token = task.getResult();

                // Log and toast
                Log.d(TAG, "FCM Registration Token: " + token);
            }
        });*/

       /* FirebaseMessaging.getInstance().getToken()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("FirebaseApp Token error: ", e.getMessage().toString());
                    }
                }).addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.d("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        fcmToken = task.getResult();
                        saveToken("fcm_token", fcmToken);
                        Log.d("FirebaseApp Token: ", fcmToken);
                    }
                });*/

      /*  if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(LoginActivity.this);
        }*/

        webServices = new WebServices(LoginActivity.this);
        binding.tvCreateone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
                finish();
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                  //  Log.w("fmc", "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                String token = task.getResult();
                               // Log.d("fmc", "" + token);
                                ModelLoginRegister.LoginRequest request = new ModelLoginRegister.LoginRequest(email, password, token);
                                webServices.Login(request, new WebServices.onResponse() {
                                    @Override
                                    public void onResponse() {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finishAffinity();
                                    }
                                });
                            }
                        });
            }
        });
        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            finish();
        });
        binding.ivShowPassword.setOnClickListener(v->{
            togglePasswordVisibility();
        });


    }
    private void togglePasswordVisibility() {
        if (binding.etPassword.getTransformationMethod() == null) {
            binding.etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_visibility_off_24, null));
        } else {
            binding.etPassword.setTransformationMethod(null);
            binding.ivShowPassword.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.baseline_visibility_24, null));
        }

        // Move cursor to the end of the text
        binding.etPassword.setSelection(binding.etPassword.getText().length());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}