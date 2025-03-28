package com.dawan.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static final String id="id";
    public static final String name="name";
    public static final String email="email";
    public static final String mobile="mobile";
    public static final String token="token";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public SharedPref(Context context) {
        pref=context.getApplicationContext().getSharedPreferences("BalajiPref",0);
        editor=pref.edit();
    }
    public void setString(String Key,String Value){
        editor.putString(Key,Value);
        editor.commit();
    }
    public void setInt(String Key,Integer Value){
        editor.putInt(Key,Value);
        editor.commit();
    }

    public void clearAll(){
        editor.clear();
        editor.apply();
    }
    public String getString(String Key){
       return pref.getString(Key,"");
    }
    public Integer getInt(String Key){
        return pref.getInt(Key,0);
    }

}
