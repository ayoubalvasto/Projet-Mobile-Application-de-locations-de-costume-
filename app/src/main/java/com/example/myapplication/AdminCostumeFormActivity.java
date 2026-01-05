package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCostumeFormActivity extends AppCompatActivity {

    private int costumeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_costume_form);

        if (!isAdmin()) return;

        TextView title = findViewById(R.id.tvFormTitle);
        EditText etNom = findViewById(R.id.etNom);
        EditText etTaille = findViewById(R.id.etTaille);
        EditText etPrix = findViewById(R.id.etPrix);
        EditText etImage = findViewById(R.id.etImage);
        Button btnSave = findViewById(R.id.btnSaveCostume);

        costumeId = getIntent().getIntExtra("id", -1);
        if (costumeId > 0) {
            title.setText("Modifier costume #" + costumeId);
            etNom.setText(getIntent().getStringExtra("nom"));
            etTaille.setText(getIntent().getStringExtra("taille"));
            etPrix.setText(String.valueOf(getIntent().getDoubleExtra("prix", 0)));
            etImage.setText(getIntent().getStringExtra("image"));
        } else {
            title.setText("Ajouter un costume");
        }

        btnSave.setOnClickListener(v -> {
            String nom = etNom.getText().toString().trim();
            String taille = etTaille.getText().toString().trim();
            String prixStr = etPrix.getText().toString().trim();
            String image = etImage.getText().toString().trim();

            if (nom.isEmpty() || taille.isEmpty() || prixStr.isEmpty()) {
                Toast.makeText(this, "Nom, taille et prix sont obligatoires", Toast.LENGTH_SHORT).show();
                return;
            }

            double prix;
            try { prix = Double.parseDouble(prixStr); }
            catch (Exception e) { Toast.makeText(this, "Prix invalide", Toast.LENGTH_SHORT).show(); return; }

            AdminCostumeRequest req = new AdminCostumeRequest(nom, taille, prix, image.isEmpty() ? null : image);
            AdminApiService service = AdminApiClient.getService(this);

            Call<GenericResponse<Costume>> call = (costumeId > 0)
                    ? service.updateCostume(costumeId, req)
                    : service.createCostume(req);

            call.enqueue(new Callback<GenericResponse<Costume>>() {
                @Override
                public void onResponse(Call<GenericResponse<Costume>> call, Response<GenericResponse<Costume>> response) {
                    if (response.isSuccessful()) {
                        finish();
                    } else {
                        ErrorHandler.handleHttpError(AdminCostumeFormActivity.this, response);
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse<Costume>> call, Throwable t) {
                    ErrorHandler.handleError(AdminCostumeFormActivity.this, t);
                }
            });
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


