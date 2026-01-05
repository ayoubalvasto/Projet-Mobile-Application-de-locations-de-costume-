package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class CostumeAdapter extends RecyclerView.Adapter<CostumeAdapter.CostumeViewHolder> {

    private final Context context;
    private final List<Costume> costumeList;

    public CostumeAdapter(Context context, List<Costume> costumeList) {
        this.context = context;
        this.costumeList = costumeList;
    }

    @NonNull
    @Override
    public CostumeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_costume, parent, false);
        return new CostumeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CostumeViewHolder holder, int position) {
        Costume costume = costumeList.get(position);

        // On remplit le texte
        holder.tvNom.setText(costume.nom);
        holder.tvPrix.setText(costume.prix + " DH");
        holder.tvTaille.setText("Taille : " + costume.taille);

        // On charge l'image avec Glide
        Glide.with(context)
                .load(costume.image)
                .placeholder(android.R.drawable.ic_menu_gallery) // Image d'attente
                .into(holder.imgCostume);

        // --- GESTION DU CLIC ---
        holder.itemView.setOnClickListener(v -> {
            // On prépare le voyage vers la page de détail
            Intent intent = new Intent(context, DetailActivity.class);

            // On met les valises (les données) dans le coffre
            intent.putExtra("id", costume.id);
            intent.putExtra("nom", costume.nom);
            intent.putExtra("prix", String.valueOf(costume.prix));
            intent.putExtra("taille", costume.taille);
            intent.putExtra("image", costume.image);

            // On démarre !
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return costumeList.size();
    }

    public static class CostumeViewHolder extends RecyclerView.ViewHolder {
        TextView tvNom, tvPrix, tvTaille;
        ImageView imgCostume;

        public CostumeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNom = itemView.findViewById(R.id.tvNom); // Assurez-vous que les ID correspondent à votre layout item_costume.xml
            tvPrix = itemView.findViewById(R.id.tvPrix);
            tvTaille = itemView.findViewById(R.id.tvTaille);
            imgCostume = itemView.findViewById(R.id.imgCostume);
        }
    }
}
