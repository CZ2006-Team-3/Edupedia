package com.example.edupedia.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.edupedia.R;
import com.example.edupedia.model.School;

public class schoolInfoUI {
    private TextView schoolName;
    private TextView grade;
    private TextView course;
    private TextView publicTime;
    private TextView dist;
    private TextView drive;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View layout =  inflater.inflate(R.layout.school_info_page, container, false);

        schoolName = layout.findViewById(R.id.schoolName);
        course = layout.findViewById(R.id.courseName);
        grade = layout.findViewById(R.id.gradeCut);
        drive = layout.findViewById(R.id.drivingTime);
        dist = layout.findViewById(R.id.distance);
        publicTime = layout.findViewById(R.id.publicTransportTiming);

        return layout;
        }

        public void setData(School schoolInfoNeeded){
            schoolName.setText(schoolInfoNeeded.getSchoolName());
            course.setText(schoolInfoNeeded.getMainCode());
            grade.setText(Double.toString(schoolInfoNeeded.getGradeCutOff()));
            drive.setText(Double.toString(schoolInfoNeeded.getDrivingTime()));
            dist.setText(Double.toString(schoolInfoNeeded.getDistance()));
            publicTime.setText(Double.toString(schoolInfoNeeded.getPublicTime()));
        }


}
