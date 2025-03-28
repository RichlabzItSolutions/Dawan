package com.dawan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dawan.adapters.AdapterFAQ;
import com.dawan.databinding.FragmentFaqBinding;
import com.dawan.models.ModelFAQ;
import com.dawan.utils.WebServices;

import java.util.List;

public class FragmentFAQ extends Fragment {

    FragmentFaqBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentFaqBinding.inflate(inflater,container,false);
        binding.rvFaq.setLayoutManager(new LinearLayoutManager(getContext()));
        webServices=new WebServices(getContext());
        webServices.GetFAQ(new WebServices.onGetFAQS() {
            @Override
            public void getFAQS(List<ModelFAQ.FAQ> faqList) {
                binding.rvFaq.setAdapter(new AdapterFAQ(getContext(),faqList));
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
