package com.example.edupedia.controller;

import android.os.Build;

import com.example.edupedia.model.School;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.RequiresApi;

/**
 * Class to provide sort methods based on parameters passed in
 */
public class SortController {
    public static final int DIST = 0;
    public static final int CUTOFF = 1;
    public static final int TRANSPORT_TIME = 2;
    public static final int DRIVING_TIME = 3;
    public static final int NAME = 4;

    private static SortController singleton = null;

    private SortController() {
    }

    public static SortController getInstance() {
        return singleton==null? singleton=new SortController() : singleton;
    }

    /**
     * sorts an arraylist of schools based on input parameters passed in
     * @param sortVariable
     * @param ascending
     * @param schools
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<School> sortBy(int sortVariable, boolean ascending, ArrayList<School> schools) {
        switch(sortVariable) {
            case DIST:
                schools.sort(School.DistanceComparator);
                break;

            case TRANSPORT_TIME:
                schools.sort(School.PublicTransportTimeComparator);
                break;

            case DRIVING_TIME:
                schools.sort(School.DrivingTimeComparator);
                break;

            case NAME:
                schools.sort(School.NameComparator);
                break;
        }
        if(!ascending) {
            Collections.reverse(schools);
        }

        return schools;
    }



}
