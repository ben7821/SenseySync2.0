package com.java.senseysync20.IHM;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.java.senseysync20.METIER.Cours;
import com.java.senseysync20.R;

import java.util.ArrayList;

public class CoursAdapter extends ArrayAdapter<Cours> {
    public CoursAdapter(Context context, ArrayList<Cours> courses) {
        super(context, 0, courses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cours course = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cours, parent, false);
        }

        TextView coursId = convertView.findViewById(R.id.courseId);
        TextView coursDate = convertView.findViewById(R.id.date);
        TextView coursNom = convertView.findViewById(R.id.nom);

        coursId.setText(String.valueOf(course.getIdCour()));
        coursDate.setText(course.getDate().toString());
        coursNom.setText(course.getNom());

        return convertView;
    }
}