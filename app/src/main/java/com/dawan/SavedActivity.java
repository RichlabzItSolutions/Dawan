package com.dawan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.dawan.adapters.AdapterSubPostWithImage;
import com.dawan.databinding.ActivitySavedBinding;
import com.dawan.models.ModelPost;
import com.dawan.utils.WebServices;

import java.util.List;

public class SavedActivity extends AppCompatActivity {
    ActivitySavedBinding binding;
    WebServices webServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySavedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });
        webServices=new WebServices(SavedActivity.this);
        binding.rvSaved.setLayoutManager(new LinearLayoutManager(this));

        webServices.getSaved(new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                AdapterSubPostWithImage adapter =new AdapterSubPostWithImage(SavedActivity.this,postList,true);
                binding.rvSaved.setAdapter(adapter);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}