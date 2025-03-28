package com.dawan.models;


import java.util.List;

public class ModelNews {
    boolean isvideo,isheader;
    List<subnews> subnewslist;

    public ModelNews(boolean isvideo, boolean isheader, List<subnews> subnewslist) {
        this.isvideo = isvideo;
        this.isheader = isheader;
        this.subnewslist = subnewslist;
    }

    public boolean isIsvideo() {
        return isvideo;
    }

    public void setIsvideo(boolean isvideo) {
        this.isvideo = isvideo;
    }

    public boolean isIsheader() {
        return isheader;
    }

    public void setIsheader(boolean isheader) {
        this.isheader = isheader;
    }

    public List<subnews> getSubnewslist() {
        return subnewslist;
    }

    public void setSubnewslist(List<subnews> subnewslist) {
        this.subnewslist = subnewslist;
    }

    public static class subnews{

    }
}

