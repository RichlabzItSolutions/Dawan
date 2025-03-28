package com.dawan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dawan.MainActivity;
import com.dawan.adapters.AdapterSubPostWithImage;
import com.dawan.databinding.FragmentSearchBinding;
import com.dawan.models.ModelPost;
import com.dawan.utils.Constants;
import com.dawan.utils.WebServices;

import java.util.List;

public class FragmentSearch extends Fragment {
    FragmentSearchBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSearchBinding.inflate(inflater,container,false);
        webServices=new WebServices(getContext());
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.textView.setOnClickListener(v->{
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        loadData();
        binding.ivSearch.setOnClickListener(v->{
            if(!binding.etSearch.getText().toString().isEmpty()) {
                binding.etSearch.clearFocus();
                Constants.SearchStr=binding.etSearch.getText().toString();
                loadData();
            }
        });

        return binding.getRoot();
    }
    public void loadData(){
        binding.textView.setText("Search for \"" + Constants.SearchStr+"\"");
        binding.etSearch.setText(Constants.SearchStr);
        webServices.Search(new ModelPost.searchRequest(Constants.SearchStr), new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                AdapterSubPostWithImage adapter =new AdapterSubPostWithImage(getContext(),postList,false);
                binding.rvSearch.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.SearchStr="";
        webServices.dismissDialog();
    }
}
