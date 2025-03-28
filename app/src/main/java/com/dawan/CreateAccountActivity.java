package com.dawan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dawan.databinding.ActivityCreateAccountBinding;
import com.dawan.databinding.ActivityOtpVerificationBinding;
import com.dawan.models.ModelLoginRegister;
import com.dawan.utils.WebServices;

import java.util.Locale;
import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity {
    ActivityCreateAccountBinding binding;
    ActivityOtpVerificationBinding dbinding;
    WebServices webServices;
    private CountDownTimer otpCountDownTimer;
    String email,password,mob,name;
    Dialog dVerify;
    String defaultMobText="we sent a verification code to ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        webServices=new WebServices(CreateAccountActivity.this);
        binding.ivShowPassword.setOnClickListener((v->{
            togglePasswordVisibility();
        }));
        binding.tvTerms.setOnClickListener(v->{
            startActivity(new Intent(CreateAccountActivity.this, TermsAndPrivacyActivity.class).putExtra("tORp","t"));
        });
        binding.tvPrivacy.setOnClickListener(v->{
            startActivity(new Intent(CreateAccountActivity.this, TermsAndPrivacyActivity.class).putExtra("tORp","p"));
        });

        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=binding.etUsername.getText().toString();
                password=binding.etPassword.getText().toString();
                mob=binding.etMob.getText().toString();
                name=binding.etName.getText().toString();
                if(email.isEmpty()||password.isEmpty()||mob.isEmpty()||name.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendOtp(name,email,mob,password);
                }

            }
        });
        binding.tvLogin.setOnClickListener(v->{
            startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            finish();
        });

    }

    private void verifyOtp() {
        dVerify=new Dialog(CreateAccountActivity.this);
        dbinding= ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        dVerify.setContentView(dbinding.getRoot());
        Objects.requireNonNull(dVerify.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dVerify.show();
        dbinding.tvMob.setText(defaultMobText+email);
        dbinding.tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webServices.verifyNewUser(new ModelLoginRegister.verifyNewUserRequest(email, dbinding.etOtp.getText().toString()), new WebServices.onResponse() {
                    @Override
                    public void onResponse() {
                        startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
                        finish();
                    }
                });
            }
        });
        dbinding.tvResend.setOnClickListener(v->{
            sendOtp(name,email,mob,password);
        });


    }

    private void sendOtp(String name, String email, String mob, String password) {
        ModelLoginRegister.RegisterRequest request=new ModelLoginRegister.RegisterRequest(name,email,mob,password);
        webServices.Register(request, new WebServices.onResponse() {
            @Override
            public void onResponse() {
                verifyOtp();
                startOtpTimer(60000);
            }
        });
    }

    private void startOtpTimer(long durationMillis) {
        dbinding.tvResend.setEnabled(false);
        otpCountDownTimer = new CountDownTimer(durationMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                String timeLeftFormatted = formatTime(millisUntilFinished);
                dbinding.tvTimer.setText(timeLeftFormatted);
            }

            public void onFinish() {
                dbinding.tvTimer.setText("00:00");
                dbinding.tvResend.setEnabled(true);
            }
        }.start();
    }

    private String formatTime(long millis) {
        // Format the time in minutes:seconds
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
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
        if(dVerify!=null&&dVerify.isShowing()) {
            dVerify.dismiss();
        }
    }


}