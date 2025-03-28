package com.dawan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dawan.MainActivity;
import com.dawan.R;
import com.dawan.adapters.AdapterSubPostWithImage;
import com.dawan.databinding.ActivitySavedBinding;
import com.dawan.models.ModelPost;
import com.dawan.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FragmentSaved extends Fragment {
    ActivitySavedBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=ActivitySavedBinding.inflate(inflater,container,false);
        BottomNavigationView botnav=((MainActivity)getContext()).findViewById(R.id.bot_nav);
        botnav.getMenu().getItem(2).setChecked(true);
        binding.textView.setOnClickListener(v->{
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        webServices=new WebServices(getContext());
        binding.rvSaved.setLayoutManager(new LinearLayoutManager(getContext()));

        webServices.getSaved(new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                AdapterSubPostWithImage adapter =new AdapterSubPostWithImage(getContext(),postList,true);
                binding.rvSaved.setAdapter(adapter);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            webServices.dismissDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}