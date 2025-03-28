package com.dawan.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dawan.MainActivity;
import com.dawan.R;
import com.dawan.adapters.AdapterCategories;
import com.dawan.adapters.AdapterNews;
import com.dawan.adapters.AdapterNewsTrending;
import com.dawan.adapters.AdapterNewsWorld;
import com.dawan.adapters.AdapterPaidPosts;
import com.dawan.adapters.AdapterPosts;
import com.dawan.adapters.AdapterPostsTgan;
import com.dawan.adapters.AdapterVideosPost;
import com.dawan.databinding.FragmenthomeBinding;
import com.dawan.models.GetPostCategoryJson;
import com.dawan.models.GetPostUtaaganResponse;
import com.dawan.models.ModelCategories;
import com.dawan.models.ModelGetVideos;
import com.dawan.models.ModelNews;
import com.dawan.models.ModelPaidPosts;
import com.dawan.models.ModelPost;
import com.dawan.models.VideosPaymentJson;
import com.dawan.models.paymentPaidPostResponse;
import com.dawan.utils.API;
import com.dawan.utils.Constants;
import com.dawan.utils.RetrofitClient;
import com.dawan.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {
    FragmenthomeBinding binding;
    List<TextView> tablist;
    WebServices webServices;
    AdapterPosts adapterPosts;

    API api;
    AdapterPaidPosts adapterpaidPosts;
     AdapterPostsTgan adapterPostTagn;
    AdapterVideosPost adapterVideosPost;

    List<ModelPaidPosts.paidPost> paidPostList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmenthomeBinding.inflate(inflater,container,false);
        webServices=new WebServices(getContext());
        BottomNavigationView botnav=((MainActivity)getContext()).findViewById(R.id.bot_nav);
        botnav.getMenu().getItem(0).setChecked(true);
        setLatestNews("23");
        tablist=new ArrayList<>();
        tablist.add(binding.tab0);
        tablist.add(binding.tab1);
        tablist.add(binding.tab2);
        tablist.add(binding.tab3);
        for(TextView selectedtab:tablist){
            selectedtab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(selectedtab.getText().toString().equalsIgnoreCase("Latest News")){
                       // setLatestNews("");
                        updateTab(selectedtab);
                    } else if (selectedtab.getText().toString().equalsIgnoreCase("World News")) {
                        //setWordNews();
                        Toast.makeText(getContext(), "Coming Soon..", Toast.LENGTH_SHORT).show();
                    }
                    else if (selectedtab.getText().toString().equalsIgnoreCase("Trending")){
                        //setTrendingNews();
                        Toast.makeText(getContext(), "Coming Soon..", Toast.LENGTH_SHORT).show();

                    }
                    else if (selectedtab.getText().toString().equalsIgnoreCase("Business")){
                        Toast.makeText(getContext(), "Coming Soon..", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            webServices = new WebServices(getActivity());

            api = RetrofitClient.getRetrofitInstance().create(API.class);

            webServices.getCategories(new WebServices.onGetCategories() {
                @Override
                public void onGotCategories(List<ModelCategories.Categories> categoryList) {
                    LinearLayoutManager lm=new LinearLayoutManager(getContext());
                    lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                    //Log.d("yuip", String.valueOf(lm));
                    binding.rvTabs.setLayoutManager(lm);
                    ModelCategories.Categories mc=new ModelCategories.Categories();
                    ModelCategories.Categories mc1=new ModelCategories.Categories();
                    mc.setName("Latest News");
                    mc.setId("23");
                    categoryList.add(0,mc);


                    mc1.setName("U taagan");
                    mc1.setId("62");
                    categoryList.add(4,mc1);

                   // setLatestNews("");
                    binding.rvTabs.setAdapter(new AdapterCategories(getContext(), categoryList, new AdapterCategories.onTabClicked() {
                        @Override
                        public void TabClicked(String id) {

                            Log.d("SelectedTab", "Tab ID: " + id);

                            Map<String, Runnable> actions = new HashMap<>();

                            actions.put("263", () -> webServices.GetPaidPosts(new WebServices.onGetPaidPosts() {
                                @Override
                                public void onGotPaidPosts(List<ModelPaidPosts.paidPost> paidPostList) {
                                    if (!paidPostList.isEmpty()) {
                                        ModelPaidPosts.paidPost post = paidPostList.get(0);
                                        Log.d("nik", post.getPaidAmount());
                                        binding.rvNews.setVisibility(View.VISIBLE);
                                        adapterpaidPosts = new AdapterPaidPosts(post, paidPostList, getContext(), requireActivity().getSupportFragmentManager());
                                        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                                        binding.rvNews.setAdapter(adapterpaidPosts);
                                        binding.NODataFound.setVisibility(View.GONE);

                                    } else {
                                        //binding.rvNews.setAdapter(null);
                                        binding.rvNews.setAdapter(adapterpaidPosts);
                                    }
                                }
                            }));

                            actions.put("23", () -> setLatestNews("23"));
                            actions.put("59", () -> setLatestNews("59"));
                            actions.put("61", () -> setLatestNews("61"));
                            actions.put("62", () -> setLatestNews("62"));
// More mappings...

                            Runnable action = actions.get(id);
                            if (action != null) {
                                action.run();
                            }




                         /*   if (id.equals("263")) {

                                webServices.GetPaidPosts(new WebServices.onGetPaidPosts() {
                                    @Override
                                    public void onGotPaidPosts(List<ModelPaidPosts.paidPost> paidPostList) {
                                        if (!paidPostList.isEmpty()) {
                                            ModelPaidPosts.paidPost post = paidPostList.get(0);
                                            Log.d("nik", post.getPaidAmount());
                                            adapterpaidPosts = new AdapterPaidPosts(post, paidPostList, getContext(), requireActivity().getSupportFragmentManager());
                                            binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                                            binding.rvNews.setAdapter(adapterpaidPosts);

                                        } else {
                                            //binding.rvNews.setAdapter(null);
                                            binding.rvNews.setAdapter(adapterpaidPosts);
                                        }
                                    }
                                });

                            }

                            if (id.equals("23")) {
                                setLatestNews("23");
                            }
                            if (id.equals("59")) {
                                setLatestNews("59");
                            }
                            if (id.equals("61")) {
                                setLatestNews("61");
                            }
                            if (id.equals("62")) {
                                setLatestNews("62");
                            }
*/
                        }

                    }));

                   /* binding.rvTabs.setAdapter(new AdapterCategories(getContext(), categoryList, new AdapterCategories.onTabClicked() {
                        @Override
                        public void TabClicked(String id) {
                            setLatestNews(id);
                        }
                    }));*/
                }
            });
        }

        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).loadFrag(new FragmentNotifications());
            }
        });
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
    private void updateTab(TextView selectedtab) {
        for(TextView textView:tablist){
            textView.setBackground(ResourcesCompat.getDrawable(getResources(),android.R.color.transparent,null));
            textView.setTextColor(Color.BLACK);
        }
        selectedtab.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.custom_tab_indicator,null));
        selectedtab.setTextColor(Color.WHITE);

    }
    public void setLatestNews(String catId){

        Log.d("vbnjk", catId);


        if (catId.equals("62")){

            api.getUtaagan("").enqueue(new Callback<GetPostUtaaganResponse>() {
                @Override
                public void onResponse(Call<GetPostUtaaganResponse> call, Response<GetPostUtaaganResponse> response) {
                    // cwd.dismiss();
                    if(response.body()!=null){
                        GetPostUtaaganResponse postDetailsResponse= response.body();

                        Log.d("kol", response.body().getMessage());

                     String  paymentStatus =  postDetailsResponse.getMessage();

                   if(paymentStatus.equals("Data found"))  {

                       binding.rvNews.setVisibility(View.GONE);
                       binding.NODataFound.setVisibility(View.VISIBLE);
                       binding.NODataFound.setText(postDetailsResponse.getData());

                   }
                   else{

                       binding.rvNews.setVisibility(View.VISIBLE);
                       binding.NODataFound.setVisibility(View.GONE);

                   }


                     Log.d("noplm", paymentStatus);



                        }
                        else{


                        }
                }

                @Override
                public void onFailure(Call<GetPostUtaaganResponse> call, Throwable t) {
                    //cwd.dismiss();
                    // showToast("something went wrong");
                    t.printStackTrace();
                }
            });
        }



        if (catId.equals("23")){


            webServices.GetPosts("",new WebServices.onGetPost() {
                @Override
                public void onGotPost(List<ModelPost.post> postList) {
                    if(!postList.isEmpty()) {
                        ModelPost.post post = postList.get(0);
                        // postList.remove(0);
//                postList.get(1).setFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
                        Log.d("bnmv", post.getCategoryName());

                        binding.NODataFound.setVisibility(View.GONE);
                        binding.rvNews.setVisibility(View.VISIBLE);
                        adapterPosts = new AdapterPosts(post, postList, getContext(), requireActivity().getSupportFragmentManager());
                        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvNews.setAdapter(adapterPosts);

                    }
                    else {
                         binding.rvNews.setVisibility(View.GONE);

                        // binding.rvNews.setAdapter(null);
                        // binding.rvNews.setAdapter(adapterPosts);
                    }
                }
            });

        }


        webServices.GetPosts(catId,new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                if(!postList.isEmpty()) {
                    ModelPost.post post = postList.get(0);
                    // postList.remove(0);
//                postList.get(1).setFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");
                    Log.d("bnmv", post.getCategoryName());

                    binding.NODataFound.setVisibility(View.GONE);
                    binding.rvNews.setVisibility(View.VISIBLE);
                    adapterPosts = new AdapterPosts(post, postList, getContext(), requireActivity().getSupportFragmentManager());
                    binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvNews.setAdapter(adapterPosts);

                }
                else {
                   // binding.rvNews.setVisibility(View.GONE);

                    // binding.rvNews.setAdapter(null);
                    // binding.rvNews.setAdapter(adapterPosts);
                }
            }
        });





/*

        webServices.GetPosts(catId,new WebServices.onGetPost() {
            @Override
            public void onGotPost(List<ModelPost.post> postList) {
                if(!postList.isEmpty()) {
                    ModelPost.post post = postList.get(0);
                   // postList.remove(0);
//                postList.get(1).setFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");

                  Log.d("bnmv", post.getCategoryName());

                    adapterPosts = new AdapterPosts(post, postList, getContext(), requireActivity().getSupportFragmentManager());
                    binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvNews.setAdapter(adapterPosts);

                }
                else {
                   // binding.rvNews.setAdapter(null);
                   // binding.rvNews.setAdapter(adapterPosts);
                }
            }
        });
*/
    }


   /* public void setVideo(){
        webServices.getVideos(new WebServices.onGetVideos(){
            @Override
            public void onGotVideos(List<ModelGetVideos.Video> videoList) {
                if(!videoList.isEmpty()) {
                    ModelGetVideos.Video post = videoList.get(0);
                    // postList.remove(0);
                //postList.get(1).setFile("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4");

                     adapterVideosPost = new AdapterVideosPost(post, videoList, getContext(), requireActivity().getSupportFragmentManager());
                    binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvNews.setAdapter(adapterVideosPost);

                }
                else {
                    //binding.rvNews.setAdapter(null);
                    binding.rvNews.setAdapter(adapterVideosPost);
                }
            }
        });
    }*/

  /*  public void setWordNews(){
        List<ModelNews> list2=new ArrayList<>();
        List<ModelNews.subnews> subnews2=new ArrayList<>();
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        list2.add(new ModelNews(false,false,subnews2));
        AdapterNewsWorld adapterNews2=new AdapterNewsWorld(list2,getContext());
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(adapterNews2);

    }
    public void setTrendingNews(){
        List<ModelNews> list2=new ArrayList<>();
        List<ModelNews.subnews> subnews2=new ArrayList<>();
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        subnews2.add(new ModelNews.subnews());
        list2.add(new ModelNews(false,false,subnews2));
        AdapterNewsTrending adapterNews2=new AdapterNewsTrending(list2,getContext());
        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvNews.setAdapter(adapterNews2);

    }*/

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
