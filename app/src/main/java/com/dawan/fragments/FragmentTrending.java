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
import com.dawan.R;
import com.dawan.adapters.AdapterPosts;
import com.dawan.adapters.AdapterSubPostWithImage;
import com.dawan.databinding.FragmentSearchBinding;
import com.dawan.models.ModelPost;
import com.dawan.utils.Constants;
import com.dawan.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FragmentTrending extends Fragment {
    FragmentSearchBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentSearchBinding.inflate(inflater,container,false);
        webServices=new WebServices(getContext());
        BottomNavigationView botnav=((MainActivity)getContext()).findViewById(R.id.bot_nav);
        botnav.getMenu().getItem(1).setChecked(true);
        binding.rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.textView.setOnClickListener(v->{
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.textView.setText("Trending");
        loadData();
        binding.ivSearch.setOnClickListener(v->{
            if(!binding.etSearch.getText().toString().isEmpty()) {
                Constants.SearchStr=binding.etSearch.getText().toString();
                binding.etSearch.setText("");
                binding.etSearch.clearFocus();
                ((MainActivity) getContext()).loadFrag(new FragmentSearch());

            }
        });

        return binding.getRoot();
    }
    public void loadData(){
        webServices.GetPosts("",new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                ModelPost.post post=postList.get(0);
                postList.remove(0);
//                postList.get(1).setFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
                AdapterPosts adapterPosts=new AdapterPosts(post,postList,getContext(),requireActivity().getSupportFragmentManager());
                binding.rvSearch.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvSearch.setAdapter(adapterPosts);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constants.SearchStr="";
        //webServices.dismissDialog();
    }
}
