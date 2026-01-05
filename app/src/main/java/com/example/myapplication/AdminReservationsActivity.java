package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminReservationsActivity extends AppCompatActivity {

    private final List<AdminReservation> reservations = new ArrayList<>();
    private AdminReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        if (!isAdmin()) return;

        TextView title = findViewById(R.id.tvAdminTitle);
        title.setText(getString(R.string.admin_reservations));

        Button btnAdd = findViewById(R.id.btnAdminAdd);
        btnAdd.setVisibility(View.GONE);

        RecyclerView recycler = findViewById(R.id.recyclerAdmin);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminReservationAdapter(this, reservations, (reservation, newStatus) -> updateStatus(reservation.id, newStatus));
        recycler.setAdapter(adapter);

        loadReservations();
    }

    private void loadReservations() {
        AdminApiService service = AdminApiClient.getService(this);
        service.getReservations().enqueue(new Callback<ReservationListResponse>() {
            @Override
            public void onResponse(Call<ReservationListResponse> call, Response<ReservationListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    reservations.clear();
                    reservations.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                } else {
                    ErrorHandler.handleHttpError(AdminReservationsActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<ReservationListResponse> call, Throwable t) {
                ErrorHandler.handleError(AdminReservationsActivity.this, t);
            }
        });
    }

    private void updateStatus(int id, String statut) {
        AdminApiService service = AdminApiClient.getService(this);
        service.updateReservationStatus(id, new StatusRequest(statut))
                .enqueue(new Callback<GenericResponse<AdminReservation>>() {
                    @Override
                    public void onResponse(Call<GenericResponse<AdminReservation>> call, Response<GenericResponse<AdminReservation>> response) {
                        if (response.isSuccessful()) {
                            loadReservations();
                        } else {
                            ErrorHandler.handleHttpError(AdminReservationsActivity.this, response);
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse<AdminReservation>> call, Throwable t) {
                        ErrorHandler.handleError(AdminReservationsActivity.this, t);
                    }
                });
    }

    private boolean isAdmin() {
        if (!ClientAuthManager.isLoggedIn(this) || !"admin".equalsIgnoreCase(ClientAuthManager.getRole(this))) {
            startActivity(new android.content.Intent(this, ClientLoginActivity.class));
            finish();
            return false;
        }
        return true;
    }
}


