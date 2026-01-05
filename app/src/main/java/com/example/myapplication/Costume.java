package com.example.myapplication;

public class Costume {
    // Les noms doivent être IDENTIQUES à ceux de votre JSON (id, nom, taille...)
    public int id;
    public String nom;
    public String taille;
    public double prix;
    public String image;

    // Pour vérifier facilement dans les logs
    @Override
    public String toString() {
        return nom + " (" + prix + " DH)";
    }
}