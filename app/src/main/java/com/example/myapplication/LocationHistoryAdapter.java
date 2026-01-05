package com.example.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationHistoryAdapter extends RecyclerView.Adapter<LocationHistoryAdapter.LocationViewHolder> {

    private final Context context;
    private final List<LocationItem> locations;

    public LocationHistoryAdapter(Context context, List<LocationItem> locations) {
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationItem loc = locations.get(position);
        holder.tvNomCostume.setText(loc.nom_costume != null ? loc.nom_costume : "Costume");
        String dates = (loc.date_debut != null ? loc.date_debut : "?") + " - " + (loc.date_fin != null ? loc.date_fin : "?");
        holder.tvDates.setText(dates);
        
        // Appliquer le statut avec le bon badge
        String statut = loc.statut != null ? loc.statut : "en_attente";
        holder.tvStatut.setText(statut);
        
        // Changer le background selon le statut
        Drawable badgeDrawable;
        switch (statut.toLowerCase()) {
            case "confirmée":
            case "confirmee":
                badgeDrawable = ContextCompat.getDrawable(context, R.drawable.status_badge_confirmee);
                break;
            case "annulée":
            case "annulee":
                badgeDrawable = ContextCompat.getDrawable(context, R.drawable.status_badge_annulee);
                break;
            case "terminée":
            case "terminee":
                badgeDrawable = ContextCompat.getDrawable(context, R.drawable.status_badge_terminee);
                break;
            default: // en_attente
                badgeDrawable = ContextCompat.getDrawable(context, R.drawable.status_badge_en_attente);
                break;
        }
        holder.tvStatut.setBackground(badgeDrawable);

        if (loc.note != null && !loc.note.isEmpty()) {
            holder.tvNote.setVisibility(View.VISIBLE);
            holder.tvNote.setText(loc.note);
        } else {
            holder.tvNote.setVisibility(View.GONE);
        }

        // Prix total (si dispo)
        if (loc.prix_total != null) {
            holder.tvPrix.setVisibility(View.VISIBLE);
            holder.tvPrix.setText("Prix : " + String.format(java.util.Locale.US, "%.2f", loc.prix_total) + " DH");
        } else {
            holder.tvPrix.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomCostume, tvDates, tvStatut, tvNote, tvPrix;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomCostume = itemView.findViewById(R.id.tvNomCostumeLoc);
            tvDates = itemView.findViewById(R.id.tvDatesLoc);
            tvStatut = itemView.findViewById(R.id.tvStatutLoc);
            tvNote = itemView.findViewById(R.id.tvNoteLoc);
            tvPrix = itemView.findViewById(R.id.tvPrixLoc);
        }
    }
}

