package com.share.greencloud.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class GreenCloudPreferences {

    private static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(GreenCloudPreferences.class.getName(), Context.MODE_PRIVATE);
    }

    public static void setOnBoarding(Context context, boolean isOnBoarding){
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("onBoarding", isOnBoarding);
        editor.apply();
    }

    public static boolean getOnBoarding(Context context){
        return getPref(context).getBoolean("onBoarding", false);
    }

    public static void setToken(Context context, String token) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static void setUserID(Context context, String userID) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_id", userID);
        editor.apply();
    }

    public static void setUserProfileImage(Context context, String userProfileImage) {
        SharedPreferences pref = getPref(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_profile_image", userProfileImage);
        editor.apply();
    }


    public static String getToken(Context context) {
        return getPref(context).getString("token", "");
    }

    public static String getUserId(Context context) {
        return getPref(context).getString("user_id", "");
    }

    public static String getUserProfileImage(Context context) {
        return getPref(context).getString("user_profile_image", "");
    }
}
