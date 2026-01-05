package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Connexion Admin via Basic Auth (valide les identifiants en appelant /api/admin/clients).
 */
public class AdminLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        EditText etUser = findViewById(R.id.etAdminUser);
        EditText etPass = findViewById(R.id.etAdminPass);
        Button btn = findViewById(R.id.btnAdminLogin);

        // Si déjà renseigné, aller direct au dashboard
        if (AdminCredentialsManager.getUser(this) != null && AdminCredentialsManager.getPass(this) != null) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
            finish();
            return;
        }

        btn.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, getString(R.string.admin_invalid_credentials), Toast.LENGTH_SHORT).show();
                return;
            }

            AdminCredentialsManager.save(this, user, pass);

            // Test: GET clients (si 200 => ok, sinon 401)
            AdminApiService service = AdminApiClient.getService(this);
            service.getClients().enqueue(new Callback<UserListResponse>() {
                @Override
                public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                    if (response.isSuccessful()) {
                        startActivity(new Intent(AdminLoginActivity.this, AdminDashboardActivity.class));
                        finish();
                    } else {
                        AdminCredentialsManager.clear(AdminLoginActivity.this);
                        Toast.makeText(AdminLoginActivity.this, getString(R.string.admin_invalid_credentials), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserListResponse> call, Throwable t) {
                    AdminCredentialsManager.clear(AdminLoginActivity.this);
                    ErrorHandler.handleError(AdminLoginActivity.this, t);
                }
            });
        });
    }
}


