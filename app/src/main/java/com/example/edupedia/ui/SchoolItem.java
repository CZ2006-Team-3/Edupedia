package com.example.edupedia.ui;

import android.content.Intent;

import com.example.edupedia.ui.StartUI;
import com.example.edupedia.ui.home.HomeFragment;

public class SchoolItem {
    private int mImageResource;
    private String schoolName;
    private String distaceInfo;
    private String gradeCutOff;

    public SchoolItem(int ImageResource, String SchoolName, String GradecutOff, String Distance_Calculated){
        mImageResource = ImageResource;
        schoolName = SchoolName;
        distaceInfo = Distance_Calculated;
        gradeCutOff = GradecutOff;

    }
    public void openSchoolInfo(){
        schoolName = "clicked";
    }


    public int getmImageResource() {
        return mImageResource;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getDistaceInfo() {
        return distaceInfo;
    }

    public String getGradeCutOff() {
        return gradeCutOff;
    }
}
