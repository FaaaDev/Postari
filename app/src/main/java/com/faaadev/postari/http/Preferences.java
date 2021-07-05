package com.faaadev.postari.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.faaadev.postari.model.User;
import com.google.gson.Gson;

public class Preferences {

    static final String KEY_USER_TEREGISTER ="user", KEY_PASS_TEREGISTER ="pass";


    private static SharedPreferences getSharedPreference(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveUser(User user, Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();

        Gson gson = new Gson();

        String json = gson.toJson(user);

        editor.putString(KEY_USER_TEREGISTER, json);
        editor.apply();

        System.out.println("JSON ==== "+json);
    }

    public static String getUsername(Context context){
        Gson gson = new Gson();
        User user = gson.fromJson(getSharedPreference(context).getString(KEY_USER_TEREGISTER,""), User.class);

        return user.getUsername();
    }

    public static String getRole(Context context){
        Gson gson = new Gson();
        User user = gson.fromJson(getSharedPreference(context).getString(KEY_USER_TEREGISTER,""), User.class);

        return user.getRole();
    }

    public static String getImage(Context context){
        Gson gson = new Gson();
        User user = gson.fromJson(getSharedPreference(context).getString(KEY_USER_TEREGISTER,""), User.class);

        return user.getImage();
    }

    public static String getUserId(Context context){
        Gson gson = new Gson();
        User user = gson.fromJson(getSharedPreference(context).getString(KEY_USER_TEREGISTER,""), User.class);

        return user.getUser_id();
    }

    public static Boolean isLogedIn(Context context){
        return !TextUtils.isEmpty(getSharedPreference(context).getString(KEY_USER_TEREGISTER,""));
    }


    public static void clearLoggedInUser (Context context){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.remove(KEY_USER_TEREGISTER);
        editor.apply();
    }
}