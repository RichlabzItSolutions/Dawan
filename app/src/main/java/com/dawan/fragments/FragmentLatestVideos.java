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
import com.dawan.databinding.FragmentLatestVideoBinding;

public class FragmentLatestVideos extends Fragment {

    FragmentLatestVideoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentLatestVideoBinding.inflate(inflater,container,false);
        binding.rvRecentVideos.setLayoutManager(new LinearLayoutManager(getContext()));
//        binding.rvRecentVideos.setAdapter(new AdapterNotifications(getContext()));
        return binding.getRoot();
    }
}
