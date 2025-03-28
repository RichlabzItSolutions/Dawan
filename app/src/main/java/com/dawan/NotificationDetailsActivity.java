package com.dawan;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.dawan.databinding.ActivityNotificationDetailsBinding;
import com.dawan.utils.Constants;

public class NotificationDetailsActivity extends AppCompatActivity {
ActivityNotificationDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNotificationDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.textView.setOnClickListener(v->{
            getOnBackPressedDispatcher().onBackPressed();
        });
        if(Constants.SelectedNotification!=null){
            binding.tvHeading.setText(Constants.SelectedNotification.getTitle());
            binding.tvDesc.setText(Constants.SelectedNotification.getDescription());
            Glide.with(NotificationDetailsActivity.this)
                    .load(Constants.SelectedNotification.getImage())
                    .error("https://t3.ftcdn.net/jpg/03/34/83/22/360_F_334832255_IMxvzYRygjd20VlSaIAFZrQWjozQH6BQ.jpg")
                    .into(binding.iv1);
            binding.tvDate.setText(Constants.formatDate(Constants.SelectedNotification.getAddedOn()));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SelectedNotification=null;
    }
}