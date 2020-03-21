package com.example.edupedia.ui.Compare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;
import com.example.edupedia.model.School;
import com.example.edupedia.model.SchoolDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class CompareFragment extends Fragment {

    private CompareViewModel compareViewModel;
    private School[] compareList = new School[2];
    private TextView school1Name;
    private TextView school2Name;
    private TextView grade1;
    private TextView grade2;
    private TextView course1;
    private TextView course2;
    private TextView public1;
    private TextView public2;
    private TextView dist1;
    private TextView dist2;
    private TextView drive1;
    private TextView drive2;
    private HashMap<String, School> schools;
    private SchoolDB schoolDB;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
       Bundle bundle = this.getArguments();
       if (bundle!=null) {
           String value = bundle.getString("SchoolName");
           if (value != null) {
               schoolDB = new SchoolDB(getContext());
               schools = schoolDB.getValue();
               School toBeCompare = schools.get(value);
               if (toBeCompare != null) {
                   if (compareList[0] == null) {
                       compareList[0] = toBeCompare;
                   } else
                       compareList[1] = toBeCompare;
               }
           }
       }
        View layout =  inflater.inflate(R.layout.fragment_compare, container, false);
       if (compareList[0] == null & compareList[1] == null) {
                Toast toast = Toast.makeText(getActivity(), "No schools have been added to compare", Toast.LENGTH_LONG);
                toast.show();
            }

        //For School 1
        if (compareList[0] != null) {
            school1Name = layout.findViewById(R.id.school1Name);
            school1Name.setText(compareList[0].getSchoolName());

            grade1 = layout.findViewById(R.id.school1Grade);
//            grade1.setText(compareList[0].getGradeCutOff());
            grade1.setText("A");

            course1 = layout.findViewById(R.id.school1Course);
            course1.setText(compareList[0].getMainCode());

            public1 = layout.findViewById(R.id.school1Public);
//            public1.setText(Double.toString(compareList[0].getPublicTime()));
            public1.setText(Double.toString(0));

            dist1 = layout.findViewById(R.id.school1Dist);
//            dist1.setText(Double.toString(compareList[0].getDistance()));
            dist1.setText(Double.toString(0));


            drive1 = layout.findViewById(R.id.school1Driving);
//            drive1.setText(Double.toString(compareList[0].getDrivingTime()));
            drive1.setText(Double.toString(0));

        }

        //For School 2
        if (compareList[1]!=null) {
            school2Name = layout.findViewById(R.id.school2Name);
            school2Name.setText(compareList[1].getSchoolName());

            grade2 = layout.findViewById(R.id.school2Grade);
            grade2.setText(compareList[1].getGradeCutOff());

            course2 = layout.findViewById(R.id.school2Course);
            course2.setText(compareList[1].getMainCode());

            public2 = layout.findViewById(R.id.school2Public);
            public2.setText(Double.toString(compareList[1].getPublicTime()));


            dist2 = layout.findViewById(R.id.school2Dist);
            dist2.setText(Double.toString(compareList[1].getDistance()));


            drive2 = layout.findViewById(R.id.school2Driving);
            drive2.setText(Double.toString(compareList[1].getDrivingTime()));
        }

        return layout;

    }

}
