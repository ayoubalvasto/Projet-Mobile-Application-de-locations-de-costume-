package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
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

public class AdminCostumesActivity extends AppCompatActivity {

    private final List<Costume> costumes = new ArrayList<>();
    private AdminCostumeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_list);

        if (!isAdmin()) return;

        TextView title = findViewById(R.id.tvAdminTitle);
        title.setText(getString(R.string.admin_costumes));

        Button btnAdd = findViewById(R.id.btnAdminAdd);
        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AdminCostumeFormActivity.class)));

        RecyclerView recycler = findViewById(R.id.recyclerAdmin);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AdminCostumeAdapter(this, costumes, new AdminCostumeAdapter.Listener() {
            @Override
            public void onEdit(Costume costume) {
                Intent i = new Intent(AdminCostumesActivity.this, AdminCostumeFormActivity.class);
                i.putExtra("id", costume.id);
                i.putExtra("nom", costume.nom);
                i.putExtra("taille", costume.taille);
                i.putExtra("prix", costume.prix);
                i.putExtra("image", costume.image);
                startActivity(i);
            }

            @Override
            public void onDelete(Costume costume) {
                deleteCostume(costume.id);
            }
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAdmin()) {
            loadCostumes();
        }
    }

    private void loadCostumes() {
        AdminApiService service = AdminApiClient.getService(this);
        service.getCostumes().enqueue(new Callback<CostumeListResponse>() {
            @Override
            public void onResponse(Call<CostumeListResponse> call, Response<CostumeListResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().data != null) {
                    costumes.clear();
                    costumes.addAll(response.body().data);
                    adapter.notifyDataSetChanged();
                } else {
                    ErrorHandler.handleHttpError(AdminCostumesActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<CostumeListResponse> call, Throwable t) {
                ErrorHandler.handleError(AdminCostumesActivity.this, t);
            }
        });
    }

    private void deleteCostume(int id) {
        AdminApiService service = AdminApiClient.getService(this);
        service.deleteCostume(id).enqueue(new Callback<GenericResponse<Object>>() {
            @Override
            public void onResponse(Call<GenericResponse<Object>> call, Response<GenericResponse<Object>> response) {
                if (response.isSuccessful()) {
                    loadCostumes();
                } else {
                    ErrorHandler.handleHttpError(AdminCostumesActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<GenericResponse<Object>> call, Throwable t) {
                ErrorHandler.handleError(AdminCostumesActivity.this, t);
            }
        });
    }

    private boolean isAdmin() {
        if (!ClientAuthManager.isLoggedIn(this) || !"admin".equalsIgnoreCase(ClientAuthManager.getRole(this))) {
            startActivity(new Intent(this, ClientLoginActivity.class));
            finish();
            return false;
        }
        return true;
    }
}


