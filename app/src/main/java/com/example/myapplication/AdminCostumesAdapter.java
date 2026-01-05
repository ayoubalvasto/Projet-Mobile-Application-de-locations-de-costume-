package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminCostumesAdapter extends RecyclerView.Adapter<AdminCostumesAdapter.VH> {

    public interface Listener {
        void onEdit(Costume c);
        void onDelete(Costume c);
    }

    private final List<Costume> items;
    private final Listener listener;

    public AdminCostumesAdapter(List<Costume> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_costume, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Costume c = items.get(position);
        holder.title.setText("#" + c.id + " - " + (c.nom != null ? c.nom : ""));
        holder.sub.setText((c.taille != null ? c.taille : "") + " • " + c.prix + " DH");

        holder.itemView.setOnClickListener(v -> listener.onEdit(c));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onDelete(c);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, sub;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvAdminCostumeTitle);
            sub = itemView.findViewById(R.id.tvAdminCostumeSub);
        }
    }
}


