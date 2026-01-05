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

public class AdminClientsActivity extends AppCompatActivity {

    private final List<UserItem> clients = new ArrayList<>();
    private AdminClientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        if (!isAdmin()) return;

        TextView title = findViewById(R.id.tvAdminTitle);
        title.setText(getString(R.string.admin_clients));

        Button btnAdd = findViewById(R.id.btnAdminAdd);
        btnAdd.setVisibility(View.GONE); // pas de création clients ici

        RecyclerView recycler = findViewById(R.id.recyclerAdmin);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminClientAdapter(this, clients, new AdminClientAdapter.Listener() {
            @Override
            public void onEdit(UserItem user) {
                android.widget.Toast.makeText(AdminClientsActivity.this, "Édition non implémentée", android.widget.Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDelete(UserItem user) {
                deleteClient(user.id);
            }
        });
        recycler.setAdapter(adapter);

        loadClients();
    }

    private void loadClients() {
        AdminApiService service = AdminApiClient.getService(this);
        service.getClients().enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    clients.clear();
                    clients.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                } else {
                    ErrorHandler.handleHttpError(AdminClientsActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                ErrorHandler.handleError(AdminClientsActivity.this, t);
            }
        });
    }

    private void deleteClient(int id) {
        AdminApiService service = AdminApiClient.getService(this);
        service.deleteClient(id).enqueue(new Callback<GenericResponse<Object>>() {
            @Override
            public void onResponse(Call<GenericResponse<Object>> call, Response<GenericResponse<Object>> response) {
                if (response.isSuccessful()) {
                    loadClients();
                } else {
                    ErrorHandler.handleHttpError(AdminClientsActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Object>> call, Throwable t) {
                ErrorHandler.handleError(AdminClientsActivity.this, t);
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
