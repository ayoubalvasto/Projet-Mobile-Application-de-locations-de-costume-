package com.example.myapplication;

/**
 * Classe de configuration centralisée pour l'API
 * Permet de changer l'URL de base facilement sans modifier tout le code
 */
public class ApiConfig {
    
    // URL de base de l'API
    // Pour l'émulateur Android : http://10.0.2.2:8000/
    // Pour un appareil physique sur le même réseau : http://192.168.x.x:8000/
    // Pour production : https://votre-domaine.com/api/

    // Par défaut pour l'émulateur Android
    public static final String BASE_URL = "http://192.168.1.114:8000/";
    
    // Endpoints de l'API
    public static final String ENDPOINT_COSTUMES = "api/costumes";
    public static final String ENDPOINT_LOCATIONS = "api/locations";
    
    // Timeout pour les requêtes (en secondes)
    public static final int TIMEOUT_SECONDS = 30;
    
    /**
     * Construit l'URL complète pour un endpoint
     */
    public static String getFullUrl(String endpoint) {
        return BASE_URL + endpoint;
    }
}

