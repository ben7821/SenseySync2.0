package com.java.senseysync20.IHM;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.java.senseysync20.METIER.Categorie;
import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.util.ArrayList;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.CategorieViewHolder> {

    private ArrayList<Categorie> categories;

    public CategorieAdapter(ArrayList<Categorie> categories) {
        this.categories = categories;
    }

    @Override
    public CategorieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categorie, parent, false);
        return new CategorieViewHolder(view);
    }

   @Override
    public void onBindViewHolder(@NonNull CategorieViewHolder holder, int position) {
        Categorie currentCategory = categories.get(position);
        holder.textViewLibelle.setText(currentCategory.getLibelle());
        holder.textViewNumberJudoka.setText(String.valueOf(currentCategory.getNumberJudoka()));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategorieViewHolder extends RecyclerView.ViewHolder {

        TextView textViewLibelle;
        TextView textViewNumberJudoka;


        public CategorieViewHolder(View itemView) {
            super(itemView);
            textViewLibelle = itemView.findViewById(R.id.textViewLibelle);
            textViewNumberJudoka = itemView.findViewById(R.id.textViewNumberJudoka);

        }
    }
}
