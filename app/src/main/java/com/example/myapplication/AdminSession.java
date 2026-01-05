package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

public class AdminSession {
    private static final String PREFS = "admin_session";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";

    public static void save(Context context, String user, String pass) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_USER, user).putString(KEY_PASS, pass).apply();
    }

    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sp.edit().remove(KEY_USER).remove(KEY_PASS).apply();
    }

    public static String getBasicAuthHeader(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String user = sp.getString(KEY_USER, null);
        String pass = sp.getString(KEY_PASS, null);
        if (user == null || pass == null) return null;
        String token = user + ":" + pass;
        return "Basic " + Base64.encodeToString(token.getBytes(), Base64.NO_WRAP);
    }
}


