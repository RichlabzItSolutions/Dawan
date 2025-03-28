package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelGetVideos {

    public static class Video{

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("videoTitle")
        @Expose
        private String videoTitle;
        @SerializedName("videoStatus")
        @Expose
        private String videoStatus;
        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("isPaid")
        @Expose
        private String isPaid;
        @SerializedName("displayType")
        @Expose
        private String displayType;
        @SerializedName("youtubeLink")
        @Expose
        private String youtubeLink;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("paidAmount")
        @Expose
        private String paidAmount;
        @SerializedName("addedOn")
        @Expose
        private String addedOn;
        @SerializedName("file")
        @Expose
        private String file;

        @SerializedName("paid")
        @Expose
        private String paid;

        @SerializedName("paymentType")
        @Expose
        private String paymentType;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getVideoStatus() {
            return videoStatus;
        }

        public void setVideoStatus(String videoStatus) {
            this.videoStatus = videoStatus;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(String isPaid) {
            this.isPaid = isPaid;
        }

        public String getDisplayType() {
            return displayType;
        }

        public void setDisplayType(String displayType) {
            this.displayType = displayType;
        }

        public String getYoutubeLink() {
            return youtubeLink;
        }

        public void setYoutubeLink(String youtubeLink) {
            this.youtubeLink = youtubeLink;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getAddedOn() {
            return addedOn;
        }

        public void setAddedOn(String addedOn) {
            this.addedOn = addedOn;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }


    }

    public static class videosResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<ModelGetVideos.Video> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<ModelGetVideos.Video> getData() {
            return data;
        }

        public void setData(List<ModelGetVideos.Video> data) {
            this.data = data;
        }
    }


}
