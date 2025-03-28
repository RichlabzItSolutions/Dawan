package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPost {
    public static class getPostResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<post> data;

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

        public List<post> getData() {
            return data;
        }

        public void setData(List<post> data) {
            this.data = data;
        }
    }

    public static class post {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("postDate")
        @Expose
        private String postDate;
        @SerializedName("postContent")
        @Expose
        private String postContent;
        @SerializedName("file")
        @Expose
        private String file;



        @SerializedName("is_paid")
        @Expose
        private String is_paid;

        @SerializedName("paid_amount")
        @Expose
        private String paid_amount;

        @SerializedName("paid")
        @Expose
        private String paid;

        @SerializedName("paymentType")
        @Expose
        private String paymentType;



        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("saved")
        @Expose
        private Boolean saved = false;





        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Boolean getSaved() {
            return saved;
        }

        public void setSaved(Boolean saved) {
            this.saved = saved;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }






        public String getIs_paid() {
            return is_paid;
        }

        public void setIs_paid(String is_paid) {
            this.is_paid = is_paid;
        }


        public String getPaid_amount() {
            return paid_amount;
        }

        public void setPaid_amount(String paid_amount) {
            this.paid_amount = paid_amount;
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



        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }
    }

    public static class searchRequest {
        @SerializedName("searchString")
        @Expose
        private String searchString;

        public searchRequest(String searchString) {
            this.searchString = searchString;
        }

        public String getSearchString() {
            return searchString;
        }

        public void setSearchString(String searchString) {
            this.searchString = searchString;
        }
    }

    public static class saveRequest {
        @SerializedName("postId")
        @Expose
        private String postId;

        public saveRequest(String postId) {
            this.postId = postId;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }
    }

    public static class postDetailsResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("postName")
        @Expose
        private String postName;
        @SerializedName("postDate")
        @Expose
        private String postDate;
        @SerializedName("postContent")
        @Expose
        private String postContent;
        @SerializedName("file")
        @Expose
        private String file;
        @SerializedName("saved")
        @Expose
        private boolean saved;
        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("data")
        @Expose
        private List<post> subPostList;

        @SerializedName("isPaid")
        @Expose
        private String isPaid;

        @SerializedName("paidAmount")
        @Expose
        private String paidAmount;

        @SerializedName("paid")
        @Expose
        private String paid;

        @SerializedName("paymentType")
        @Expose
        private String paymentType;


        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public boolean isSaved() {
            return saved;
        }

        public void setSaved(boolean saved) {
            this.saved = saved;
        }

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getPostDate() {
            return postDate;
        }

        public void setPostDate(String postDate) {
            this.postDate = postDate;
        }

        public String getPostContent() {
            return postContent;
        }

        public void setPostContent(String postContent) {
            this.postContent = postContent;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }


        public String getIsPaid() {
            return isPaid;
        }

        public void setIsPaid(String isPaid) {
            this.isPaid = isPaid;
        }


        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
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



        public List<post> getSubPostList() {
            return subPostList;
        }

        public void setSubPostList(List<post> data) {
            this.subPostList = data;
        }


    }
    public static class postRequest{
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
      /*  @SerializedName("trending")
        @Expose
        private String trending;*/

        public postRequest(String categoryId) {
            this.categoryId = categoryId;
           // this.trending = trending;
        }

      /*  public String getTrending() {
            return trending;
        }

        public void setTrending(String trending) {
            this.trending = trending;
        }*/

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }
    public static class postTrendingRequest{
        @SerializedName("trending")
        @Expose
        private String trending;

        public postTrendingRequest(String trending) {
            this.trending = trending;
        }

        public String getTrending() {
            return trending;
        }

        public void setTrending(String trending) {
            this.trending = trending;
        }
    }
}
