package com.example.myapplication;

/**
 * Réponse pour les opérations d'authentification (login/register/me).
 */
public class AuthResponse {
    public boolean success;
    public String message;
    public String token;
    public UserItem user;
    public String role;
}


