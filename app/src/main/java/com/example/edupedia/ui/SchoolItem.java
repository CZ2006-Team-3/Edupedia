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
    public void openSchoolInfo(){
        //technically should open up the schoolInformation Page;

        //search through school hashmap based on the schoolName.
        //pass that school into the schoolInfoUIClass
        //creates layout dynamically and goes to that activity
    }





    public void removeWatchList(){
        //watchlistController.removeSchool(0);
        //school should be removed from watchList
        //search through school hashmap based on schoolName
        //call the watchlist add method by passing that school into the class
    }

    public void addToCompare(){
        gradeCutOff = "added to compare";
        //school should be added to compare class
        //search through school hashmap based on schoolName
        //add to the compare array and write a toast if compare array is full!
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
            return Double.compare(Double.parseDouble(school.getDistanceInfo()), Double.parseDouble(school2.getDistanceInfo()));
        }
    };

    public static Comparator<SchoolItem> NameComparator = new Comparator<SchoolItem>() {
        @Override
        public int compare(SchoolItem school, SchoolItem t2) {
            return school.getSchoolName().compareTo(t2.getSchoolName());
        }
    };
}
