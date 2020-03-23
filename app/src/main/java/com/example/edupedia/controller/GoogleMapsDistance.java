package com.example.edupedia.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.location.Address;

import com.example.edupedia.model.School;
import com.example.edupedia.ui.AdapterClass;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;


public class GoogleMapsDistance extends AppCompatActivity implements Serializable {
    //AsyncTask<Object, String, ArrayList<School>>
    private static final String TAG = "GoogleMapsDistance";
    private static final String KEY = "AIzaSyCUaalvzVnKZLKDtGCAp1hmU9pIrov4EMM";
    private AdapterClass adapterClass;
    private GoogleMapsActivity googleMaps;
//    private ProgressDialog dialog;
    //private static GoogleMapsActivity googleMaps;

    /*protected void onPreExecute() {
        this.googleMaps = new GoogleMapsActivity();
        adapterClass = new AdapterClass();
       // dialog = ProgressDialog.show(getContext(), null, "Loading schools...", false);
    }
*/
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                ArrayList<School> schools = (ArrayList) extras.get("School List");
                Log.d(TAG, "Goittee");
                String userLocation = extras.getString("User Location");
                new distanceTask().execute(schools, userLocation);
            }
             googleMaps = new GoogleMapsActivity();
             adapterClass = new AdapterClass();
        }

        protected class distanceTask extends AsyncTask<Object, Void, ArrayList<School>>{
             protected ArrayList<School> doInBackground(Object... objects) {
            List<School> schools = (List) objects[0];
            ArrayList<School> retSchools = new ArrayList<>();
            String userLocation = (String) objects[1];
            for (School school : schools) {
                Address schAddress = googleMaps.geoLocate2("Singapore " + school.getPostalCode());
                Log.e(TAG, school.getPostalCode());
                Log.e(TAG, school.getSchoolName());
                String schLat = String.valueOf(schAddress.getLatitude());
                Log.e(TAG, "Sch Lat+" + schLat);
                String schLong = String.valueOf(schAddress.getLongitude());
                Log.e(TAG, "Sch Long:" + schLong);
                Address userAddress = googleMaps.geoLocate2(userLocation);
                String userLat = String.valueOf(userAddress.getLatitude());
                Log.e(TAG, "User Lat:" + userLat);
                String userLong = String.valueOf(userAddress.getLongitude());
                Log.e(TAG, "User Long" + userLong);
                ArrayList<String> modeList = new ArrayList<>();
                modeList.add("driving");
                modeList.add("transit");
                ArrayList<Double> distList = new ArrayList<>();
                ArrayList<Double> durList = new ArrayList<>();
                for (String mode : modeList) {
                    Log.e(TAG, mode);
                    String requestURL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + schLat + "," + schLong +
                            "&destinations=" + userLat + "%2C" + userLong +
                            "&mode=" + mode +
                            "&key=" + KEY;
                    Log.e(TAG, requestURL);
                    //HttpHandler sh = new HttpHandler();
                    //String jsonStr = sh.makeServiceCall(userLocation);
                    String jsonStr = null;
                    try {
                        URL url = new URL(requestURL);
                        Log.d("URL", requestURL);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.connect();
                        int responseCode = conn.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            InputStream in = new BufferedInputStream(conn.getInputStream());
                            //response = convertStreamToString(in);
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder sb = new StringBuilder();

                            String line;
                            try {
                                while ((line = reader.readLine()) != null) {
                                    sb.append(line).append('\n');
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    in.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonStr = sb.toString();
                        }
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "MalformedURLException: " + e.getMessage());
                    } catch (ProtocolException e) {
                        Log.e(TAG, "ProtocolException: " + e.getMessage());
                    } catch (IOException e) {
                        Log.e(TAG, "IOException: " + e.getMessage());
                    } catch (Exception e) {
                        Log.e(TAG, "Exception: " + e.getMessage());
                    }
                    Log.e(TAG, "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(jsonStr);
                            JSONArray rows = jsonObj.getJSONArray("rows");
                            JSONArray elements = rows.getJSONObject(0).getJSONArray("elements");
                            double distance = Double.valueOf(elements.getJSONObject(0).getJSONObject("distance").getString("value"));
                            distance = distance / 1000;
                            double duration = Double.valueOf(elements.getJSONObject(0).getJSONObject("duration").getString("value"));
                            duration = duration / 60;
                            distList.add(distance);
                            Log.d(TAG, mode + distance);
                            durList.add(duration);
                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    }

                    try {
                        Log.e(TAG, "Driving time:" + String.valueOf(durList.get(0)));
                        school.setDrivingTime(durList.get(0));

                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Driving time error: " + school.getSchoolName());
                    }
                    try {
                        Log.e(TAG, "Transport time:" + String.valueOf(durList.get(1)));
                        school.setPublicTime(durList.get(1));
                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Transport time error: " + school.getSchoolName());
                    }
                    try {
                        Log.e(TAG, "Distance" + String.valueOf(distList.get(1)));
                        school.setDistance(distList.get(0));

                    } catch (IndexOutOfBoundsException e) {
                        Log.e(TAG, "Distance error: " + school.getSchoolName());
                    }
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(getBaseContext(), school.getSchoolName(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Running on UI Thread");
                        adapterClass.notifyDataSetChanged();

                    }
                });
                //publishProgress(school.getSchoolName());
                retSchools.add(school);
            }
            return retSchools;
        }

        /*
    protected void onPostExecute(School school) {
        Log.d(TAG,"Post Execute");
        //Toast.makeText(this.getApplicationContext(), "HI", Toast.LENGTH_SHORT).show();
        adapterClass.notifyDataSetChanged();
    }
        protected void onProgressUpdate(String... schName) {
            super.onProgressUpdate(schName);
            Log.d(TAG, schName[0]);
            adapterClass.notifyDataSetChanged();
        }

         */

    }

   /* public static void setGoogleMapsActivity(GoogleMapsActivity googleMapsActivity) {
        googleMaps = googleMapsActivity;
    }*/
}
