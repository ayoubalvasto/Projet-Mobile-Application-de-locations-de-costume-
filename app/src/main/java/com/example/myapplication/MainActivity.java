package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;
import android.widget.Button;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnHistorique;
    private Button btnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Configuration de la liste (RecyclerView)
        recyclerView = findViewById(R.id.recyclerViewCostumes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnHistorique = findViewById(R.id.btnHistorique);
        btnHistorique.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, HistoryActivity.class)));
        btnAccount = findViewById(R.id.btnAccount);
        btnAccount.setOnClickListener(v -> handleAccountAction());

        // 2. Utiliser le client API centralisé
        ApiService apiService = ApiClient.getApiService();

        // 3. Appel API avec gestion d'erreurs améliorée
        apiService.getCostumes().enqueue(new Callback<CostumeListResponse>() {
            @Override
            public void onResponse(Call<CostumeListResponse> call, Response<CostumeListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Costume> costumes = response.body().data;

                    if (costumes.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Aucun costume disponible", Toast.LENGTH_SHORT).show();
                    } else {
                    // 4. On donne la liste à l'adaptateur pour l'afficher
                    CostumeAdapter adapter = new CostumeAdapter(MainActivity.this, costumes);
                    recyclerView.setAdapter(adapter);
                    }
                } else {
                    // Gestion d'erreur améliorée
                    ErrorHandler.handleHttpError(MainActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<CostumeListResponse> call, Throwable t) {
                // Gestion d'erreur réseau améliorée
                ErrorHandler.handleError(MainActivity.this, t);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAccountButtonLabel();
    }

    private void handleAccountAction() {
        if (ClientAuthManager.isLoggedIn(this)) {
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(getString(R.string.logout_client))
                    .setMessage(getString(R.string.confirm_logout_message))
                    .setPositiveButton(R.string.logout, (dialog, which) -> {
                        ClientAuthManager.clear(this);
                        updateAccountButtonLabel();
                        Toast.makeText(this, "Déconnecté", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton(R.string.cancel, null)
                    .show();
        } else {
            startActivity(new Intent(MainActivity.this, ClientLoginActivity.class));
        }
    }

    private void updateAccountButtonLabel() {
        if (btnAccount == null) return;
        if (ClientAuthManager.isLoggedIn(this)) {
            String name = ClientAuthManager.getName(this);
            btnAccount.setText(name != null && !name.isEmpty() ? name : getString(R.string.logout_client));
        } else {
            btnAccount.setText(R.string.client_account);
        }
    }
}