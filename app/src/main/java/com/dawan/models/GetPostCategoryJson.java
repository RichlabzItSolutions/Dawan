package com.dawan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPostCategoryJson {

    @SerializedName("categoryId")
    @Expose
    private String categoryId;


    public GetPostCategoryJson(String categoryId) {
        this.categoryId = categoryId;
    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
