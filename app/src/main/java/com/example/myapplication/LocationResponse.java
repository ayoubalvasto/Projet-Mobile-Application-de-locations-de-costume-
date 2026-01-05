package com.example.myapplication;

/**
 * Classe de réponse pour les requêtes de location
 * Compatible avec les nouvelles réponses du backend (success, message, data)
 */
public class LocationResponse {
    boolean success;
    String message;
    Object data; // Peut contenir les détails de la location
    
    // Getters pour faciliter l'accès
    public boolean isSuccess() {
        return success;
    }
    
    public String getMessage() {
        return message != null ? message : "";
    }
}