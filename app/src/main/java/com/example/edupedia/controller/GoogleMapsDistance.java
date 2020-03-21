package com.example.edupedia.controller;

import android.location.Address;

import com.example.edupedia.model.School;

import java.util.ArrayList;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GoogleMapsDistance {

    private static final String TAG = HttpHandler.class.getSimpleName();
    private static final String KEY = "AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM";

    public static School googleMapsDistance(School school, String userLocation) {
        GoogleMapsActivity googleMaps = new GoogleMapsActivity();
        Address schAddress = googleMaps.geoLocate2(school.getAddress());
        String schLat = String.valueOf(schAddress.getLatitude());
        String schLong = String.valueOf(schAddress.getLongitude());
        Address userAddress = googleMaps.geoLocate2(userLocation);
        String userLat = String.valueOf(userAddress.getLatitude());
        String userLong = String.valueOf(userAddress.getLatitude());
        ArrayList<String> modeList = new ArrayList<String>();
        modeList.add("driving");
        modeList.add("transit");
        ArrayList<Double> distList = new ArrayList<Double>();
        ArrayList<Double> durList = new ArrayList<Double>();
        for (String mode: modeList) {
            String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + schLat + "," + schLong +
                    "&destinations=" + userLat + "%2C" + userLong +
                    "&mode=" + mode +
                    "&key=" + KEY;
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(requestURL);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray rows= jsonObj.getJSONArray("rows");
                    JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                    double distance = Double.valueOf(elements.getJSONObject(0).getJSONObject("distance").getString("text"));
                    double duration = Double.valueOf(elements.getJSONObject(0).getJSONObject("duration").getString("text"));
                    distList.add(distance);
                    durList.add(duration);
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }
            }
        }
        school.setDrivingTime(durList.get(0));
        school.setPublicTime(durList.get(1));
        school.setDistance(distList.get(0));
        //school.setTravelTimes(durList);
        //school.setDistances(distList);
        return school;
    }
}