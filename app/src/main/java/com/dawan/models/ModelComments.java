package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelComments {
    public static class GetCommentsResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<comment> data;

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

        public List<comment> getData() {
            return data;
        }

        public void setData(List<comment> data) {
            this.data = data;
        }

    }
    public static class comment {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("commenterId")
        @Expose
        private String commenterId;
        @SerializedName("commenterName")
        @Expose
        private String commenterName;
        @SerializedName("commentorEmail")
        @Expose
        private String commentorEmail;
        @SerializedName("commentorAuthorUrl")
        @Expose
        private String commentorAuthorUrl;
        @SerializedName("commentDate")
        @Expose
        private String commentDate;
        @SerializedName("commentContent")
        @Expose
        private String commentContent;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCommenterId() {
            return commenterId;
        }

        public void setCommenterId(String commenterId) {
            this.commenterId = commenterId;
        }

        public String getCommenterName() {
            return commenterName;
        }

        public void setCommenterName(String commenterName) {
            this.commenterName = commenterName;
        }

        public String getCommentorEmail() {
            return commentorEmail;
        }

        public void setCommentorEmail(String commentorEmail) {
            this.commentorEmail = commentorEmail;
        }

        public String getCommentorAuthorUrl() {
            return commentorAuthorUrl;
        }

        public void setCommentorAuthorUrl(String commentorAuthorUrl) {
            this.commentorAuthorUrl = commentorAuthorUrl;
        }

        public String getCommentDate() {
            return commentDate;
        }

        public void setCommentDate(String commentDate) {
            this.commentDate = commentDate;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

    }
    public static class commentRequest{
        @SerializedName("postId")
        @Expose
        private String postId;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("websiteLink")
        @Expose
        private String websiteLink;

        public commentRequest(String postId, String comment) {
            this.postId = postId;
            this.comment = comment;
        }

        public commentRequest(String postId) {
            this.postId = postId;
        }

        public commentRequest(String postId, String comment, String websiteLink) {
            this.postId = postId;
            this.comment = comment;
            this.websiteLink = websiteLink;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getWebsiteLink() {
            return websiteLink;
        }

        public void setWebsiteLink(String websiteLink) {
            this.websiteLink = websiteLink;
        }
    }
}
