package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminClientsAdapter extends RecyclerView.Adapter<AdminClientsAdapter.VH> {
    private final List<AdminUserItem> items;

    public AdminClientsAdapter(List<AdminUserItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_client, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        AdminUserItem u = items.get(position);
        holder.name.setText(u.name != null ? u.name : ("User #" + u.id));
        holder.email.setText(u.email != null ? u.email : "");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, email;
        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvClientName);
            email = itemView.findViewById(R.id.tvClientEmail);
        }
    }
}


