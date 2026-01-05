package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminClientAdapter extends RecyclerView.Adapter<AdminClientAdapter.VH> {
    public interface Listener {
        void onEdit(UserItem user);
        void onDelete(UserItem user);
    }

    private final Context context;
    private final List<UserItem> items;
    private final Listener listener;

    public AdminClientAdapter(Context context, List<UserItem> items, Listener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_admin_client, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        UserItem u = items.get(position);
        h.name.setText("#" + u.id + " - " + (u.name != null ? u.name : ""));
        h.email.setText(u.email != null ? u.email : "");
        
        if (h.btnEdit != null) {
            h.btnEdit.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onEdit(u);
                }
            });
        }
        
        if (h.btnDelete != null) {
            h.btnDelete.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onDelete(u);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView name, email, btnEdit, btnDelete;
        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvClientName);
            email = itemView.findViewById(R.id.tvClientEmail);
            btnEdit = itemView.findViewById(R.id.btnEditClient);
            btnDelete = itemView.findViewById(R.id.btnDeleteClient);
        }
    }
}


