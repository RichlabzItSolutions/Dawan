package com.dawan.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dawan.CreateAccountActivity;
import com.dawan.LoginActivity;
import com.dawan.MainActivity;
import com.dawan.R;
import com.dawan.SavedActivity;
import com.dawan.TermsAndPrivacyActivity;
import com.dawan.databinding.FragmentMyAccountBinding;
import com.dawan.utils.SharedPref;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentMyAccount extends Fragment {
    FragmentMyAccountBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentMyAccountBinding.inflate(inflater,container,false);


        BottomNavigationView botnav=((MainActivity)getContext()).findViewById(R.id.bot_nav);
        botnav.getMenu().getItem(3).setChecked(true);
        checkLogin();
        binding.llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        binding.llCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CreateAccountActivity.class));
            }
        });
        binding.llSavedStories.setOnClickListener(v->{
            if(!new SharedPref(getContext()).getString(SharedPref.token).isEmpty()&&new SharedPref(getContext()).getString(SharedPref.token)!=null) {
                startActivity(new Intent(getContext(), SavedActivity.class));
            }
            else {
                Toast.makeText(getContext(), "You must login first", Toast.LENGTH_SHORT).show();
                BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                bottomSheetLogin.show(requireActivity().getSupportFragmentManager(), bottomSheetLogin.getTag());
            }
        });

        binding.llSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!new SharedPref(getContext()).getString(SharedPref.token).isEmpty()&&new SharedPref(getContext()).getString(SharedPref.token)!=null) {
                    ((MainActivity) getActivity()).loadFrag(new FragmentSendFeedback());
                }
                else {
                    Toast.makeText(getContext(), "You must login first", Toast.LENGTH_SHORT).show();
                    BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                    bottomSheetLogin.show(requireActivity().getSupportFragmentManager(), bottomSheetLogin.getTag());
                }
            }
        });
        binding.llFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!new SharedPref(getContext()).getString(SharedPref.token).isEmpty()||new SharedPref(getContext()).getString(SharedPref.token)!=null) {
                    ((MainActivity)getActivity()).loadFrag(new FragmentFAQ());
//                }
//                else {
//                    Toast.makeText(getContext(), "You must login to continue", Toast.LENGTH_SHORT).show();
//                }

            }
        });
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        binding.llLogout.setOnClickListener(v->{
            new SharedPref(getContext()).clearAll();
            Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
            requireActivity().recreate();
        });

        binding.llNotification.setOnClickListener(v->{
            String packageName = getContext().getPackageName();
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        binding.llTerms.setOnClickListener(v->{
            startActivity(new Intent(getContext(), TermsAndPrivacyActivity.class).putExtra("tORp","t"));
        });
        binding.llPrivacy.setOnClickListener(v->{
            startActivity(new Intent(getContext(), TermsAndPrivacyActivity.class).putExtra("tORp","p"));
        });

        return binding.getRoot();
    }

    private void checkLogin() {
        SharedPref sharedPref =new SharedPref(getContext());
        if(sharedPref.getString(SharedPref.token).isEmpty()||sharedPref.getString(SharedPref.token)==null){
            binding.llLogin.setVisibility(View.VISIBLE);
            binding.llCreateAcc.setVisibility(View.VISIBLE);
            binding.llLogout.setVisibility(View.GONE);
            binding.llSavedStories.setVisibility(View.GONE);
        }
        else {
            binding.llLogin.setVisibility(View.GONE);
            binding.llCreateAcc.setVisibility(View.GONE);
            binding.llLogout.setVisibility(View.VISIBLE);
            binding.llSavedStories.setVisibility(View.VISIBLE);
        }
    }
}
