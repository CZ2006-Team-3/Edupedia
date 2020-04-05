package com.example.edupedia.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.edupedia.R;
import com.example.edupedia.controller.SearchController;
import com.example.edupedia.controller.StatisticsController;
import com.example.edupedia.model.School;


/**
 * school Info UI class for implementing information page about schools
 */
public class schoolInfoUI extends AppCompatActivity {
    private TextView schoolName;
    private TextView grade;
    private TextView course;
    private TextView publicTime;
    private TextView dist;
    private TextView drive;
    private TextView url;
    private TextView watchCount;
    private ImageView schoolLogo;
    private ImageView courseLogo;
    private ImageView gradeLogo;
    private ImageView drivingLogo;
    private ImageView distanceLogo;
    private ImageView trainLogo;
    private ImageView urlLogo;
    private ImageView watchlistLogo;


    /**
     * default method that occurs upon the creation of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_info_page);
        StatisticsController viewmodel = new ViewModelProvider(this).get(StatisticsController.class);
        Bundle extras = getIntent().getExtras();
        viewmodel.subscribeUserDB(extras.getString("schoolName"));


        schoolName = findViewById(R.id.schoolName);
        course = findViewById(R.id.courseName);
        grade = findViewById(R.id.gradeCut);
        drive = findViewById(R.id.drivingTime);
        dist = findViewById(R.id.distance);
        publicTime = findViewById(R.id.publicTransportTiming);
        url = findViewById(R.id.url_display);
        watchCount = findViewById(R.id.watchCount);
        schoolLogo = findViewById(R.id.schoolIcon);
        courseLogo = findViewById(R.id.courseIcon);
        gradeLogo = findViewById(R.id.gradeIcon);
        drivingLogo = findViewById(R.id.drivingIcon);
        distanceLogo = findViewById(R.id.distanceIcon);
        trainLogo = findViewById(R.id.trainIcon);
        urlLogo = findViewById(R.id.web2);
        watchlistLogo = findViewById(R.id.watchListLogo);
        schoolLogo.setImageResource(R.drawable.school);
        courseLogo.setImageResource(R.drawable.course);
        gradeLogo.setImageResource(R.drawable.grade);
        drivingLogo.setImageResource(R.drawable.driving_time);
        distanceLogo.setImageResource(R.drawable.distance);
        trainLogo.setImageResource(R.drawable.public_transport);
        urlLogo.setImageResource(R.drawable.link);
        watchlistLogo.setImageResource(R.drawable.fire);
        LiveData<Integer> watchcount = viewmodel.getWatchCount();
        watchcount.observe(this, new Observer<Integer>(){

            @Override
            public void onChanged(Integer i) {
                watchCount.setText("Number of people looking at this school : " + String.valueOf(i));
            }

        });

        setData(extras);
    }

    /**
     *  method that sets data to the various textView elements
     */
    public void setData(Bundle extras) {
        schoolName.setText(extras.getString("schoolName"));
        course.setText("Course Name: " + extras.getString("course"));
//        if (Integer.toString(extras.getInt("grade")).equals("0")) {
//            grade.setText("Not Applicable");
//        } else {
        grade.setText("Grade Cut-Off: " + extras.getString("grade"));
        drive.setText("Time taken to drive: " + String.format("%.2f", extras.getDouble("drive")) + " min");
        dist.setText("Distance: " + String.format("%.2f", extras.getDouble("dist")) + " km");
        publicTime.setText("Time to travel in Public Transport: " + String.format("%.2f", extras.getDouble("publicTime")) + " min");
        url.setText(extras.getString("url"));
    }

}