package com.example.myapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientRegisterActivity extends AppCompatActivity {

    private MaterialButton btnRegister;
    private TextInputEditText etName, etEmail, etPassword, etPasswordConfirm;
    private TextView tvGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        btnRegister = findViewById(R.id.btnRegister);
        tvGoLogin = findViewById(R.id.tvGoLogin);

        btnRegister.setOnClickListener(v -> doRegister());
        tvGoLogin.setOnClickListener(v -> finish());
    }

    private void doRegister() {
        String name = etName.getText() != null ? etName.getText().toString().trim() : "";
        String email = etEmail.getText() != null ? etEmail.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String passwordConfirm = etPasswordConfirm.getText() != null ? etPasswordConfirm.getText().toString().trim() : "";

        if (name.isEmpty()) {
            Toast.makeText(this, "Nom obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "Email obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mot de passe obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        btnRegister.setEnabled(false);
        ApiService api = ApiClient.getApiService();
        RegisterRequest request = new RegisterRequest(name, email, password, passwordConfirm);
        api.register(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnRegister.setEnabled(true);
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    AuthResponse resp = response.body();
                    String name = resp.user != null ? resp.user.name : "";
                    String email = resp.user != null ? resp.user.email : "";
                    String role = resp.user != null ? resp.user.role : resp.role;
                    ClientAuthManager.saveAuth(ClientRegisterActivity.this, resp.token, name, email, role);
                    Toast.makeText(ClientRegisterActivity.this, "Compte créé", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ErrorHandler.handleHttpError(ClientRegisterActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnRegister.setEnabled(true);
                ErrorHandler.handleError(ClientRegisterActivity.this, t);
            }
        });
    }
}


