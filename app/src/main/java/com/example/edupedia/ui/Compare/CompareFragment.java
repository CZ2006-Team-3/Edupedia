package com.example.edupedia.ui.Compare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edupedia.R;
import com.example.edupedia.model.School;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View layout =  inflater.inflate(R.layout.fragment_compare, container, false);
        //For School 1
        school1Name = layout.findViewById(R.id.school1Name);
        school1Name.setText(compareList[1].getSchoolName());

        grade1 = layout.findViewById(R.id.school1Grade);
        grade1.setText(compareList[1].getGradeCutOff());

        course1 = layout.findViewById(R.id.school1Course);
        course1.setText(compareList[1].getMainCode());

        public1 = layout.findViewById(R.id.school1Public);
        public1.setText(Double.toString(compareList[1].getPublicTime()));

        dist1 = layout.findViewById(R.id.school1Dist);
        dist1.setText(Double.toString(compareList[1].getDistance()));


        drive1 = layout.findViewById(R.id.school1Driving);
        drive1.setText(Double.toString(compareList[1].getDrivingTime()));

        //For School 2
        school2Name = layout.findViewById(R.id.school2Name);
        school2Name.setText(compareList[2].getSchoolName());

        grade2 = layout.findViewById(R.id.school2Grade);
        grade2.setText(compareList[2].getGradeCutOff());


        course2 = layout.findViewById(R.id.school2Course);
        course2.setText(compareList[2].getMainCode());


        public2 = layout.findViewById(R.id.school2Public);
        public2.setText(Double.toString(compareList[2].getPublicTime()));


        dist2 = layout.findViewById(R.id.school2Dist);
        dist2.setText(Double.toString(compareList[2].getDistance()));


        drive2 = layout.findViewById(R.id.school2Driving);
        drive2.setText(Double.toString(compareList[2].getDrivingTime()));

        return layout;

    }

}
