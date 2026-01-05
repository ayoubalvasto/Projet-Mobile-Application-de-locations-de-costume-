package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminApiService {

    // Costumes CRUD
    @GET("api/admin/costumes")
    Call<CostumeListResponse> getCostumes();

    @POST("api/admin/costumes")
    Call<GenericResponse<Costume>> createCostume(@Body AdminCostumeRequest req);

    @PUT("api/admin/costumes/{id}")
    Call<GenericResponse<Costume>> updateCostume(@Path("id") int id, @Body AdminCostumeRequest req);

    @DELETE("api/admin/costumes/{id}")
    Call<GenericResponse<Object>> deleteCostume(@Path("id") int id);

    // Clients
    @GET("api/admin/clients")
    Call<UserListResponse> getClients();

    @DELETE("api/admin/clients/{id}")
    Call<GenericResponse<Object>> deleteClient(@Path("id") int id);

    // Réservations
    @GET("api/admin/reservations")
    Call<ReservationListResponse> getReservations();

    @PATCH("api/admin/reservations/{id}/statut")
    Call<GenericResponse<AdminReservation>> updateReservationStatus(@Path("id") int id, @Body StatusRequest req);
}
