package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientLoginActivity extends AppCompatActivity {

    private MaterialButton btnLogin;
    private TextInputEditText etEmail, etPassword;
    private TextView tvGoRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvGoRegister = findViewById(R.id.tvGoRegister);

        btnLogin.setOnClickListener(v -> doLogin());
        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(ClientLoginActivity.this, ClientRegisterActivity.class))
        );
    }

    private void doLogin() {
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";

        if (email.isEmpty()) {
            Toast.makeText(this, "Email obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mot de passe obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        btnLogin.setEnabled(false);
        ApiService api = ApiClient.getApiService();
        api.login(new LoginRequest(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnLogin.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    AuthResponse resp = response.body();
                    String name = resp.user != null ? resp.user.name : "";
                    String email = resp.user != null ? resp.user.email : "";
                    String role = resp.user != null ? resp.user.role : resp.role;
                    ClientAuthManager.saveAuth(ClientLoginActivity.this, resp.token, name, email, role);
                    Toast.makeText(ClientLoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    if ("admin".equalsIgnoreCase(role)) {
                        startActivity(new Intent(ClientLoginActivity.this, AdminDashboardActivity.class));
                    } else {
                        startActivity(new Intent(ClientLoginActivity.this, MainActivity.class));
                    }
                    finish();
                } else {
                    ErrorHandler.handleHttpError(ClientLoginActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                ErrorHandler.handleError(ClientLoginActivity.this, t);
            }
        });
    }
}


