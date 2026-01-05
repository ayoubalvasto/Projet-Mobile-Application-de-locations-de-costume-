package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminCostumeAdapter extends RecyclerView.Adapter<AdminCostumeAdapter.VH> {

    public interface Listener {
        void onEdit(Costume costume);
        void onDelete(Costume costume);
    }

    private final Context context;
    private final List<Costume> items;
    private final Listener listener;

    public AdminCostumeAdapter(Context context, List<Costume> items, Listener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin_costume, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        Costume c = items.get(position);
        h.title.setText("#" + c.id + " - " + (c.nom != null ? c.nom : ""));
        h.sub.setText("Taille: " + (c.taille != null ? c.taille : "") + " | Prix: " + c.prix + " DH");
        h.btnEdit.setOnClickListener(v -> listener.onEdit(c));
        h.btnDelete.setOnClickListener(v -> listener.onDelete(c));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title, sub;
        Button btnEdit, btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvAdminCostumeTitle);
            sub = itemView.findViewById(R.id.tvAdminCostumeSub);
            btnEdit = itemView.findViewById(R.id.btnAdminEdit);
            btnDelete = itemView.findViewById(R.id.btnAdminDelete);
        }
    }
}


