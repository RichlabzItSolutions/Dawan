package com.dawan.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dawan.adapters.AdapterComment;
import com.dawan.databinding.DialogCommentBinding;
import com.dawan.models.ModelComments;
import com.dawan.utils.Constants;
import com.dawan.utils.WebServices;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BottomSheetComments extends BottomSheetDialogFragment {
    DialogCommentBinding binding;
    WebServices webServices;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=DialogCommentBinding.inflate(inflater,container,false);
        webServices=new WebServices(getContext());
        
        fetchComment();

        binding.ivSend.setOnClickListener(v->{
            if(!binding.etComment.getText().toString().isEmpty()){
                webServices.postComment(new ModelComments.commentRequest(Constants.PostID,binding.etComment.getText().toString(),""),new WebServices.onResponse() {
                    @Override
                    public void onResponse() {
                        binding.etComment.setText("");
                        fetchComment();
                    }
                });
            }
        });
        
        return binding.getRoot();
    }

    private void fetchComment() {
        webServices.getComments(Constants.PostID, new WebServices.onGetComments() {
            @Override
            public void onGotComments(List<ModelComments.comment> commentList) {
                binding.rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvComments.setAdapter(new AdapterComment(getContext(),commentList));
            }
        });
    }
}
