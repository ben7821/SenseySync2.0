package com.java.senseysync20.IHM;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.java.senseysync20.DAO.Cours_JudokaDAO;
import com.java.senseysync20.DAO.JudokaDAO;
import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity {
    private ListView listViewStudents;
    private StudentListAdapter adapter;
    private Cours_JudokaDAO coursJudokaDAO;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        listViewStudents = findViewById(R.id.listViewStudents);

        coursJudokaDAO = new Cours_JudokaDAO(this);
        adapter = new StudentListAdapter(this, new ArrayList<Judoka>(), -1, new Cours_JudokaDAO(this));
        ListView listViewStudents = findViewById(R.id.listViewStudents);
        listViewStudents.setAdapter(adapter);



        int courseId = getIntent().getIntExtra("COURSE_ID", -1);
        if (courseId != -1) {
            JudokaDAO JudokaDAO = new JudokaDAO(this);
            Cours_JudokaDAO coursJudokaDAO = new Cours_JudokaDAO(this);
            JudokaDAO.open();
            coursJudokaDAO.open();
            ArrayList<Judoka> students = JudokaDAO.getStudents(courseId);
            adapter = new StudentListAdapter(this, students, courseId, coursJudokaDAO);
            listViewStudents.setAdapter(adapter);
            JudokaDAO.close();
        }

        Button validateButton = findViewById(R.id.validateButton);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursJudokaDAO.open();
                adapter.validate();
                coursJudokaDAO.close();
            }
        });
    }
}