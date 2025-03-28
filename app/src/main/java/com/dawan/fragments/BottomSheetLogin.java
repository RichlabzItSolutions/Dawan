package com.dawan.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.dawan.CreateAccountActivity;
import com.dawan.ForgotPasswordActivity;
import com.dawan.MainActivity;
import com.dawan.R;
import com.dawan.SplashScreenActivity;
import com.dawan.databinding.ActivityLoginBinding;
import com.dawan.models.ModelLoginRegister;
import com.dawan.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

public class BottomSheetLogin extends BottomSheetDialogFragment {

    private ActivityLoginBinding binding;
    private WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityLoginBinding.inflate(inflater, container, false);
        FirebaseApp.initializeApp(getActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webServices = new WebServices(requireContext());

        binding.tvCreateone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireContext(), CreateAccountActivity.class));
                dismiss();
            }
        });

        binding.tvLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString();
            String password = binding.etPassword.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Invalid Email or Password", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "Fetching FCM registration token failed", Toast.LENGTH_SHORT).show();
                                   // Log.w("fmc", "Fetching FCM registration token failed", task.getException());
                                    return;
                                }
                                String token = task.getResult();
                               // Log.d("fmc", "" + token);
                                ModelLoginRegister.LoginRequest request = new ModelLoginRegister.LoginRequest(email, password, token);
                                webServices.Login(request, new WebServices.onResponse() {
                                    @Override
                                    public void onResponse() {
                                        startActivity(new Intent(requireContext(), MainActivity.class));
                                        requireActivity().finishAffinity();
                                        dismiss();
                                    }
                                });
                            }
                        });
            }
        });

        binding.tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), ForgotPasswordActivity.class));
//            requireActivity().finish();
            dismiss();
        });

        binding.ivShowPassword.setOnClickListener(v -> {
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
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
