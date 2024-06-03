package com.java.senseysync20.IHM;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.java.senseysync20.DAO.CategorieDAO;
import com.java.senseysync20.DAO.JudokaDAO;
import com.java.senseysync20.METIER.Categorie;
import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.util.ArrayList;

public class CategorieActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCategorie;
    private CategorieDAO categorieDAO;
    private ArrayList<Categorie> categories;
    private JudokaDAO judokaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);
        Button btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(CategorieActivity.this, MainActivity.class);
            startActivity(intent);
        });

        recyclerViewCategorie = findViewById(R.id.recyclerViewCategorie);
        recyclerViewCategorie.setLayoutManager(new LinearLayoutManager(this));

        categorieDAO = new CategorieDAO(this);
        judokaDAO = new JudokaDAO(this);
        categories = categorieDAO.read();

        CategorieAdapter adapter = new CategorieAdapter(categories);
        recyclerViewCategorie.setAdapter(adapter);

        recyclerViewCategorie.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewCategorie ,new RecyclerItemClickListener.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override public void onItemClick(View view, int position) {
                        showJudokaListeCat(categories.get(position));
                    }

                    @Override public void onLongItemClick(View view, int position) {
                    }

                })
        );
    }


    // show list of judokas in the selected category
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void showJudokaListeCat(Categorie categorie) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Liste des Judokas dans la catègorie " + categorie.getLibelle());

        // Get all judokas in the selected category
        ArrayList<Judoka> judokas = judokaDAO.allCatJudoka(categorie.getIdCategorie());

        // Add Show options for the selected judoka
        ArrayAdapter<Judoka> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, judokas);
        builder.setAdapter(adapter, (dialog, which) -> showJudokaOptions(judokas.get(which)));
        builder.show();
    }


    // show options for the selected judoka
    private void showJudokaOptions(Judoka judoka) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options for " + judoka.getNom());

        Spinner spinner = new Spinner(this);

        // Get all categories
        ArrayList<Categorie> allCategories = categorieDAO.read();
        ArrayAdapter<Categorie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allCategories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set the current category as the selected item in the spinner
        int currentCategoryId = judoka.getCat().getIdCategorie();
        for (int i = 0; i < allCategories.size(); i++) {
            if (allCategories.get(i).getIdCategorie() == currentCategoryId) {
                spinner.setSelection(i);
                break;
            }
        }
        builder.setView(spinner);

        // Change the category of the judoka
        builder.setPositiveButton("Categorie (X)", (dialog, which) -> {
            Categorie selectedCategory = (Categorie) spinner.getSelectedItem();
            // Change the category of the judoka
            judoka.setCat(selectedCategory);
            judokaDAO.update(judoka);
            Log.d("Judoka", "Judoka updated successfully" + judoka);


        });

        builder.show();
    }

}