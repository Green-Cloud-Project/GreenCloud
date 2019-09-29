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

    public static String getToken(Context context) {
        return getPref(context).getString("token", "");
    }
}
