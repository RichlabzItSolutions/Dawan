package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideosPaymentJson {

    @SerializedName("videoId")
    @Expose
    private String videoId;
    @SerializedName("mobile")
    @Expose
    private String mobile;


    public VideosPaymentJson(String videoId, String mobile) {
        this.videoId = videoId;
        this.mobile = mobile;
    }


    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
