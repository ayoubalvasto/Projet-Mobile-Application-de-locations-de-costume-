package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerHistory;
    private TextView tvHistoryTitle;
    private final List<LocationItem> locations = new ArrayList<>();
    private LocationHistoryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerHistory = findViewById(R.id.recyclerHistory);
        tvHistoryTitle = findViewById(R.id.tvHistoryTitle);

        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        adapter = new LocationHistoryAdapter(this, locations);
        recyclerHistory.setAdapter(adapter);

        loadHistory();
    }

    private void loadHistory() {
        ApiService apiService = ApiClient.getApiService();
        Call<LocationListResponse> call;

        if (ClientAuthManager.isLoggedIn(this)) {
            String token = ClientAuthManager.getToken(this);
            call = apiService.getMyLocations("Bearer " + token);
        } else {
            call = apiService.getLocations();
        }

        call.enqueue(new Callback<LocationListResponse>() {
            @Override
            public void onResponse(Call<LocationListResponse> call, Response<LocationListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    locations.clear();
                    if (response.body().data != null) {
                        locations.addAll(response.body().data);
                    }
                    adapter.notifyDataSetChanged();

                    if (locations.isEmpty()) {
                        tvHistoryTitle.setText(getString(R.string.aucune_location));
                    }
                } else {
                    ErrorHandler.handleHttpError(HistoryActivity.this, response);
                }
            }

            @Override
            public void onFailure(Call<LocationListResponse> call, Throwable t) {
                ErrorHandler.handleError(HistoryActivity.this, t);
            }
        });
    }
}

