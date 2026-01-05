package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        if (!isAdmin()) return;

        View btnCostumes = findViewById(R.id.btnAdminCostumes);
        View btnClients = findViewById(R.id.btnAdminClients);
        View btnReservations = findViewById(R.id.btnAdminReservations);
        Button btnLogout = findViewById(R.id.btnAdminLogout);

        btnCostumes.setOnClickListener(v -> startActivity(new Intent(this, AdminCostumesActivity.class)));
        btnClients.setOnClickListener(v -> startActivity(new Intent(this, AdminClientsActivity.class)));
        btnReservations.setOnClickListener(v -> startActivity(new Intent(this, AdminReservationsActivity.class)));
        btnLogout.setOnClickListener(v -> {
            ClientAuthManager.clear(this);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
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


