package com.dawan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dawan.MainActivity;
import com.dawan.databinding.FragmentSendFeedbackBinding;
import com.dawan.models.ModelFeedback;
import com.dawan.models.ModelQuerry;
import com.dawan.models.ModelSpinner;
import com.dawan.utils.CustomSpinnerAdapter;
import com.dawan.utils.WebServices;

import java.util.ArrayList;
import java.util.List;

public class FragmentSendFeedback extends Fragment {
    FragmentSendFeedbackBinding binding;
    WebServices webServices;
    String SelectedQueryId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding=FragmentSendFeedbackBinding.inflate(inflater,container,false);
        webServices=new WebServices(getContext());
        setQueryList();
        binding.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });

        binding.tvSubmit.setOnClickListener(v->{
            if(SelectedQueryId.isEmpty()){
                Toast.makeText(getContext(), "Select Query", Toast.LENGTH_SHORT).show();
            }
            else if(binding.etMessage.getText().toString().isEmpty()){
                Toast.makeText(getContext(), "Enter Message", Toast.LENGTH_SHORT).show();
            }else {
                ModelFeedback.FeedbackRequest request=new ModelFeedback.FeedbackRequest(SelectedQueryId,binding.etMessage.getText().toString());
                webServices.SendFeedback(request, new WebServices.onResponse() {
                    @Override
                    public void onResponse() {
                        requireActivity().getOnBackPressedDispatcher().onBackPressed();
                    }
                });
            }

        });
        return binding.getRoot();

    }

    private void setQueryList() {
        webServices.GetQueryList(new WebServices.onGetQueryList() {
            @Override
            public void getQueryList(List<ModelQuerry.Query> queryList) {
                List<ModelSpinner> spinnerList = new ArrayList<>();
                spinnerList.add(new ModelSpinner("Select Query", "0"));
                for (ModelQuerry.Query s : queryList) {
                    spinnerList.add(new ModelSpinner(s.getQuery(), s.getId()));
                }
                CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getContext(), spinnerList);
                binding.spQuery.setAdapter(adapter);
//                for (int i = 0; i < spinnerList.size(); i++) {
//                    if (spinnerList.get(i).getId().equals(prequeryid)) {
//                        binding.spQuery.setSelection(i);
//                        break;
//                    }
//                }
                binding.spQuery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ModelSpinner selectedModel = (ModelSpinner) parent.getItemAtPosition(position);
                        if(!selectedModel.getId().equals("0")) {
                            SelectedQueryId = selectedModel.getId();
                        }else {
                            SelectedQueryId="";
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

}
