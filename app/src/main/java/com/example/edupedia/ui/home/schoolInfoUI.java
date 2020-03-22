package com.example.edupedia.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.edupedia.R;
import com.example.edupedia.model.School;

public class schoolInfoUI extends Activity implements View.OnClickListener {
    private TextView schoolName;
    private TextView grade;
    private TextView course;
    private TextView publicTime;
    private TextView dist;
    private TextView drive;
    private ImageView schoolLogo;
    private ImageView courseLogo;
    private ImageView gradeLogo;
    private ImageView drivingLogo;
    private ImageView distanceLogo;
    private ImageView trainLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_info_page);
        Bundle extras = getIntent().getExtras();

        schoolName = findViewById(R.id.schoolName);
        course = findViewById(R.id.courseName);
        grade = findViewById(R.id.gradeCut);
        drive = findViewById(R.id.drivingTime);
        dist = findViewById(R.id.distance);
        publicTime = findViewById(R.id.publicTransportTiming);
        schoolLogo = findViewById(R.id.schoolIcon);
        courseLogo = findViewById(R.id.courseIcon);
        gradeLogo = findViewById(R.id.gradeIcon);
        drivingLogo = findViewById(R.id.drivingIcon);
        distanceLogo = findViewById(R.id.distanceIcon);
        trainLogo = findViewById(R.id.trainIcon);

        schoolLogo.setImageResource(R.drawable.school);
        courseLogo.setImageResource(R.drawable.course);
        gradeLogo.setImageResource(R.drawable.grade);
        drivingLogo.setImageResource(R.drawable.driving_time);
        distanceLogo.setImageResource(R.drawable.distance);
        trainLogo.setImageResource(R.drawable.public_transport);


        setData(extras);
        }

        public void setData(Bundle extras){
            schoolName.setText(extras.getString("schoolName"));
            course.setText(extras.getString("course"));
            if (Integer.toString(extras.getInt("grade")) == "-1"){
                grade.setText("Not Applicable");
            }
            else {
                grade.setText(Integer.toString(extras.getInt("grade")));
            }
            drive.setText(Double.toString(extras.getDouble("drive")));
            dist.setText(Double.toString(extras.getDouble("dist")));
            publicTime.setText(Double.toString(extras.getDouble("publicTime")));
        }

    @Override
    public void onClick(View v) {

    }
}
