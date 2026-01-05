package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button btnExplore;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnExplore = findViewById(R.id.btnExplore);
        btnLogin = findViewById(R.id.btnLoginClient);

        btnExplore.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MainActivity.class)));

        btnLogin.setOnClickListener(v -> {
            if (ClientAuthManager.isLoggedIn(HomeActivity.this)) {
                String role = ClientAuthManager.getRole(HomeActivity.this);
                navigateByRole(role);
            } else {
                startActivity(new Intent(HomeActivity.this, ClientLoginActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateButtonText();
    }

    private void updateButtonText() {
        if (ClientAuthManager.isLoggedIn(this)) {
            String name = ClientAuthManager.getName(this);
            String buttonText = (name != null && !name.isEmpty()) ? 
                "Mon espace (" + name + ")" : "Mon espace";
            btnLogin.setText(buttonText);
        } else {
            btnLogin.setText(getString(R.string.btn_login));
        }
    }

    private void navigateByRole(String role) {
        if ("admin".equalsIgnoreCase(role)) {
            startActivity(new Intent(HomeActivity.this, AdminDashboardActivity.class));
        } else {
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
        }
    }
}


