package com.java.senseysync20.IHM;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.util.ArrayList;

public class JudokaAdapter extends RecyclerView.Adapter<JudokaAdapter.JudokaViewHolder> {

    private static ArrayList<Judoka> judokas;

    public JudokaAdapter(ArrayList<Judoka> judokas) {
        this.judokas = judokas;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public JudokaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_judoka, parent, false);
        return new JudokaViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(JudokaViewHolder holder, int position) {
        Judoka judoka = judokas.get(position);
        holder.textViewNom.setText(judoka.getNom());
        holder.textViewPrenom.setText(judoka.getPrenom());
        holder.textViewTel.setText(judoka.getTel());
        holder.textViewCategorie.setText(judoka.getCat().getLibelle());
        holder.textViewDateNaissance.setText(judoka.getDateNaissance() + "");
    }

    @Override
    public int getItemCount() {
        return judokas.size();
    }

    public static class JudokaViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNom;
        TextView textViewPrenom;
        TextView textViewTel;
        TextView textViewCategorie;
        TextView textViewDateNaissance;

        @RequiresApi(api = Build.VERSION_CODES.O)
        public JudokaViewHolder(View itemView) {
            super(itemView);
            textViewNom = itemView.findViewById(R.id.textViewNom);
            textViewPrenom = itemView.findViewById(R.id.textViewPrenom);
            textViewTel = itemView.findViewById(R.id.textViewTel);
            textViewDateNaissance = itemView.findViewById(R.id.textViewDateNaissance);
            textViewCategorie = itemView.findViewById(R.id.textViewCategorie);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    ((JudokaActivity) v.getContext()).showJudokaOptionsDialog(judokas.get(position));
                }
            });
        }
    }
}