package com.example.edupedia.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.edupedia.controller.WatchlistController;
import com.example.edupedia.ui.StartUI;
import com.example.edupedia.ui.home.HomeFragment;
import com.example.edupedia.model.School;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;

public class SchoolItem {
    private int mImageResource;
    private String schoolName;
    private String distaceInfo;
    private String gradeCutOff;
    private String publicTransport;
    private String drivingTime;
//    private SharedPreferences sharedPref = this.getActivity().getPreferences(MODE_PRIVATE);
//    private String uid = sharedPref.getString(StartUI.firebase_key,"");
//
    private WatchlistController watchlistController = WatchlistController.getInstance();

    public SchoolItem(int ImageResource, String SchoolName, String GradecutOff, String Distance_Calculated){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distaceInfo = Distance_Calculated;
        gradeCutOff = GradecutOff;

    }

    public SchoolItem(int ImageResource, String SchoolName, String gradecutOff, String Distance_Calculated, String publicTiming, String driving_time ){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distaceInfo = Distance_Calculated;
        gradeCutOff = gradecutOff;
        publicTransport = publicTiming;
        drivingTime = driving_time;
    }


    public int getmImageResource() {
        return mImageResource;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getGradeCutOff() {
        return gradeCutOff;
    }

    public String getDistanceInfo() {
        return distaceInfo;
    }

    public String getPublicTransport() { return publicTransport; }

    public String getDrivingTime() { return drivingTime; }

    //    public static Comparator<SchoolItem> DrivingTimeComparator = new Comparator<SchoolItem>() {
//        @Override
//        public int compare(SchoolItem schoolItem, SchoolItem schoolItem2) {
//            return Double.compare(schoolItem.getDrivingTime(), school2.getDrivingTime());
//        }
//    };
//
//    public static Comparator<School> PublicTransportTimeComparator = new Comparator<School>() {
//        @Override
//        public int compare(School school, School school2) {
//            return Double.compare(school.getPublicTime(), school2.getPublicTime());
//        }
//    };

    public static Comparator<SchoolItem> DistanceComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem school2) {
            float schoolDistance = Float.parseFloat(school.distaceInfo.split(" ")[1]);
            float schoolDistance2 = Float.parseFloat(school2.distaceInfo.split(" ")[1]);

            return Float.compare(schoolDistance, schoolDistance2);
        }
    };

    public static Comparator<SchoolItem> DrivingTimeComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem school2) {
            return Float.compare(Float.parseFloat(school.getDrivingTime()), Float.parseFloat(school2.getDrivingTime()));
        }
    };

    public void openSchoolInfo(){
        //
        //
        //
        //
        //
    }

    public static Comparator<SchoolItem> NameComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem t2) {
            return school.getSchoolName().compareTo(t2.getSchoolName());
        }
    };

    public static Comparator<SchoolItem> PublicTransportTimeComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem schoolItem, SchoolItem t1) {
            return Float.compare(Float.parseFloat(schoolItem.getPublicTransport()), Float.parseFloat(t1.getPublicTransport()));
        }
    };
}
