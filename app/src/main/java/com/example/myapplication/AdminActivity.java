package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btnLogin = findViewById(R.id.btnAdminLogin);
        Button btnCostumes = findViewById(R.id.btnAdminCostumes);
        Button btnClients = findViewById(R.id.btnAdminClients);
        Button btnReservations = findViewById(R.id.btnAdminReservations);

        btnLogin.setOnClickListener(v -> showAdminCredentialsDialog());
        btnCostumes.setOnClickListener(v -> startActivity(new Intent(this, AdminCostumesActivity.class)));
        btnClients.setOnClickListener(v -> startActivity(new Intent(this, AdminClientsActivity.class)));
        btnReservations.setOnClickListener(v -> startActivity(new Intent(this, AdminReservationsActivity.class)));
    }

    private void showAdminCredentialsDialog() {
        EditText etUser = new EditText(this);
        etUser.setHint("Admin user (ex: admin)");
        EditText etPass = new EditText(this);
        etPass.setHint("Admin pass");

        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        int pad = (int) (16 * getResources().getDisplayMetrics().density);
        layout.setPadding(pad, pad, pad, pad);
        layout.addView(etUser);
        layout.addView(etPass);

        new AlertDialog.Builder(this)
                .setTitle("Identifiants Admin (Basic Auth)")
                .setView(layout)
                .setPositiveButton("Enregistrer", (d, which) -> AdminSession.save(this,
                        etUser.getText().toString().trim(),
                        etPass.getText().toString().trim()
                ))
                .setNegativeButton("Annuler", null)
                .show();
    }
}


