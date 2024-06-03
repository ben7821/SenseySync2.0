package com.java.senseysync20.IHM;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.java.senseysync20.DAO.CoursDAO;
import com.java.senseysync20.METIER.Cours;
import com.java.senseysync20.R;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private Button bQuitter;
    private Button bCategorie;
    private Button bJudoka;
    private ListView listViewCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        constructeurGraphique();

        bCategorie.setOnClickListener(buttonClickListener);
        bJudoka.setOnClickListener(buttonClickListener);
        bQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitter();
            }
        });


        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                // Convert the selected date to LocalDate format
                LocalDate localDate = selectedDate.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Get the list of courses for the selected date
                CoursDAO coursDAO = new CoursDAO(MainActivity.this);
                ArrayList<Cours> courses = coursDAO.read(localDate);

                CoursAdapter adapter = new CoursAdapter(MainActivity.this, courses);
                listViewCours.setAdapter(adapter);

                listViewCours.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cours selectedCourse = (Cours) parent.getItemAtPosition(position);
                        Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                        intent.putExtra("COURSE_ID", selectedCourse.getIdCour());
                        startActivity(intent);
                    }
                });
            }

        });

    }

    private void constructeurGraphique(){
        bQuitter = (Button) findViewById(R.id.bQuitter);
        bCategorie = (Button) findViewById(R.id.bCategorie);
        bJudoka = (Button) findViewById(R.id.bJudoka);
        listViewCours = findViewById(R.id.listViewCours);
        bCategorie.setTag("Categorie");
        bJudoka.setTag("Judoka");

    }


    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tag = (String) v.getTag();
            switch (tag) {
                case "Categorie":
                    openActivity(CategorieActivity.class);
                    break;
                case "Judoka":
                    openActivity(JudokaActivity.class);
                    break;
            }
        }
    };

    private void quitter(){
        finish();
    }

    private void openActivity(Class<?> cls){
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}