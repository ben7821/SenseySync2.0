package com.java.senseysync20.IHM;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.java.senseysync20.DAO.Cours_JudokaDAO;
import com.java.senseysync20.METIER.Cours_Judoka;
import com.java.senseysync20.METIER.Judoka;
import com.java.senseysync20.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentListAdapter extends ArrayAdapter<Judoka> {
    private int courseId;
    private Cours_JudokaDAO coursJudokaDAO;
    private HashMap<Integer, String> tempStatuses = new HashMap<>();


    public StudentListAdapter(Context context, ArrayList<Judoka> students, int courseId, Cours_JudokaDAO coursJudokaDAO) {
        super(context, 0, students);
        this.courseId = courseId;
        this.coursJudokaDAO = coursJudokaDAO;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Judoka student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_student_list, parent, false);
        }

        TextView judokaName = convertView.findViewById(R.id.judokaName);
        CheckBox absentCheckBox = convertView.findViewById(R.id.absentCheckBox);
        CheckBox presentCheckBox = convertView.findViewById(R.id.presentCheckBox);

        judokaName.setText(student.getNom() + " " + student.getPrenom() + " ---> " + student.getCat().getLibelle());

        absentCheckBox.setOnCheckedChangeListener(null);
        presentCheckBox.setOnCheckedChangeListener(null);

        Cours_Judoka coursJudoka = coursJudokaDAO.read(student.getIdjudoka(), courseId);
        if (coursJudoka != null) {
            String presence = coursJudoka.isPresence();
            if ("P".equals(presence)) {
                presentCheckBox.setChecked(true);
            } else if ("A".equals(presence)) {
                absentCheckBox.setChecked(true);
            }
        }

        absentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    presentCheckBox.setChecked(false);
                    tempStatuses.put(student.getIdjudoka(), "A");
                }
            }
        });

        presentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    absentCheckBox.setChecked(false);
                    tempStatuses.put(student.getIdjudoka(), "P");
                }
            }
        });

        return convertView;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void validate() {
        coursJudokaDAO.open();
        for (Map.Entry<Integer, String> entry : tempStatuses.entrySet()) {
            int idJudoka = entry.getKey();
            String presence = entry.getValue();
            Log.d("VALIDATE", "" + idJudoka + " " + courseId + presence);
            Cours_Judoka coursJudoka = coursJudokaDAO.read(idJudoka, courseId);
            if (coursJudoka != null) {
                coursJudokaDAO.update(new Cours_Judoka(idJudoka, courseId, presence));
                Log.d("UPDATE", "Updated presence for judoka " + idJudoka + " in course " + courseId + " to " + presence);
            } else {
                try {
                    coursJudokaDAO.insert(new Cours_Judoka(idJudoka, courseId, presence));
                    Log.d("INSERT", "Inserted presence for judoka " + idJudoka + " in course " + courseId + " to " + presence);
                } catch (SQLiteConstraintException e) {
                    Log.e("VALIDATE", "Failed to insert presence for judoka " + idJudoka + " in course " + courseId, e);
                }
            }
        }
        coursJudokaDAO.read();
        tempStatuses.clear();
        Log.d("VALIDATE", "Validation completed");
    }

}