package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 1. Récupérer les vues
        ImageView imgDetail = findViewById(R.id.imgDetail);
        TextView tvNom = findViewById(R.id.tvDetailNom);
        TextView tvPrix = findViewById(R.id.tvDetailPrix);
        TextView tvTaille = findViewById(R.id.tvDetailTaille);
        Button btnLouer = findViewById(R.id.btnLouer);
        EditText etNomClient = findViewById(R.id.etNomClient);
        EditText etDateDebut = findViewById(R.id.etDateDebut);
        EditText etDateFin = findViewById(R.id.etDateFin);
        EditText etTelephone = findViewById(R.id.etTelephone);
        EditText etTaille = findViewById(R.id.etTaille);
        EditText etNote = findViewById(R.id.etNote);

        // 2. Récupérer les données envoyées depuis la liste
        int costumeId = getIntent().getIntExtra("id", -1);
        String nom = getIntent().getStringExtra("nom");
        String prix = getIntent().getStringExtra("prix");
        String taille = getIntent().getStringExtra("taille");
        String image = getIntent().getStringExtra("image");

        // 3. Afficher les données
        tvNom.setText(nom);
        tvPrix.setText(prix + " DH");
        tvTaille.setText("Taille : " + taille);

        // Charger l'image avec Glide et définir le contentDescription pour l'accessibilité
        imgDetail.setContentDescription("Image du costume " + nom);
        Glide.with(this)
                .load(image)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(imgDetail);

        // Sélecteurs de date
        etDateDebut.setOnClickListener(v -> pickDate(etDateDebut));
        etDateFin.setOnClickListener(v -> pickDate(etDateFin));

        // 4. Action du bouton
        btnLouer.setOnClickListener(v -> {
            String nomClient = etNomClient.getText().toString().trim();
            String dateDebut = etDateDebut.getText().toString().trim();
            String dateFin = etDateFin.getText().toString().trim();
            String telephone = etTelephone.getText().toString().trim();
            String tailleChoisie = etTaille.getText().toString().trim();
            String note = etNote.getText().toString().trim();

            if (!ClientAuthManager.isLoggedIn(this)) {
                Toast.makeText(DetailActivity.this, "Connectez-vous pour louer", Toast.LENGTH_SHORT).show();
                startActivity(new android.content.Intent(DetailActivity.this, ClientLoginActivity.class));
                return;
            }

            if (nomClient.isEmpty()) {
                        Toast.makeText(DetailActivity.this, "Le nom est obligatoire", Toast.LENGTH_SHORT).show();
                return;
            }
            if (costumeId <= 0) {
                Toast.makeText(DetailActivity.this, "Costume inconnu", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dateDebut.isEmpty() || dateFin.isEmpty()) {
                Toast.makeText(DetailActivity.this, "Dates début/fin requises", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!datesValides(dateDebut, dateFin)) {
                Toast.makeText(DetailActivity.this, "Date fin doit être après ou égale à date début", Toast.LENGTH_SHORT).show();
                return;
            }

            faireLaLocation(nomClient, telephone, costumeId, dateDebut, dateFin, tailleChoisie, note);
        });
    } // FIN ONCREATE

    private void faireLaLocation(String nomClient, String telephone, int costumeId, String dateDebut, String dateFin, String taille, String note) {
        String token = ClientAuthManager.getToken(this);
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Session expirée, veuillez vous reconnecter", Toast.LENGTH_SHORT).show();
            startActivity(new android.content.Intent(DetailActivity.this, ClientLoginActivity.class));
            return;
        }

        ApiService apiService = ApiClient.getApiService();

        LocationRequest request = new LocationRequest(
                nomClient,
                telephone,
                costumeId,
                dateDebut,
                dateFin,
                taille,
                note
        );

        apiService.louerCostume("Bearer " + token, request).enqueue(new Callback<LocationResponse>() {
            @Override
            public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().message != null ?
                            response.body().message : "Location réussie !";
                    Toast.makeText(DetailActivity.this, "✅ " + message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    ErrorHandler.handleHttpError(DetailActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<LocationResponse> call, Throwable t) {
                ErrorHandler.handleError(DetailActivity.this, t);
            }
        });
    }

    private void pickDate(EditText target) {
        android.app.DatePickerDialog dialog = new android.app.DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String monthStr = String.format("%02d", month + 1);
                    String dayStr = String.format("%02d", dayOfMonth);
                    target.setText(year + "-" + monthStr + "-" + dayStr);
                },
                java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
                java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
                java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    private boolean datesValides(String debut, String fin) {
        try {
            Date d1 = sdf.parse(debut);
            Date d2 = sdf.parse(fin);
            if (d1 == null || d2 == null) return false;
            return !d2.before(d1);
        } catch (ParseException e) {
            return false;
        }
    }
}