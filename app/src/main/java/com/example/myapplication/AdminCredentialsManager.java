package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Stockage simple des identifiants Admin (Basic Auth) pour l'API /api/admin/*
 * Note: en production, il faut éviter de stocker en clair. Ici c'est pour un projet scolaire.
 */
public class AdminCredentialsManager {
    private static final String PREFS = "admin_prefs";
    private static final String KEY_USER = "admin_user";
    private static final String KEY_PASS = "admin_pass";

    public static void save(Context context, String user, String pass) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_USER, user).putString(KEY_PASS, pass).apply();
    }

    public static String getUser(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_USER, null);
    }

    public static String getPass(Context context) {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_PASS, null);
    }

    public static void clear(Context context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().clear().apply();
    }
}


