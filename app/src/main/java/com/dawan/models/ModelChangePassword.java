package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChangePassword {
    public static class ChangePasswordRequest{
        //mobile,otp,newPassword
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("otp")
        private String otp;
        @Expose
        @SerializedName("newPassword")
        private String newPassword;

        public ChangePasswordRequest(String email, String otp, String newPassword) {
            this.email = email;
            this.otp = otp;
            this.newPassword = newPassword;
        }

        public ChangePasswordRequest(String email) {
            this.email = email;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }
}
