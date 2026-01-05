package com.example.myapplication;

public class RegisterRequest {
    String name;
    String email;
    String password;
    String password_confirmation;

    public RegisterRequest(String name, String email, String password, String password_confirmation) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }
}










