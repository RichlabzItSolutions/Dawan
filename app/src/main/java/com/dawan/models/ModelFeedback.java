package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFeedback {
    public static class FeedbackRequest{
        @SerializedName("queryId")
        @Expose
        public String queryId;
        @SerializedName("message")
        @Expose
        public String message;

        public FeedbackRequest(String queryId, String message) {
            this.queryId = queryId;
            this.message = message;
        }

        public String getQueryId() {
            return queryId;
        }

        public void setQueryId(String queryId) {
            this.queryId = queryId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
