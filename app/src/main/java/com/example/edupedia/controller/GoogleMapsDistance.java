package com.example.edupedia.controller;

import android.location.Address;

import com.example.edupedia.model.School;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GoogleMapsDistance {

    private static final String TAG = "Google Maps Distance";
    private static final String KEY = "AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM";

    public static School googleMapsDistance(School school, String userLocation) {
        GoogleMapsActivity googleMaps = new GoogleMapsActivity();
        Address schAddress = googleMaps.geoLocate2("Singapore " + school.getPostalCode());
        Log.e(TAG, school.getPostalCode());
        Log.e(TAG, school.getSchoolName());
        String schLat = String.valueOf(schAddress.getLatitude());
        Log.e(TAG, "Sch Lat+" +schLat);
        String schLong = String.valueOf(schAddress.getLongitude());
        Log.e(TAG, "Sch Long:" +schLong);
        Address userAddress = googleMaps.geoLocate2(userLocation);
        String userLat = String.valueOf(userAddress.getLatitude());
        Log.e(TAG, "User Lat:" +userLat);
        String userLong = String.valueOf(userAddress.getLongitude());
        Log.e(TAG, "User Long" + userLong);
        ArrayList<String> modeList = new ArrayList<String>();
        modeList.add("driving");
        modeList.add("transit");
        ArrayList<Double> distList = new ArrayList<Double>();
        ArrayList<Double> durList = new ArrayList<Double>();
        for (String mode: modeList) {
            Log.e(TAG, mode);
            String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + schLat + "," + schLong +
                    "&destinations=" + userLat + "%2C" + userLong +
                    "&mode=" + mode +
                    "&key=" + KEY;
            //https://maps.googleapis.com/maps/api/distancematrix/json?origins=1.29836,103.882979&destinations=1.332989%2C103.843957&mode=transit&key=AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM
            Log.e(TAG, requestURL);
            HttpHandler sh = new HttpHandler();
            try {
                String jsonStr = sh.execute(requestURL).get();
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);
                        JSONArray rows= jsonObj.getJSONArray("rows");
                        JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                        double distance = Double.valueOf(elements.getJSONObject(0).getJSONObject("distance").getString("value"));
                        distance = distance/1000;
                        double duration = Double.valueOf(elements.getJSONObject(0).getJSONObject("duration").getString("value"));
                        duration = duration/60;
                        distList.add(distance);
                        Log.d(TAG, mode+distance);
                        durList.add(duration);
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }
                }
            }
            catch(InterruptedException e) {

            }
            catch (ExecutionException e) {

            }
        }
        try {
        Log.e(TAG, "Driving time:"+String.valueOf(durList.get(0)));
        Log.e(TAG, "Transport time:"+ String.valueOf(durList.get(1)));
            school.setDrivingTime(durList.get(0));
            school.setPublicTime(durList.get(1));
            school.setDistance(distList.get(0));
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Error: " + school.getSchoolName());
        }
        //school.setTravelTimes(durList);
        //school.setDistances(distList);
        return school;
    }
}