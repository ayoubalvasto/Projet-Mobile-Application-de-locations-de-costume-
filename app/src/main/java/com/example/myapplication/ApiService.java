package com.example.myapplication;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Header;

// 1. "interface" au lieu de "class"
public interface ApiService {

    // 2. L'annotation @GET est OBLIGATOIRE pour dire où chercher
    @GET("api/costumes")
    Call<CostumeListResponse> getCostumes();
    // 3. Pas d'accolades { ... } ni de "return null", juste un point-virgule ;
    @POST("api/locations")
    Call<LocationResponse> louerCostume(@Header("Authorization") String bearerToken, @Body LocationRequest request);

    @GET("api/locations")
    Call<LocationListResponse> getLocations();

    @GET("api/locations/mine")
    Call<LocationListResponse> getMyLocations(@Header("Authorization") String bearerToken);

    // Auth client
    @POST("api/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("api/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @POST("api/logout")
    Call<SimpleResponse> logout(@Header("Authorization") String bearerToken);

    @GET("api/me")
    Call<AuthResponse> me(@Header("Authorization") String bearerToken);
}