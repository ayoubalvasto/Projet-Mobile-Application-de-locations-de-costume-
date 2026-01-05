package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminReservationAdapter extends RecyclerView.Adapter<AdminReservationAdapter.VH> {

    public interface Listener {
        void onUpdateStatus(AdminReservation reservation, String newStatus);
    }

    private final Context context;
    private final List<AdminReservation> items;
    private final Listener listener;
    private final String[] statuses = new String[]{"en_attente", "confirmée", "annulée", "terminée"};

    public AdminReservationAdapter(Context context, List<AdminReservation> items, Listener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin_reservation, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        AdminReservation r = items.get(position);
        String costumeName = (r.costume != null && r.costume.nom != null) ? r.costume.nom : r.nom_costume;
        h.title.setText("#" + r.id + " - " + (costumeName != null ? costumeName : ""));
        h.dates.setText((r.date_debut != null ? r.date_debut : "?") + " → " + (r.date_fin != null ? r.date_fin : "?"));
        String client = r.nom_client != null ? r.nom_client : "";
        if (r.user != null && r.user.email != null) client += " (" + r.user.email + ")";
        h.client.setText(client);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, statuses);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        h.spinner.setAdapter(adapter);

        // Set selection
        int idx = 0;
        for (int i = 0; i < statuses.length; i++) {
            if (statuses[i].equals(r.statut)) { idx = i; break; }
        }
        h.spinner.setSelection(idx);

        h.btnUpdate.setOnClickListener(v -> {
            String selected = (String) h.spinner.getSelectedItem();
            listener.onUpdateStatus(r, selected);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, dates, client;
        Spinner spinner;
        Button btnUpdate;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvResTitle);
            dates = itemView.findViewById(R.id.tvResDates);
            client = itemView.findViewById(R.id.tvResClient);
            spinner = itemView.findViewById(R.id.spStatus);
            btnUpdate = itemView.findViewById(R.id.btnUpdateStatus);
        }
    }
}


