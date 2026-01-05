package com.example.myapplication;

public class LocationRequest {
    String nom_client;
    String telephone;
    int costume_id;
    String date_debut;
    String date_fin;
    String taille;
    String note;

    public LocationRequest(String nom_client, String telephone, int costume_id, String date_debut, String date_fin, String taille, String note) {
        this.nom_client = nom_client;
        this.telephone = telephone;
        this.costume_id = costume_id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.taille = taille;
        this.note = note;
    }
}
