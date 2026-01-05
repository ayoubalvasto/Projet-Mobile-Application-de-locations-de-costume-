package com.example.myapplication;

public class AdminCostumeRequest {
    public String nom;
    public String taille;
    public double prix;
    public String image;

    public AdminCostumeRequest(String nom, String taille, double prix, String image) {
        this.nom = nom;
        this.taille = taille;
        this.prix = prix;
        this.image = image;
    }
}

