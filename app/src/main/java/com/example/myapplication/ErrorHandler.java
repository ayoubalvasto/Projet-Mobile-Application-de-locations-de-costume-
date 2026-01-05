package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;
import retrofit2.Response;
import java.io.IOException;

/**
 * Classe utilitaire pour gérer les erreurs de manière centralisée
 */
public class ErrorHandler {
    
    /**
     * Affiche un message d'erreur approprié selon le type d'erreur
     */
    public static void handleError(Context context, Throwable throwable) {
        String message = "Une erreur est survenue";
        
        if (throwable instanceof java.net.SocketTimeoutException) {
            message = "Temps d'attente dépassé. Vérifiez votre connexion.";
        } else if (throwable instanceof java.net.UnknownHostException) {
            message = "Impossible de se connecter au serveur. Vérifiez l'URL.";
        } else if (throwable instanceof java.net.ConnectException) {
            message = "Connexion refusée. Le serveur est-il démarré ?";
        } else if (throwable instanceof IOException) {
            message = "Erreur de connexion réseau";
        } else {
            message = "Erreur : " + throwable.getMessage();
        }
        
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    
    /**
     * Affiche un message d'erreur selon le code de réponse HTTP
     */
    public static void handleHttpError(Context context, Response<?> response) {
        String message = "Erreur serveur";
        
        if (response != null) {
            switch (response.code()) {
                case 400:
                    message = "Requête invalide";
                    break;
                case 401:
                    message = "Non autorisé";
                    break;
                case 403:
                    message = "Accès refusé";
                    break;
                case 404:
                    message = "Ressource non trouvée";
                    break;
                case 409:
                    // Conflit (souvent dû à un chevauchement de dates)
                    message = "Ce costume est déjà réservé sur cette période";
                    break;
                case 422:
                    message = "Données invalides";
                    break;
                case 500:
                    message = "Erreur interne du serveur";
                    break;
                case 503:
                    message = "Service indisponible";
                    break;
                default:
                    message = "Erreur serveur (" + response.code() + ")";
                    break;
            }

            // Essayer de récupérer le message détaillé renvoyé par l'API
            try {
                if (response.errorBody() != null) {
                    String errorBody = response.errorBody().string();
                    com.google.gson.JsonElement json = com.google.gson.JsonParser.parseString(errorBody);
                    if (json.isJsonObject()) {
                        com.google.gson.JsonObject obj = json.getAsJsonObject();
                        if (obj.has("message") && !obj.get("message").isJsonNull()) {
                            message = obj.get("message").getAsString();
                        } else if (obj.has("errors")) {
                            // Récupère le premier message de validation s'il existe
                            com.google.gson.JsonObject errors = obj.getAsJsonObject("errors");
                            if (!errors.entrySet().isEmpty()) {
                                com.google.gson.JsonElement first = errors.entrySet().iterator().next().getValue();
                                if (first.isJsonArray() && first.getAsJsonArray().size() > 0) {
                                    message = first.getAsJsonArray().get(0).getAsString();
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                // On garde le message par défaut en cas d'erreur de parsing
            }
        }

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}

