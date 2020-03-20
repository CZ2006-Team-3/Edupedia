package com.example.edupedia.controller;

import android.os.Build;

import com.example.edupedia.model.School;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.RequiresApi;

public class SortController {
    private static final int DIST = 0;
    private static final int CUTOFF = 1;
    private static final int TRANSPORT_TIME = 2;
    private static final int DRIVING_TIME = 3;

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
        }
        if(!ascending) {
            Collections.reverse(schools);
        }
        return schools;
    }


}
