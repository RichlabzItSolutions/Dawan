package com.dawan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.widget.Toast;

import com.dawan.databinding.ActivityForgotPasswordBinding;
import com.dawan.utils.Uttils;


public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvSendOtp.setOnClickListener(v -> {
            String mob=binding.etMob.getText().toString();
            /*if(!mob.isEmpty()&&) {
                Intent i = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
                i.putExtra("mob", mob);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(ForgotPasswordActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                binding.etMob.requestFocus();
            }*/


            if (TextUtils.isEmpty(mob)) {

                Toast.makeText(ForgotPasswordActivity.this, "Please Enter Your Email", Toast.LENGTH_SHORT).show();

            }
            else if (!Uttils.isValidEmail(mob)) {

                Toast.makeText(ForgotPasswordActivity.this, "Please Enter Your Valid Email Address", Toast.LENGTH_SHORT).show();

            }

            else {

                Intent i = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
                i.putExtra("mob", mob);
                startActivity(i);
                finish();

            }

        });

    }
}