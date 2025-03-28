package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class paymentPaidPostJson {

    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("mobile")
    @Expose
    private String mobile;


    public paymentPaidPostJson(String postId, String mobile) {
        super();
        this.postId = postId;
        this.mobile = mobile;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
