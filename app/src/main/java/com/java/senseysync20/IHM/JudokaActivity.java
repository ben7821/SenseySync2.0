package com.java.senseysync20.IHM;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.java.senseysync20.DAO.CategorieDAO;
import com.java.senseysync20.DAO.CoursDAO;
import com.java.senseysync20.DAO.Cours_JudokaDAO;
import com.java.senseysync20.DAO.JudokaDAO;
import com.java.senseysync20.METIER.Categorie;
import com.java.senseysync20.METIER.Cours;
import com.java.senseysync20.METIER.Cours_Judoka;
import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class JudokaActivity extends AppCompatActivity {

    private JudokaDAO judokaDAO;
    private RecyclerView recyclerViewJudoka;
    private Cours_JudokaDAO coursJudokaDAO;
    private CoursDAO coursDAO;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_judoka);

        recyclerViewJudoka = findViewById(R.id.recyclerViewJudoka);
        recyclerViewJudoka.setLayoutManager(new LinearLayoutManager(this));

        Button btnHome = findViewById(R.id.btnHome);
        Button btncCreateJudoka = findViewById(R.id.bCreateJudoka);
        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(JudokaActivity.this, MainActivity.class);
            startActivity(intent);
        });
        judokaDAO = new JudokaDAO(this);
        coursJudokaDAO = new Cours_JudokaDAO(this);
        coursDAO = new CoursDAO(this);
        judokaDAO.open();
        coursJudokaDAO.open();
        coursDAO.open();

        ArrayList<Judoka> judokas = judokaDAO.read();

        JudokaAdapter adapter = new JudokaAdapter(judokas);
        recyclerViewJudoka.setAdapter(adapter);

      btncCreateJudoka.setOnClickListener(v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(JudokaActivity.this);
        builder.setTitle("Create new Judoka");

        // Create a layout for the dialog
        LinearLayout layout = new LinearLayout(JudokaActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Create input fields for the Judoka details

        final EditText nameInput = new EditText(JudokaActivity.this);
        nameInput.setHint("Name");
        layout.addView(nameInput);

        final EditText prenomInput = new EditText(JudokaActivity.this);
        prenomInput.setHint("Prenom");
        layout.addView(prenomInput);

        final EditText telInput = new EditText(JudokaActivity.this);
        telInput.setHint("Tel");
        layout.addView(telInput);

        final EditText dateNaissanceInput = new EditText(JudokaActivity.this);
        dateNaissanceInput.setHint("Date de naissance (yyyy-mm-dd)");
        layout.addView(dateNaissanceInput);

        // Create a spinner for the categories
        Spinner categorieSpinner = new Spinner(JudokaActivity.this);
        layout.addView(categorieSpinner);

        // Get all categories
        CategorieDAO categorieDAO = new CategorieDAO(JudokaActivity.this);
        ArrayList<Categorie> allCategories = categorieDAO.read();
        // Create an ArrayAdapter using the categories list
        ArrayAdapter<Categorie> adapterC = new ArrayAdapter<>(JudokaActivity.this, android.R.layout.simple_spinner_item, allCategories);
        adapterC.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        categorieSpinner.setAdapter(adapterC);

        // Add the layout to the dialog
        builder.setView(layout);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String name = nameInput.getText().toString();
            String prenom = prenomInput.getText().toString();
            String tel = telInput.getText().toString();
            String dateNaissance = dateNaissanceInput.getText().toString();

            // Create a new Judoka with the entered details
            Judoka newJudoka = new Judoka();
            newJudoka.setNom(name);
            newJudoka.setPrenom(prenom);
            newJudoka.setTel(tel);
            newJudoka.setDateNaissance(LocalDate.parse(dateNaissance));
            Categorie selectedCategory = (Categorie) categorieSpinner.getSelectedItem();
            newJudoka.setCat(selectedCategory);

            // Add the new Judoka to the database
            judokaDAO.insert(newJudoka);

            // Update the RecyclerView
            updateRecyclerView();
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();
    });
    }

    // Implement the updateRecyclerView method here
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private void updateRecyclerView() {
        // Get the updated list of judokas from the database
        ArrayList<Judoka> updatedJudokas = judokaDAO.read();

        // Create a new adapter with the updated list
        JudokaAdapter updatedAdapter = new JudokaAdapter(updatedJudokas);

        // Set the new adapter on the RecyclerView
        recyclerViewJudoka.setAdapter(updatedAdapter);

        // Notify the adapter that the data set has changed
        updatedAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showJudokaOptionsDialog(Judoka judoka) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options for " + judoka.getNom());

        builder.setPositiveButton("Delete", (dialog, which) -> {
            // Affichez un autre AlertDialog pour confirmer la suppression
            showDeleteConfirmationDialog(judoka);
        });

        builder.setNegativeButton("Show Courses", (dialog, which) -> {
            ArrayList<Cours_Judoka> courses = coursJudokaDAO.read(judoka.getIdjudoka());
            StringBuilder coursesString = new StringBuilder();
            CoursDAO coursDAO = new CoursDAO(this);
         for (Cours_Judoka course : courses) {
                Cours cours = coursDAO.read(course.getIdCours());
                String presence = course.isPresence().equals("P") ? "Présent" : "Absent";
                coursesString.append(cours.getNom()).append(" - ").append(presence).append(" ce jour du ").append(cours.getDate()).append("\n");
            }
            // Show the list of courses in a new AlertDialog
            AlertDialog.Builder coursesDialog = new AlertDialog.Builder(this);
            coursesDialog.setTitle(judoka.getNom() + "'s Courses");
            coursesDialog.setMessage(coursesString.toString());
            coursesDialog.show();
        });

        builder.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showDeleteConfirmationDialog(Judoka judoka) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Judoka" + " " + judoka.getNom());
        builder.setMessage("Are you sure you want to delete this judoka?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            judokaDAO.delete(judoka);
            updateRecyclerView();
        });

        builder.setNegativeButton("No", null);

        builder.show();
    }
}