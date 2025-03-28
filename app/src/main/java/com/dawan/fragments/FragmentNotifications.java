package com.dawan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dawan.adapters.AdapterNotifications;
import com.dawan.databinding.FragmentNotificationsBinding;
import com.dawan.models.ModelNotification;
import com.dawan.utils.WebServices;

import java.util.List;

public class FragmentNotifications extends Fragment {
    FragmentNotificationsBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentNotificationsBinding.inflate(inflater,container,false);
        binding.rvNoti.setLayoutManager(new LinearLayoutManager(getContext()));
        webServices=new WebServices(getContext());
        webServices.GetNotification(new WebServices.onGetNotification() {
            @Override
            public void onGotNotification(List<ModelNotification.Notification> notificationList) {
                binding.rvNoti.setAdapter(new AdapterNotifications(getContext(),notificationList));
            }
        });

        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}
