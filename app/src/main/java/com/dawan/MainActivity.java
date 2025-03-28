package com.dawan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.dawan.databinding.ActivityMainBinding;
import com.dawan.fragments.BottomSheetLogin;
import com.dawan.fragments.FragmentHome;
import com.dawan.fragments.FragmentMyAccount;
import com.dawan.fragments.FragmentNotifications;
import com.dawan.fragments.FragmentSaved;
import com.dawan.fragments.FragmentTrending;
import com.dawan.utils.SharedPref;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        count=0;
        loadFrag(new FragmentHome());
        if(getIntent()!=null){
            if(getIntent().getStringExtra("postId")!=null){
                if(getIntent().getStringExtra("postId").equals("0")){
                    loadFrag(new FragmentNotifications());
                }
                else {
                    Intent i=new Intent(MainActivity.this,PostDetailsActivity.class);
                    i.putExtra("postid",getIntent().getStringExtra("postId"));
                    startActivity(i);
                }
            }
        }
        binding.botNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()){
                    case "Home":
                        count=0;
                        loadFrag(new FragmentHome());
                        return true;
                    case "Trending":
                        loadFrag(new FragmentTrending());
                        return true;
                    case "Saved":
//                        startActivity(new Intent(MainActivity.this, SavedActivity.class));
                        if(new SharedPref(MainActivity.this).getString(SharedPref.token).isEmpty()||new SharedPref(MainActivity.this).getString(SharedPref.token)==null){
                            Toast.makeText(MainActivity.this, "You must login first", Toast.LENGTH_SHORT).show();
                            BottomSheetLogin bottomSheetLogin = new BottomSheetLogin();
                            bottomSheetLogin.show(getSupportFragmentManager(), bottomSheetLogin.getTag());
                            return false;
                        }else {
                            loadFrag(new FragmentSaved());
                            return true;
                        }

                    case "Account":
                        loadFrag(new FragmentMyAccount());
                        return true;

                }
                return false;
            }
        });

    }

    public void loadFrag(Fragment frag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(count>0){
            ft.addToBackStack(null);
        }
        ft.setCustomAnimations(R.anim.slide_in,R.anim.fade_out,R.anim.fade_in,R.anim.slide_out);
        ft.replace(R.id.frame,frag);
        count=count+1;
        ft.commit();

    }

}